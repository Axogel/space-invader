package Aliens;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import Recursos.Recursos;

public class Aliens {
    private int x;
    private int y;
    private final int ancho = 40;
    private final int alto = 30;
    private final Image imagen;
    private boolean activo;

    public Aliens(int inicioX, int inicioY) {
        this.x = inicioX;
        this.y = inicioY;
        this.imagen = Recursos.cargarImagen("src/main/java/Imagenes/calamar.png"); 
        this.activo = true;
    }

    public void mover() {
        x += 2;
        if (x > 800) {
            x = -ancho;
        }
    }

    public void dibujar(Graphics g) {
        if (activo) {
            g.drawImage(imagen, x, y, ancho, alto, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }

    public boolean estaActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
