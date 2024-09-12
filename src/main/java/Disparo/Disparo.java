package Disparo;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;

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

        this.imagen = new ImageIcon("src/main/java/Imagenes/bullet.png").getImage();
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
     * Mueve el disparo hacia arriba en la pantalla.
     * <p>
     * Este método actualiza la posición vertical del disparo y desactiva el disparo si se sale de la pantalla.
     */
    public void mover() {
        y -= 5;
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
