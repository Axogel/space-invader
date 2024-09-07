package Logica;

import Jugador.Jugador;
import Disparo.Disparo;
import Aliens.Aliens;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class Logica extends Canvas {

    private final Jugador jugador;
    private boolean izquierdaPresionada = false;
    private boolean derechaPresionada = false;
    private final Disparo[] disparos = new Disparo[10];
    private int numDisparos = 0;
    private final Aliens[] enemigos = new Aliens[5]; // Arreglo para enemigos
    private int numEnemigos = 0;

    public Logica() {
        jugador = new Jugador(400, 550);

        // Inicializar enemigos
        for (int i = 0; i < enemigos.length; i++) {
            enemigos[i] = new Aliens(100 + i * 60, 50); 
            numEnemigos++;
        }

        setBackground(Color.BLACK);
        setSize(800, 600);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    izquierdaPresionada = true;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    derechaPresionada = true;
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    disparar();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    izquierdaPresionada = false;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    derechaPresionada = false;
                }
            }
        });

        Timer timer = new Timer(16, e -> actualizar());
        timer.start();
    }

    private void actualizar() {
        if (izquierdaPresionada && jugador.getX() > 0) {
            jugador.moverIzquierda();
        }
        if (derechaPresionada && jugador.getX() < getWidth() - jugador.getAncho()) {
            jugador.moverDerecha();
        }

        // Mover enemigos
        for (int i = 0; i < numEnemigos; i++) {
            Aliens enemigo = enemigos[i];
            if (enemigo != null) {
                enemigo.mover();
            }
        }

        // Actualizar disparos
        for (int i = 0; i < numDisparos; i++) {
            Disparo disparo = disparos[i];
            if (disparo != null) {
                disparo.mover();
                if (!disparo.estaActivo()) {
                    disparos[i] = disparos[numDisparos - 1];
                    disparos[numDisparos - 1] = null;
                    numDisparos--;
                }
            }
        }

        // Detectar colisiones
        for (int i = 0; i < numDisparos; i++) {
            Disparo disparo = disparos[i];
            if (disparo != null) {
                Rectangle boundsDisparo = disparo.getBounds();
                for (int j = 0; j < numEnemigos; j++) {
                    Aliens enemigo = enemigos[j];
                    if (enemigo != null && enemigo.estaActivo()) {
                        Rectangle boundsEnemigo = enemigo.getBounds();
                        if (boundsDisparo.intersects(boundsEnemigo)) {
                            enemigo.setActivo(false);
                            disparo.setActivo(false);
                            break;
                        }
                    }
                }
            }
        }

        repaint();
    }

    private void disparar() {
        if (numDisparos < disparos.length) {
            Disparo nuevoDisparo = new Disparo(jugador.getX() + jugador.getAncho() / 2 - Disparo.ANCHO / 2, jugador.getY() - Disparo.ALTO);
            disparos[numDisparos] = nuevoDisparo;
            numDisparos++;
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
        for (int i = 0; i < numEnemigos; i++) {
            Aliens enemigo = enemigos[i];
            if (enemigo != null) {
                enemigo.dibujar(g);
            }
        }
    }
}
