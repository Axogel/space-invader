package Recursos;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

public class Recursos {

    public static Image cargarImagen(String nombreArchivo) {
        URL url = Recursos.class.getResource("/" + nombreArchivo);
        if (url != null) {
            ImageIcon icono = new ImageIcon(url);
            return icono.getImage();
        } else {
            System.err.println("No se pudo cargar la imagen: " + nombreArchivo);
            return null;
        }
    }
}
