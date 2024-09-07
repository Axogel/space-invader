package Logica;

import Jugador.Jugador;
import Disparo.Disparo;
import Aliens.Aliens;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class Logica extends Canvas {

    private final Jugador jugador;
    private boolean izquierdaPresionada = false;
    private boolean derechaPresionada = false;
    private final Disparo[] disparos;
    private int numDisparos = 0;
    private final Aliens[] enemigos;

    public Logica() {
        jugador = new Jugador(400, 550);
        disparos = new Disparo[10];
        enemigos = new Aliens[5];

        for (int i = 0; i < enemigos.length; i++) {
            enemigos[i] = new Aliens(100 + i * 60, 50);
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

        for (Aliens enemigo : enemigos) {
            enemigo.mover();
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
            for (Aliens enemigo : enemigos) {
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
        for (Aliens enemigo : enemigos) {
            if (enemigo != null) {
                enemigo.dibujar(g);
            }
        }
    }
}