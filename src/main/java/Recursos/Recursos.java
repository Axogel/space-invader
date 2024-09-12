package Recursos;

import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * La clase {@code Recursos} proporciona métodos para cargar imágenes desde archivos.
 * <p>
 * Esta clase está diseñada para simplificar la carga de recursos gráficos, como imágenes,
 * que se utilizan en la aplicación.
 */
public class Recursos {

    /**
     * Carga una imagen desde el archivo especificado.
     * <p>
     * Este método utiliza el nombre del archivo para crear un {@code ImageIcon} y devuelve la imagen
     * correspondiente. La imagen debe estar en el directorio correcto para que se cargue correctamente.
     *
     * @param nombreArchivo el nombre del archivo de la imagen a cargar, debe ser una ruta válida
     * @return la imagen cargada desde el archivo especificado
     */
    public static Image cargarImagen(String nombreArchivo) {
        ImageIcon icono = new ImageIcon(nombreArchivo);
        return icono.getImage();
    }
}
