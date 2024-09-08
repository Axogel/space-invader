package Aliens;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import Recursos.Recursos;

public class Aliens {
    private int x;
    private int y;
    private int dx = 2; // Movimiento horizontal
    private int dy = 10; // Movimiento vertical
    public int puntos;
    private final int ancho = 40;
    private final int alto = 30;
    private final Image imagen;
    private boolean activo;

    public Aliens(int inicioX, int inicioY, int tipo) {
        this.x = inicioX;
        this.y = inicioY;
        this.activo = true;

        switch (tipo) {
            case 1:
                this.imagen = Recursos.cargarImagen("src/main/java/Imagenes/calamar.png");
                this.puntos = 30;  // Puntos para el alien tipo 1
                break;
            case 2:
                this.imagen = Recursos.cargarImagen("src/main/java/Imagenes/cangrejo.png");
                this.puntos = 20;  // Puntos para el alien tipo 2
                break;
            case 3:
                this.imagen = Recursos.cargarImagen("src/main/java/Imagenes/pulpo.png");
                this.puntos = 10;  // Puntos para el alien tipo 3
                break;
            case 4: 
                //nave 
                this.imagen = Recursos.cargarImagen("src/main/java/Imagenes/pulpo.png");
                this.puntos = (int)(Math.random());  // Puntos para el alien tipo 3
                break;
            default:
                this.imagen = Recursos.cargarImagen("src/main/java/Imagenes/default.png");
                this.puntos = 50;   // Puntos por defecto si no coincide ningún tipo
                break;
        }
    }

    public void mover() {
        x += dx;
        if (x > 800 - ancho || x < 0) {
            dx = -dx;
            y += dy; // Baja cuando llega a los bordes
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
    public int  getX(){
        return x;
    }
    public int  getY(){
        return y;
    }
}