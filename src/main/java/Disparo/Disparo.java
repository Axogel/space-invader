package Disparo;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;

public class Disparo {
    
    public static final int ANCHO = 5;
    public static final int ALTO = 10;
    private int x;
    private int y;
    private boolean activo;
    private final Image imagen;

    public Disparo(int x, int y) {
        this.x = x;
        this.y = y;
        this.activo = true;

        this.imagen = new ImageIcon("src/main/java/Imagenes/bullet.png").getImage();
        reproducirSonido("src/main/java/sounds/shot.wav");

    }

    public void dibujar(Graphics g) {
        if (activo) {
            g.drawImage(imagen, x, y, ANCHO, ALTO, null);
        }
    }

    public void mover() {
        y -= 5;
        if (y < 0) {
            activo = false;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ANCHO, ALTO);
    }

    public boolean estaActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }   
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
