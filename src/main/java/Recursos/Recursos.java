package Recursos;

import javax.swing.ImageIcon;
import java.awt.Image;

public class Recursos {

    /**
     * Carga una imagen desde el archivo especificado.
     *
     * @param nombreArchivo el nombre del archivo de la imagen a cargar
     * @return la imagen cargada desde el archivo especificado
     */
    public static Image cargarImagen(String nombreArchivo) {
        // Usa /Imagenes/ si tu imagen está en src/main/resources/Imagenes/
        java.net.URL url = Recursos.class.getResource("/Imagenes/" + nombreArchivo);
        if (url == null) {
            throw new RuntimeException("No se pudo encontrar el recurso: /Imagenes/" + nombreArchivo);
        }
        ImageIcon icono = new ImageIcon(url);
        return icono.getImage();
    }
}
