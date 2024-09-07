package Bloques;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Bloques {
    private int x;
    private int y;
    private final int ancho = 50; // Puedes ajustar el tamaño según sea necesario
    private final int alto = 20;
    private final Image imagen;
    private boolean activo;

    public Bloques(int x, int y) {
        this.x = x;
        this.y = y;
        this.imagen = new ImageIcon("src/main/java/Imagenes/bloque.png").getImage(); // Asegúrate de tener esta imagen
        this.activo = true;
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

    public void destruir() {
        activo = false; // El bloque se considera destruido
    }
}
