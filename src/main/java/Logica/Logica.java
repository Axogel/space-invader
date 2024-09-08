package Logica;

import Jugador.Jugador;
import Disparo.Disparo;
import Aliens.Aliens;
import Bloques.Bloques;
import Nivel.Nivel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.image.BufferStrategy;
import Nave.Nave; 
import Menu.Menu; 
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Logica extends Canvas {

    private final Jugador jugador;
    private boolean izquierdaPresionada = false;
    private boolean derechaPresionada = false;
    private final Disparo[] disparos;
    private int numDisparos = 0;
    private Aliens[] enemigos;
    private int numEnemigos;
    private Bloques[] bloques;
    private int numBloques;
    private boolean juegoEnPausa = false;
    private Timer timer;
    private int puntuacion;
    private Nivel nivelActual;
    private int nivel;
    private Timer naveTimer;
    private int numNaves = 0;
    private Nave[] naves;
private Clip clipMusica;

    public Logica() {
        jugador = new Jugador(400, 550);
        disparos = new Disparo[10];
        enemigos = new Aliens[0]; 
        bloques = new Bloques[0]; 
        naves = new Nave[2];

        numEnemigos = 0;
        numBloques = 0;
        nivel = 1;
        cargarNivel(nivel);
        puntuacion = 0;

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
        naveTimer = new Timer(10000, e -> generarNave());
        naveTimer.start();

    }
        public void setClipMusica(Clip clip) {
        this.clipMusica = clip;
    }

    private void cerrarJuego() {
        if (clipMusica != null && clipMusica.isRunning()) {
            clipMusica.stop();
            clipMusica.close();
        }
    }
private void generarNave() {
        if (numNaves < naves.length) {
            Nave nuevaNave = new Nave((int) (Math.random() * (getWidth() - 40)), 50);
            naves[numNaves] = nuevaNave;
            numNaves++;
        }
    }

    private void moverNaves() {
        for (int i = 0; i < numNaves; i++) {
            Nave nave = naves[i];
            if (nave != null && nave.estaActivo()) {
                nave.mover(); 
            }
        }
    }

    private void dibujarNaves(Graphics g) {
        for (Nave nave : naves) {
            if (nave != null && nave.estaActivo()) {
                nave.dibujar(g);
            }
        }
    }
    private void cargarNivel(int nivel) {
        nivelActual = new Nivel(nivel);
        enemigos = nivelActual.getEnemigos();
        bloques = nivelActual.getBloques();
        numEnemigos = nivelActual.getNumEnemigos();
        numBloques = bloques.length;
    }

    private void verificarNivel() {
        if (numEnemigos <= 0) {
            nivel++;
            if (nivel > 3) {
                JOptionPane.showMessageDialog(this, "¡Has ganado el juego!");
                System.exit(0);
            } else {
                cargarNivel(nivel); 
            }
        }
    }

    private void eliminarEnemigo(int index) {
        for (int i = index; i < numEnemigos - 1; i++) {
            enemigos[i] = enemigos[i + 1];
        }
        enemigos[numEnemigos - 1] = null;
        numEnemigos--;
    }

    private void verificarColisionesConEnemigos() {
        for (Aliens enemigo : enemigos) {
            if (enemigo != null && enemigo.estaActivo()) {
                Rectangle boundsEnemigo = enemigo.getBounds();
                Rectangle boundsJugador = new Rectangle(jugador.getX(), jugador.getY(), jugador.getAncho(), 20);
                
                if (boundsJugador.intersects(boundsEnemigo)) {
                    jugador.perderVida();
                    enemigo.setActivo(false);
                    puntuacion -= 50; 
                    break;
                }
            }
        }
    }

private void detectarColisiones() {
    for (int i = 0; i < numDisparos; i++) {
        Disparo disparo = disparos[i];
        Rectangle boundsDisparo = disparo.getBounds();

        for (int j = 0; j < numEnemigos; j++) {
            Aliens enemigo = enemigos[j];
            if (enemigo != null && enemigo.estaActivo()) {
                Rectangle boundsEnemigo = enemigo.getBounds();
                if (boundsDisparo.intersects(boundsEnemigo)) {
                    enemigo.setActivo(false);
                    disparo.setActivo(false);
                    puntuacion += enemigo.puntos; 
                    eliminarEnemigo(j);
                    break;
                }
            }
        }

        for (int k = 0; k < numBloques; k++) {
            Bloques bloque = bloques[k];
            if (bloque != null && bloque.estaActivo() && boundsDisparo.intersects(bloque.getBounds())) {
                bloque.destruir(); 
                disparo.setActivo(false);
                break;
            }
        }

        for (int l = 0; l < numNaves; l++) {
            Nave nave = naves[l];
            if (nave != null && nave.estaActivo()) {
                Rectangle boundsNave = nave.getBounds();
                if (boundsDisparo.intersects(boundsNave)) {
                    nave.setActivo(false); 
                    disparo.setActivo(false);
                    puntuacion += nave.puntos; 
                    break;
                }
            }
        }
    }

    verificarColisionesConEnemigos();

    verificarNivel();
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
            if (opcion == 0) { 
                juegoEnPausa = false;
                timer.start();
            } else if (opcion == 1) {
                guardarPartida();
                juegoEnPausa = false;
                timer.start();
            } else if (opcion == 2) { 
                                cerrarJuego();
                JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
                ventana.dispose();
                new Menu(); 

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
                    writer.write("Enemigo " + i + " X: " + enemigo.getBounds().x + " Y: " + enemigo.getBounds().y + "\n");
                }
            }
            writer.write("Puntuación: " + puntuacion + "\n");
            writer.write("Vidas: " + jugador.getVidas() + "\n");
            JOptionPane.showMessageDialog(this, "Partida guardada exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            moverNaves();
            detectarColisiones();
            renderizar(); 
        }
    }

    private void eliminarDisparo(int index) {
        for (int i = index; i < numDisparos - 1; i++) {
            disparos[i] = disparos[i + 1];
        }
        disparos[numDisparos - 1] = null;
        numDisparos--;
    }

    public void renderizar() {
        BufferStrategy buffer = getBufferStrategy();
        if (buffer == null) {
            createBufferStrategy(3); 
            return;
        }

        Graphics g = buffer.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        jugador.dibujar(g);
        for (Aliens enemigo : enemigos) {
            if (enemigo != null && enemigo.estaActivo()) {
                enemigo.dibujar(g);
            }
        }
        for (int i = 0; i < numDisparos; i++) {
            if (disparos[i] != null) {
                disparos[i].dibujar(g);
            }
        }
        for (Bloques bloque : bloques) {
            if (bloque != null && bloque.estaActivo()) {
                bloque.dibujar(g);
            }
        }
        dibujarNaves(g); 

    int xInicio = 10; 
    int yInicio = 520;
    jugador.dibujarVidas(g, xInicio, yInicio);
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 20));
    g.drawString("Puntuación: " + puntuacion, 10, 30); 
        g.dispose();
        buffer.show();
    }
}
