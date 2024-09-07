package Jugador;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Jugador {
    private int x;
    private final int y;
    private final int ancho = 60;
    private final int alto = 20;
    private final Image imagen;

    public Jugador(int inicioX, int inicioY) {
        this.x = inicioX;
        this.y = inicioY;
        imagen = new ImageIcon("src/main/java/Imagenes/nave.png").getImage();
    }

    public void moverIzquierda() {
        x -= 5;
    }

    public void moverDerecha() {
        x += 5;
    }

    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ancho, alto, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAncho() {
        return ancho;
    }
}
