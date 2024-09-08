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
    private final Image imagenVida; // Imagen para las vidas
    private final int anchoVida; // Ancho de la imagen de vida
    private final int altoVida; // Alto de la imagen de vida
    private int vidas;

    public Jugador(int inicioX, int inicioY) {
        this.x = inicioX;
        this.y = inicioY;
        imagen = new ImageIcon("src/main/java/Imagenes/nave.png").getImage();
        imagenVida = new ImageIcon("src/main/java/Imagenes/nave.png").getImage(); // Imagen para las vidas
        this.anchoVida = 20; // Ajusta el ancho deseado de la imagen de vida
        this.altoVida = 20; // Ajusta el alto deseado de la imagen de vida
        this.vidas = 3;
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

    public void dibujarVidas(Graphics g, int xInicio, int yInicio) {
        for (int i = 0; i < vidas; i++) {
            g.drawImage(imagenVida, xInicio + i * (anchoVida + 10), yInicio, anchoVida, altoVida, null);
        }
    }

    public void perderVida() {
        if (vidas > 0) {
            vidas--;
        }
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

    public int getVidas() {
        return vidas;
    }
}
