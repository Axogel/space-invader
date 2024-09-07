package Aliens;

import java.awt.Graphics;
import java.awt.Image;
import Recursos.Recursos;
import java.awt.Rectangle;

public class Aliens {
    private int x;
    private int y;
    private final int ancho = 40;
    private final int alto = 30;
    private final Image imagen;

    public Aliens(int inicioX, int inicioY) {
        this.x = inicioX;
        this.y = inicioY;
        imagen = Recursos.cargarImagen("src/main/java/Imagenes/calamar.png");
    }

    public void mover() {
        x += 2;
        if (x > 800) { 
            x = -ancho;
        }
    }

    public void dibujar(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, x, y, ancho, alto, null);
        }
    }

        public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }

    public boolean estaActivo() {
        return imagen != null;
    }

    public void setActivo(boolean activo) {
        // Si la imagen no es null, se considera activo
    }
}
