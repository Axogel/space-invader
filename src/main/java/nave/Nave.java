package Nave;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import Recursos.Recursos;

public class Nave {
    private int x;
    private int y;
    private int dx; // Movimiento horizontal aleatorio
    private final int dy = 10; // Movimiento vertical fijo
    public int puntos;
    private final int ancho = 50; // Ancho más grande para la nave
    private final int alto = 40;  // Alto más grande para la nave
    private final Image imagen;
    private boolean activo;
    private final Random random;

    public Nave(int inicioX, int inicioY) {
        this.x = inicioX;
        this.y = inicioY;
        this.activo = true;
        this.random = new Random();
        this.imagen = Recursos.cargarImagen("src/main/java/Imagenes/pulpo.png");
        this.puntos = 100 + random.nextInt(100); 

        this.dx = random.nextInt(3) + 1; 
    }

    public void mover() {
        x += dx;
        if (x > 800 - ancho || x < 0) {
            dx = -(random.nextInt(5) + 1); // Velocidad aleatoria negativa para moverse en sentido contrario
            y += dy;
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
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
