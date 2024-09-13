package Disparo;

import Recursos.Recursos;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * La clase {@code Disparo} representa un disparo en el juego.
 * <p>
 * Un disparo tiene una posición en la pantalla, una imagen asociada y un estado de actividad. Se puede dibujar en la pantalla y mover hacia arriba.
 */
public class Disparo {
    
    /** El ancho del disparo en píxeles. */
    public static final int ANCHO = 5;

    /** La altura del disparo en píxeles. */
    public static final int ALTO = 10;

    /** La posición horizontal del disparo en la pantalla. */
    private int x;

    /** La posición vertical del disparo en la pantalla. */
    private int y;

    /** Indica si el disparo está activo o no. */
    private boolean activo;

    /** La imagen que representa el disparo. */
    private final Image imagen;
      /** Dirección del disparo: -1 para arriba, 1 para abajo */
    private int direccion = -1;

    /**
     * Crea un nuevo {@code Disparo} en la posición especificada.
     * <p>
     * Inicializa la posición del disparo, establece su estado como activo y carga la imagen del disparo. Además, reproduce el sonido del disparo.
     *
     * @param x la posición horizontal inicial del disparo
     * @param y la posición vertical inicial del disparo
     */
    public Disparo(int x, int y) {
        this.x = x;
        this.y = y;
        this.activo = true;

        this.imagen = Recursos.cargarImagen("bullet.png");
        reproducirSonido("src/main/java/sounds/shot.wav");
    }

    /**
     * Dibuja el disparo en la pantalla.
     * <p>
     * Este método utiliza la imagen del disparo para dibujarlo en la posición actual, siempre y cuando el disparo esté activo.
     *
     * @param g el objeto {@code Graphics} que se usa para dibujar el disparo
     */
    public void dibujar(Graphics g) {
        if (activo) {
            g.drawImage(imagen, x, y, ANCHO, ALTO, null);
        }
    }
    /**
     * Establece la dirección del disparo.
     * 
     * @param direccion -1 para disparo hacia arriba, 1 para disparo hacia abajo.
     */
    public void setDirection(int direccion) {
        this.direccion = direccion;
    }
    /**
     * Mueve el disparo hacia la direccion en la pantalla.
     * <p>
     * Este método actualiza la posición vertical del disparo y desactiva el disparo si se sale de la pantalla.
     */
    public void mover() {
        y += 5 * direccion;
        if (y < 0) {
            activo = false;
        }
    }

    /**
     * Obtiene el rectángulo delimitador del disparo.
     * <p>
     * El rectángulo delimitador se utiliza para detectar colisiones con otros objetos en la pantalla.
     *
     * @return un {@code Rectangle} que representa el área del disparo
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, ANCHO, ALTO);
    }

    /**
     * Verifica si el disparo está activo.
     * 
     * @return {@code true} si el disparo está activo; {@code false} en caso contrario
     */
    public boolean estaActivo() {
        return activo;
    }

    /**
     * Establece el estado de actividad del disparo.
     * 
     * @param activo {@code true} para activar el disparo; {@code false} para desactivarlo
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Reproduce un sonido especificado por la ruta del archivo.
     * <p>
     * Este método carga y reproduce un archivo de sonido.
     *
     * @param rutaArchivo la ruta del archivo de sonido a reproducir
     */
    public void reproducirSonido(String rutaArchivo) {
        try {
            File archivoSonido = new File(rutaArchivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoSonido);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    } 
}
