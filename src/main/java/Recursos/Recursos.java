package Recursos;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

public class Recursos {

    public static Image cargarImagen(String nombreArchivo) {
            ImageIcon icono = new ImageIcon(nombreArchivo);
            return icono.getImage();

    }
}
