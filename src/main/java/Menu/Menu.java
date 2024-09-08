package Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Logica.Logica;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException; 

public class Menu extends JFrame {

    public Menu() {
        setTitle("Menú Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 1, 10, 10));

        // Botón Nueva Partida
        JButton botonNuevaPartida = new JButton("Nueva Partida");
        botonNuevaPartida.setFont(new Font("Arial", Font.BOLD, 30));
        botonNuevaPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioNuevaPartida();
            }
        });

        // Botón Cargar Juego
        JButton botonCargarJuego = new JButton("Cargar Juego");
        botonCargarJuego.setFont(new Font("Arial", Font.BOLD, 30));
        botonCargarJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCargarJuego();
            }
        });

        // Botón Instrucciones
        JButton botonInstrucciones = new JButton("Instrucciones");
        botonInstrucciones.setFont(new Font("Arial", Font.BOLD, 30));
        botonInstrucciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInstrucciones();
            }
        });

        // Botón Créditos
        JButton botonCreditos = new JButton("Créditos");
        botonCreditos.setFont(new Font("Arial", Font.BOLD, 30));
        botonCreditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCreditos();
            }
        });

        // Añadir botones al panel
        panelMenu.add(botonNuevaPartida);
        panelMenu.add(botonCargarJuego);
        panelMenu.add(botonInstrucciones);
        panelMenu.add(botonCreditos);

        add(panelMenu);
        setVisible(true);
    }

    private void mostrarFormularioNuevaPartida() {
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel etiquetaNombre = new JLabel("Ingrese el nombre del jugador:");
        // Ajustar el tamaño del campo de texto
        JTextField campoNombre = new JTextField(20); // Cambia el tamaño del campo aquí
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
        public static void reproducirMusica(String rutaArchivo) {
        try {
            File archivoMusica = new File(rutaArchivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoMusica);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Repetir en bucle
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    private void iniciarJuego(String nombreJugador) {
               JFrame ventana = new JFrame("Space Invaders");
        Logica logica = new Logica();
        reproducirMusica("src/main/java/sounds/soundtrack.wav");


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

    private void mostrarCargarJuego() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de Cargar Juego aún no implementada.");
    }

    private void mostrarInstrucciones() {
        JOptionPane.showMessageDialog(this, "Instrucciones aún no implementadas.");
    }

    private void mostrarCreditos() {
        JOptionPane.showMessageDialog(this, "Créditos: Desarrolladores del juego.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu();
            }
        });
    }
}
