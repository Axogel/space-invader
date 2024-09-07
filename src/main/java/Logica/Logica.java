package Logica;

import Jugador.Jugador;
import Disparo.Disparo;
import Aliens.Aliens;
import Bloques.Bloques;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Logica extends Canvas {

    private final Jugador jugador;
    private boolean izquierdaPresionada = false;
    private boolean derechaPresionada = false;
    private final Disparo[] disparos;
    private int numDisparos = 0;
    private final Aliens[] enemigos;
    private int numEnemigos;
    private final Bloques[] bloques; // Lista de bloques
    private int numBloques; // Número de bloques
    private boolean juegoEnPausa = false;
    private Timer timer;
    private int puntuacion; // Variable para la puntuación

    public Logica() {
        jugador = new Jugador(400, 550);
        disparos = new Disparo[10];
        enemigos = new Aliens[5];
        bloques = new Bloques[3]; // Inicializa el array de bloques

        // Inicializar enemigos
        for (int i = 0; i < enemigos.length; i++) {
            enemigos[i] = new Aliens(100 + i * 60, 50);
        }

        // Inicializar bloques
        for (int i = 0; i < bloques.length; i++) {
            bloques[i] = new Bloques(270 + i * 100, 500);
        }

        puntuacion = 0; // Inicializar la puntuación

        setBackground(Color.BLACK);
        setSize(800, 600);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!juegoEnPausa) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        izquierdaPresionada = true;
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        derechaPresionada = true;
                    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        disparar();
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_P) {
                    pausarJuego();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!juegoEnPausa) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        izquierdaPresionada = false;
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        derechaPresionada = false;
                    }
                }
            }
        });

        timer = new Timer(16, e -> actualizar());
        timer.start();
    }

    private void actualizar() {
        if (!juegoEnPausa) {
            if (izquierdaPresionada && jugador.getX() > 0) {
                jugador.moverIzquierda();
            }
            if (derechaPresionada && jugador.getX() < getWidth() - jugador.getAncho()) {
                jugador.moverDerecha();
            }

            for (Aliens enemigo : enemigos) {
                if (enemigo != null) {
                    enemigo.mover();
                }
            }

            for (int i = 0; i < numDisparos; i++) {
                Disparo disparo = disparos[i];
                if (disparo != null) {
                    disparo.mover();
                    if (!disparo.estaActivo()) {
                        eliminarDisparo(i);
                    }
                }
            }

            detectarColisiones();
            repaint();
        }
    }

    private void eliminarDisparo(int index) {
        for (int i = index; i < numDisparos - 1; i++) {
            disparos[i] = disparos[i + 1];
        }
        disparos[numDisparos - 1] = null;
        numDisparos--;
    }

    private void detectarColisiones() {
        for (int i = 0; i < numDisparos; i++) {
            Disparo disparo = disparos[i];
            Rectangle boundsDisparo = disparo.getBounds();

            // Colisiones con enemigos
            for (int j = 0; j < numEnemigos; j++) {
                Aliens enemigo = enemigos[j];
                if (enemigo != null && enemigo.estaActivo()) {
                    Rectangle boundsEnemigo = enemigo.getBounds();
                    if (boundsDisparo.intersects(boundsEnemigo)) {
                        enemigo.setActivo(false);
                        disparo.setActivo(false);
                        puntuacion += 100; // Incrementar la puntuación por destruir un enemigo
                        break;
                    }
                }
            }

            // Colisiones con bloques
            for (int k = 0; k < bloques.length; k++) {
                Bloques bloque = bloques[k];
                if (bloque != null && bloque.estaActivo() && boundsDisparo.intersects(bloque.getBounds())) {
                    bloque.destruir();
                    disparo.setActivo(false);
                    puntuacion += 50; // Incrementar la puntuación por destruir un bloque
                    break;
                }
            }
        }
    }

    private void disparar() {
        if (numDisparos < disparos.length) {
            Disparo nuevoDisparo = new Disparo(jugador.getX() + jugador.getAncho() / 2 - Disparo.ANCHO / 2, jugador.getY() - Disparo.ALTO);
            disparos[numDisparos] = nuevoDisparo;
            numDisparos++;
        }
    }

    public void pausarJuego() {
        juegoEnPausa = !juegoEnPausa;
        if (juegoEnPausa) {
            timer.stop();
            int opcion = mostrarMenuPausa();
            if (opcion == 0) { // Reanudar
                juegoEnPausa = false;
                timer.start();
            } else if (opcion == 1) { // Guardar Partida
                guardarPartida();
                juegoEnPausa = false;
                timer.start();
            } else if (opcion == 2) { // Salir
                System.exit(0);
            }
        }
    }

    private int mostrarMenuPausa() {
        String[] opciones = {"Reanudar", "Guardar Partida", "Salir"};
        return JOptionPane.showOptionDialog(this, "Juego en Pausa", "Menu de Pausa",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
    }

    private void guardarPartida() {
        try (FileWriter writer = new FileWriter("partida_guardada.txt")) {
            writer.write("Jugador X: " + jugador.getX() + "\n");
            for (int i = 0; i < enemigos.length; i++) {
                Aliens enemigo = enemigos[i];
                if (enemigo != null) {
                    writer.write("Enemigo " + i + " X: " + enemigo.getX() + " Y: " + enemigo.getY() + "\n");
                }
            }
            JOptionPane.showMessageDialog(this, "Partida guardada exitosamente");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la partida");
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        jugador.dibujar(g);
        for (int i = 0; i < numDisparos; i++) {
            Disparo disparo = disparos[i];
            if (disparo != null) {
                disparo.dibujar(g);
            }
        }
        for (Aliens enemigo : enemigos) {
            if (enemigo != null) {
                enemigo.dibujar(g);
            }
        }
        for (Bloques bloque : bloques) {
            if (bloque != null) {
                bloque.dibujar(g);
            }
        }

        // Mostrar la puntuación
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Puntuación: " + puntuacion, 10, 20);
    }
}
