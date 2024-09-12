package Nave;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import Recursos.Recursos;

/**
 * La clase {@code Nave} representa una nave que se mueve en el juego.
 * <p>
 * La nave se mueve horizontalmente con una velocidad aleatoria y cambia de dirección
 * cuando llega al borde de la pantalla. También se mueve verticalmente hacia abajo
 * después de cambiar de dirección. La nave tiene una imagen y un valor en puntos
 * asociado que puede ser utilizado para la puntuación.
 */
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

    /**
     * Crea una nueva instancia de la nave con una posición inicial.
     * <p>
     * La nave se inicializa en la posición especificada y tiene una velocidad
     * horizontal aleatoria. El valor en puntos se establece aleatoriamente entre 100 y 199.
     *
     * @param inicioX la coordenada x inicial de la nave
     * @param inicioY la coordenada y inicial de la nave
     */
    public Nave(int inicioX, int inicioY) {
        this.x = inicioX;
        this.y = inicioY;
        this.activo = true;
        this.random = new Random();
        this.imagen = Recursos.cargarImagen("src/main/java/Imagenes/naveespacial.png");
        this.puntos = 100 + random.nextInt(100); 

        this.dx = random.nextInt(3) + 1; // Velocidad horizontal aleatoria
    }

    /**
     * Mueve la nave horizontalmente. Cambia la dirección horizontal cuando la nave
     * alcanza el borde de la pantalla y se mueve verticalmente hacia abajo.
     */
    public void mover() {
        x += dx;
        if (x > 800 - ancho || x < 0) {
            dx = -(random.nextInt(5) + 1); // Velocidad aleatoria negativa para moverse en sentido contrario
            y += dy;
        }
    }

    /**
     * Dibuja la nave en el gráfico proporcionado.
     * <p>
     * Solo se dibuja la nave si está activa.
     *
     * @param g el objeto {@link Graphics} utilizado para dibujar la nave
     */
    public void dibujar(Graphics g) {
        if (activo) {
            g.drawImage(imagen, x, y, ancho, alto, null);
        }
    }

    /**
     * Obtiene los límites de la nave en forma de un rectángulo.
     * <p>
     * El rectángulo se usa para detectar colisiones con otros objetos.
     *
     * @return un objeto {@link Rectangle} que representa los límites de la nave
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }

    /**
     * Verifica si la nave está activa.
     * 
     * @return {@code true} si la nave está activa, {@code false} de lo contrario
     */
    public boolean estaActivo() {
        return activo;
    }

    /**
     * Establece el estado de la nave como activa o inactiva.
     * 
     * @param activo {@code true} para activar la nave, {@code false} para desactivarla
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    /**
     * Obtiene la coordenada x de la nave.
     * 
     * @return la coordenada x de la nave
     */
    public int getX() {
        return x;
    }
    
    /**
     * Obtiene la coordenada y de la nave.
     * 
     * @return la coordenada y de la nave
     */
    public int getY() {
        return y;
    }
}
