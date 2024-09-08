package Bloques;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Bloques {
    private int x, y;
    private Image[] imagenes; // Array de imágenes para el bloque en diferentes estados
    private int estado; // Para rastrear el estado actual del bloque
    private boolean activo;

    public Bloques(int x, int y) {
        this.x = x;
        this.y = y;
        this.estado = 0; // Comienza con el estado completo
        this.activo = true;

        // Cargar las imágenes para los estados del bloque usando ImageIcon
        imagenes = new Image[6];
        imagenes[0] = new ImageIcon("src/main/java/Imagenes/bloque.png").getImage();
        imagenes[1] = new ImageIcon("src/main/java/Imagenes/bloque_progresivo_0.png").getImage();
        imagenes[2] = new ImageIcon("src/main/java/Imagenes/bloque_progresivo_1.png").getImage();
        imagenes[3] = new ImageIcon("src/main/java/Imagenes/bloque_progresivo_2.png").getImage();
        imagenes[4] = new ImageIcon("src/main/java/Imagenes/bloque_progresivo_3.png").getImage();
        imagenes[5] = new ImageIcon("src/main/java/Imagenes/bloque_progresivo_4.png").getImage();
    }

    public void destruir() {
        if (estado < imagenes.length - 1) {
            estado++;
        } else {
            activo = false; // El bloque se destruye completamente
        }
    }

    public void dibujar(Graphics g) {
        if (activo) {
            g.drawImage(imagenes[estado], x, y, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, imagenes[estado].getWidth(null), imagenes[estado].getHeight(null));
    }

    public boolean estaActivo() {
        return activo;
    }
}
