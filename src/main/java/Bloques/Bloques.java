package Bloques;

import Recursos.Recursos;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * La clase {@code Bloques} representa un bloque en el juego que puede tener varios estados visuales.
 * Los bloques pueden ser destruidos progresivamente, cambiando su imagen en cada estado.
 */
public class Bloques {
    private int x, y;
    private Image[] imagenes; // Array de imágenes para el bloque en diferentes estados
    private int estado; // Para rastrear el estado actual del bloque
    private boolean activo;

    /**
     * Crea una nueva instancia de {@code Bloques} con la posición inicial especificada.
     * Inicialmente, el bloque está en el estado completo.
     *
     * @param x la coordenada X inicial del bloque
     * @param y la coordenada Y inicial del bloque
     */
    public Bloques(int x, int y) {
        this.x = x;
        this.y = y;
        this.estado = 0; // Comienza con el estado completo
        this.activo = true;

        // Cargar las imágenes para los estados del bloque usando ImageIcon
        imagenes = new Image[6];
        imagenes[0] = Recursos.cargarImagen("bloque.png");
        imagenes[1] = Recursos.cargarImagen("bloque_progresivo_0.png");
        imagenes[2] = Recursos.cargarImagen("bloque_progresivo_1.png");
        imagenes[3] = Recursos.cargarImagen("bloque_progresivo_2.png");
        imagenes[4] = Recursos.cargarImagen("bloque_progresivo_3.png");
        imagenes[5] = Recursos.cargarImagen("bloque_progresivo_4.png");
    }

    /**
     * Destruye el bloque, progresando a través de sus estados.
     * Si el bloque ya está en el último estado, se marca como inactivo.
     */
    public void destruir() {
        if (estado < imagenes.length - 1) {
            estado++;
        } else {
            activo = false; // El bloque se destruye completamente
        }
    }

    /**
     * Dibuja la imagen actual del bloque en el gráfico especificado.
     *
     * @param g el objeto {@code Graphics} en el que se dibuja el bloque
     */
    public void dibujar(Graphics g) {
        if (activo) {
            g.drawImage(imagenes[estado], x, y, null);
        }
    }

    /**
     * Obtiene los límites del bloque como un rectángulo basado en su imagen actual.
     *
     * @return un {@code Rectangle} que representa los límites del bloque
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, imagenes[estado].getWidth(null), imagenes[estado].getHeight(null));
    }

    /**
     * Verifica si el bloque está activo.
     *
     * @return {@code true} si el bloque está activo, {@code false} en caso contrario
     */
    public boolean estaActivo() {
        return activo;
    }
}
