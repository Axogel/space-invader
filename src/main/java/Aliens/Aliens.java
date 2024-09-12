package Aliens;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import Recursos.Recursos;

/**
 * La clase {@code Aliens} representa un alienígena en el juego Space Invaders.
 * Cada alienígena tiene una posición, una imagen asociada y puede moverse horizontal y verticalmente.
 * Además, la clase gestiona el estado del alienígena (activo o inactivo) y los puntos que otorga al ser destruido.
 */
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

    /**
     * Crea una nueva instancia de {@code Aliens} con la posición inicial especificada y el tipo de alienígena.
     *
     * @param inicioX la coordenada X inicial del alienígena
     * @param inicioY la coordenada Y inicial del alienígena
     * @param tipo el tipo de alienígena que determina la imagen y los puntos
     */
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
                // Nave 
                this.imagen = Recursos.cargarImagen("src/main/java/Imagenes/pulpo.png");
                this.puntos = (int)(Math.random());  // Puntos para el alien tipo 4
                break;
            default:
                this.imagen = Recursos.cargarImagen("src/main/java/Imagenes/default.png");
                this.puntos = 50;   // Puntos por defecto si no coincide ningún tipo
                break;
        }
    }

    /**
     * Mueve el alienígena horizontalmente. Si el alienígena llega al borde de la pantalla, cambia la dirección horizontal
     * y se mueve hacia abajo.
     */
    public void mover() {
        x += dx;
        if (x > 800 - ancho || x < 0) {
            dx = -dx;
            y += dy; // Baja cuando llega a los bordes
        }
    }

    /**
     * Dibuja la imagen del alienígena en el gráfico especificado.
     *
     * @param g el objeto {@code Graphics} en el que se dibuja el alienígena
     */
    public void dibujar(Graphics g) {
        if (activo) {
            g.drawImage(imagen, x, y, ancho, alto, null);
        }
    }

    /**
     * Obtiene los límites del alienígena como un rectángulo.
     *
     * @return un {@code Rectangle} que representa los límites del alienígena
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }

    /**
     * Verifica si el alienígena está activo.
     *
     * @return {@code true} si el alienígena está activo, {@code false} en caso contrario
     */
    public boolean estaActivo() {
        return activo;
    }

    /**
     * Establece el estado activo del alienígena.
     *
     * @param activo {@code true} para activar el alienígena, {@code false} para desactivarlo
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Obtiene la coordenada X del alienígena.
     *
     * @return la coordenada X del alienígena
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada Y del alienígena.
     *
     * @return la coordenada Y del alienígena
     */
    public int getY() {
        return y;
    }
}
