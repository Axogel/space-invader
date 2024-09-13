package Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Logica.Logica;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * La clase {@code Menu} representa el menú principal del juego. 
 * Permite al usuario iniciar una nueva partida, cargar un juego guardado, ver instrucciones y créditos.
 * <p>
 * Esta clase extiende {@code JFrame} y configura una ventana con botones para las diferentes opciones del menú.
 */
public class Menu extends JFrame {
    private Clip clipMusica;

    /**
     * Crea una nueva instancia de {@code Menu} y configura la interfaz de usuario.
     */
    public Menu() {
        setTitle("Menú Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 1, 10, 10));

        JButton botonNuevaPartida = new JButton("Nueva Partida");
        botonNuevaPartida.setFont(new Font("Arial", Font.BOLD, 30));
        botonNuevaPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioNuevaPartida();
            }
        });

        JButton botonCargarJuego = new JButton("Cargar Juego");
        botonCargarJuego.setFont(new Font("Arial", Font.BOLD, 30));
        botonCargarJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCargarJuego();
            }
        });

        JButton botonInstrucciones = new JButton("Instrucciones");
        botonInstrucciones.setFont(new Font("Arial", Font.BOLD, 30));
        botonInstrucciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInstrucciones();
            }
        });

        JButton botonCreditos = new JButton("Créditos");
        botonCreditos.setFont(new Font("Arial", Font.BOLD, 30));
        botonCreditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCreditos();
            }
        });

        panelMenu.add(botonNuevaPartida);
        panelMenu.add(botonCargarJuego);
        panelMenu.add(botonInstrucciones);
        panelMenu.add(botonCreditos);

        add(panelMenu);
        setVisible(true);
    }

    /**
     * Muestra un formulario para que el jugador ingrese su nombre y comience una nueva partida.
     * <p>
     * La ventana de formulario permite ingresar el nombre del jugador y contiene un botón para iniciar el juego.
     */
    private void mostrarFormularioNuevaPartida() {
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel etiquetaNombre = new JLabel("Ingrese el nombre del jugador:");
        JTextField campoNombre = new JTextField(20); 
        JButton botonJugar = new JButton("Jugar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(etiquetaNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(campoNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(botonJugar, gbc);

        JFrame ventanaFormulario = new JFrame("Nueva Partida");
        ventanaFormulario.setSize(350, 200); // Ajustar el tamaño de la ventana
        ventanaFormulario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaFormulario.setLocationRelativeTo(this);
        ventanaFormulario.add(panelFormulario);
        ventanaFormulario.setVisible(true);

        botonJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreJugador = campoNombre.getText();
                if (nombreJugador.isEmpty()) {
                    JOptionPane.showMessageDialog(ventanaFormulario, "El nombre del jugador no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                iniciarJuego(nombreJugador);
                ventanaFormulario.dispose();
            }
        });
    }

    /**
     * Reproduce una pista de música en bucle.
     * <p>
     * Abre el archivo de audio especificado y reproduce el clip en bucle continuo.
     *
     * @param rutaArchivo la ruta del archivo de música a reproducir
     * @return el clip de música reproducido
     */
    public static Clip reproducirMusica(String rutaArchivo) {
        Clip clip = null;
        try {
            File archivoMusica = new File(rutaArchivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoMusica);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Repetir en bucle
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }

    /**
     * Inicia el juego con el nombre del jugador especificado.
     * <p>
     * Crea una nueva ventana del juego y la inicializa con la lógica del juego. También configura la reproducción de música.
     *
     * @param nombreJugador el nombre del jugador que iniciará el juego
     */
    private void iniciarJuego(String nombreJugador) {
        JFrame ventana = new JFrame("Space Invaders");
        Logica logica = new Logica();
        Clip clipMusica = reproducirMusica("src/main/java/sounds/soundtrack.wav");
        logica.setClipMusica(clipMusica);

        JButton botonPausa = new JButton("Pausa");
        botonPausa.setSize(100, 30);

        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(null); 

        botonPausa.setBounds(700, 10, 80, 30);

        botonPausa.addActionListener(e -> logica.pausarJuego());

        ventana.add(botonPausa);
        ventana.add(logica);

        ventana.setVisible(true);

        logica.requestFocusInWindow();
        this.dispose();
    }

    /**
     * Muestra un mensaje informativo sobre la funcionalidad de cargar un juego.
     * <p>
     * Actualmente, la funcionalidad de carga de juego no está implementada.
     */
    private void mostrarCargarJuego() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de Cargar Juego aún no implementada.");
    }

    /**
     * Muestra un mensaje con las instrucciones del juego.
     * <p>
     * Proporciona detalles sobre los controles y el objetivo del juego.
     */
    private void mostrarInstrucciones() {
        String instrucciones = "Instrucciones de Space Invaders:\n\n"
                             + "- Usa las flechas izquierda y derecha para mover tu nave.\n"
                             + "- Presiona la barra espaciadora para disparar a los enemigos.\n"
                             + "- Presiona 'P' para pausar el juego.\n"
                             + "- Tu objetivo es derrotar a todos los enemigos antes de que toquen tus defensas.\n"
                             + "- ¡Buena suerte!";
                             
        JOptionPane.showMessageDialog(this, instrucciones, "Instrucciones", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje con los créditos del juego.
     * <p>
     * Proporciona el nombre del creador del juego o información relevante de créditos.
     */
    private void mostrarCreditos() {
        JOptionPane.showMessageDialog(this, "Créditos: Jean Carlos Contreras y Angel Molina Ortiz ");
    }

    /**
     * Método principal que inicia la aplicación del menú.
     * <p>
     * Llama al constructor de la clase {@code Menu} en el hilo de eventos de Swing.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu();
            }
        });
    }
}
