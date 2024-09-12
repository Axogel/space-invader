package Logica;

import Jugador.Jugador;
import Disparo.Disparo;
import Aliens.Aliens;
import Bloques.Bloques;
import Nivel.Nivel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.image.BufferStrategy;
import Nave.Nave; 
import Menu.Menu; 
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.Random;
/**
 * La clase {@code Logica} gestiona la lógica principal del juego, incluyendo el control del jugador, la actualización de los enemigos, 
 * la detección de colisiones, la gestión de disparos y la renderización de la interfaz gráfica.
 * <p>
 * Esta clase extiende {@code Canvas} y contiene el bucle principal del juego, maneja las entradas del teclado, y actualiza 
 * y dibuja los elementos del juego en pantalla.
 */
public class Logica extends Canvas {

    private final Jugador jugador;
    private boolean izquierdaPresionada = false;
    private boolean derechaPresionada = false;
    private final Disparo[] disparos;
    private int numDisparos = 0;
    private Aliens[] enemigos;
    private int numEnemigos;
    private Bloques[] bloques;
    private int numBloques;
    private boolean juegoEnPausa = false;
    private Timer timer;
    private int puntuacion;
    private Nivel nivelActual;
    private int nivel;
    private Timer naveTimer;
    private int numNaves = 0;
    private Nave[] naves;
    private Clip clipMusica;
    private Timer disparoEnemigosTimer;
    private final Disparo[] disparosEnemigos = new Disparo[1000];
    private int numDisparosEnemigos = 0;
    /**
     * Crea una nueva instancia de {@code Logica}. Inicializa el jugador, los disparos, enemigos, bloques, y naves,
     * y configura los temporizadores para la actualización del juego y la generación de naves.
     */
    public Logica() {
        jugador = new Jugador(400, 550);
        disparos = new Disparo[10];
        enemigos = new Aliens[0]; 
        bloques = new Bloques[0]; 
        naves = new Nave[2];

        numEnemigos = 0;
        numBloques = 0;
        nivel = 1;
        cargarNivel(nivel);
        puntuacion = 0;

        setBackground(Color.BLACK);
        setSize(800, 600);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!juegoEnPausa) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        izquierdaPresionada = true;
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        derechaPresionada = true;
                    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        disparar();
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_P) {
                    pausarJuego();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!juegoEnPausa) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        izquierdaPresionada = false;
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        derechaPresionada = false;
                    }
                }
            }
        });

        timer = new Timer(16, e -> actualizar());
        timer.start();
        naveTimer = new Timer(10000, e -> generarNave());
        naveTimer.start();
        disparoEnemigosTimer = new Timer(1000, e -> dispararEnemigos());
        disparoEnemigosTimer.start();
    }

    /**
     * Establece el clip de música que se reproducirá durante el juego.
     *
     * @param clip el clip de música a establecer
     */
    public void setClipMusica(Clip clip) {
        this.clipMusica = clip;
    }

    /**
     * Cierra el juego y detiene la reproducción de música si está en curso.
     */
    private void cerrarJuego() {
        if (clipMusica != null && clipMusica.isRunning()) {
            clipMusica.stop();
            clipMusica.close();
        }
        timer.stop();
        naveTimer.stop();
        disparoEnemigosTimer.stop();
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
        ventana.dispose();
        new Menu(); 
    }

    /**
     * Genera una nueva nave y la agrega al array de naves si hay espacio disponible.
     */
    private void generarNave() {
        if (numNaves < naves.length) {
            Nave nuevaNave = new Nave((int) (Math.random() * (getWidth() - 40)), 50);
            naves[numNaves] = nuevaNave;
            numNaves++;
        }
    }

    /**
     * Mueve todas las naves activas.
     */
    private void moverNaves() {
        for (int i = 0; i < numNaves; i++) {
            Nave nave = naves[i];
            if (nave != null && nave.estaActivo()) {
                nave.mover(); 
            }
        }
    }

    /**
     * Dibuja todas las naves activas en el gráfico especificado.
     *
     * @param g el objeto {@code Graphics} en el que se dibujan las naves
     */
    private void dibujarNaves(Graphics g) {
        for (Nave nave : naves) {
            if (nave != null && nave.estaActivo()) {
                nave.dibujar(g);
            }
        }
    }

    /**
     * Carga el nivel especificado y establece los enemigos y bloques correspondientes.
     *
     * @param nivel el número del nivel a cargar
     */
    private void cargarNivel(int nivel) {
        nivelActual = new Nivel(nivel);
        enemigos = nivelActual.getEnemigos();
        bloques = nivelActual.getBloques();
        numEnemigos = nivelActual.getNumEnemigos();
        numBloques = bloques.length;
    }

    /**
     * Verifica si el nivel actual ha sido completado y avanza al siguiente nivel o finaliza el juego.
     */
    private void verificarNivel() {
        if (numEnemigos <= 0) {
            nivel++;
            if (nivel > 3) {
                JOptionPane.showMessageDialog(this, "¡Has ganado el juego!");
                System.exit(0);
            } else {
                cargarNivel(nivel); 
            }
        }
    }

    /**
     * Elimina un enemigo del array de enemigos en la posición especificada.
     *
     * @param index el índice del enemigo a eliminar
     */
    private void eliminarEnemigo(int index) {
        for (int i = index; i < numEnemigos - 1; i++) {
            enemigos[i] = enemigos[i + 1];
        }
        enemigos[numEnemigos - 1] = null;
        numEnemigos--;
    }

    /**
     * Realiza los disparos de los enemigos si el juego no está en pausa.
     * Recorre la lista de enemigos y genera un nuevo disparo para aquellos que están activos.
     * Solo se permiten disparos si no se ha alcanzado el límite máximo de disparos.
     */
    private void dispararEnemigos() {
        if (!juegoEnPausa) {
            for (Aliens enemigo : enemigos) {
                if (enemigo != null && enemigo.estaActivo()) {
                    Random random = new Random();
                    int numeroAleatorio = random.nextInt(100); ///voy a hacer un numero random porque es muy dificil si todos disparan al mismo tiempo 
                    if (numDisparosEnemigos < disparosEnemigos.length && numeroAleatorio > 90) {
                        Disparo disparoEnemigo = new Disparo((int)Math.round(enemigo.getX() * 0.9 ) - Disparo.ANCHO / 2, enemigo.getY());
                        disparoEnemigo.setDirection(1); // Disparo hacia abajo
                        disparosEnemigos[numDisparosEnemigos] = disparoEnemigo;
                        numDisparosEnemigos++;
                    }
                }
            }
        }
    }

    /**
     * Mueve los disparos de los enemigos. Recorre todos los disparos activos y los mueve.
     * Si un disparo deja de estar activo, se elimina de la lista de disparos enemigos.
     */
    private void moverDisparosEnemigos() {
        for (int i = 0; i < numDisparosEnemigos; i++) {
            Disparo disparo = disparosEnemigos[i];
            if (disparo != null) {
                disparo.mover();
                if (!disparo.estaActivo()) {
                    eliminarDisparoEnemigo(i);
                }
            }
        }
    }

    /**
     * Detecta colisiones entre los disparos enemigos y el jugador o los bloques.
     * Si un disparo colisiona con el jugador, este pierde una vida y el disparo se elimina.
     * Si un disparo colisiona con un bloque, este puede destruirse o dañarse y el disparo se elimina.
     */
    private void detectarColisionesConDisparosEnemigos() {
        for (int i = 0; i < numDisparosEnemigos; i++) {
            Disparo disparo = disparosEnemigos[i];
            Rectangle boundsDisparo = disparo.getBounds();

            Rectangle boundsJugador = new Rectangle(jugador.getX(), jugador.getY(), jugador.getAncho(), 20);
            if (boundsDisparo.intersects(boundsJugador)) {
                jugador.perderVida();
                disparo.setActivo(false);
                eliminarDisparoEnemigo(i);
                continue;
            }

            for (int j = 0; j < numBloques; j++) {
                Bloques bloque = bloques[j];
                if (bloque != null && bloque.estaActivo() && boundsDisparo.intersects(bloque.getBounds())) {
                    bloque.destruir(); // O daño parcial si quieres que se destruya gradualmente
                    disparo.setActivo(false);
                    eliminarDisparoEnemigo(i);
                    break;
                }
            }
        }
    }

    /**
     * Elimina un disparo enemigo del arreglo de disparos en el índice especificado.
     * Los disparos posteriores se desplazan una posición hacia la izquierda.
     *
     * @param index El índice del disparo enemigo a eliminar.
     */
    private void eliminarDisparoEnemigo(int index) {
        for (int i = index; i < numDisparosEnemigos - 1; i++) {
            disparosEnemigos[i] = disparosEnemigos[i + 1];
        }
        disparosEnemigos[numDisparosEnemigos - 1] = null;
        numDisparosEnemigos--;
    }

    /**
     * Dibuja los disparos enemigos en la pantalla.
     * Recorre todos los disparos activos y llama al método de dibujo de cada disparo.
     *
     * @param g El contexto gráfico utilizado para dibujar los disparos.
     */
    private void dibujarDisparosEnemigos(Graphics g) {
        for (int i = 0; i < numDisparosEnemigos; i++) {
            if (disparosEnemigos[i] != null && disparosEnemigos[i].estaActivo()) {
                disparosEnemigos[i].dibujar(g);
            }
        }
    }



    /**
     * Verifica las colisiones entre el jugador y los enemigos. Si hay una colisión, el jugador pierde una vida
     * y el enemigo se desactiva.
     */
    private void verificarColisionesConEnemigos() {
        for (Aliens enemigo : enemigos) {
            if (enemigo != null && enemigo.estaActivo()) {
                Rectangle boundsEnemigo = enemigo.getBounds();
                Rectangle boundsJugador = new Rectangle(jugador.getX(), jugador.getY(), jugador.getAncho(), 20);
                
                if (boundsJugador.intersects(boundsEnemigo)) {
                    jugador.perderVida();
                    enemigo.setActivo(false);
                    puntuacion -= 50; 
                    break;
                }
            }
        }
    }

    /**
     * Detecta y maneja las colisiones entre disparos y otros objetos (enemigos, bloques, naves).
     */
    private void detectarColisiones() {
        for (int i = 0; i < numDisparos; i++) {
            Disparo disparo = disparos[i];
            Rectangle boundsDisparo = disparo.getBounds();

            for (int j = 0; j < numEnemigos; j++) {
                Aliens enemigo = enemigos[j];
                if (enemigo != null && enemigo.estaActivo()) {
                    Rectangle boundsEnemigo = enemigo.getBounds();
                    if (boundsDisparo.intersects(boundsEnemigo)) {
                        enemigo.setActivo(false);
                        disparo.setActivo(false);
                        puntuacion += enemigo.puntos; 
                        eliminarEnemigo(j);
                        break;
                    }
                }
            }

            for (int k = 0; k < numBloques; k++) {
                Bloques bloque = bloques[k];
                if (bloque != null && bloque.estaActivo() && boundsDisparo.intersects(bloque.getBounds())) {
                    bloque.destruir(); 
                    disparo.setActivo(false);
                    break;
                }
            }

            for (int l = 0; l < numNaves; l++) {
                Nave nave = naves[l];
                if (nave != null && nave.estaActivo()) {
                    Rectangle boundsNave = nave.getBounds();
                    if (boundsDisparo.intersects(boundsNave)) {
                        nave.setActivo(false); 
                        disparo.setActivo(false);
                        puntuacion += nave.puntos; 
                        break;
                    }
                }
            }
        }

        verificarColisionesConEnemigos();
        verificarNivel();
    }

    /**
     * Crea un nuevo disparo y lo añade al array de disparos si hay espacio disponible.
     */
    private void disparar() {
        if (numDisparos < disparos.length) {
            Disparo nuevoDisparo = new Disparo(jugador.getX() + jugador.getAncho() / 2 - Disparo.ANCHO / 2, jugador.getY() - Disparo.ALTO);
            disparos[numDisparos] = nuevoDisparo;
            numDisparos++;
        }
    }
      /**
     * Reanuda el juego con la logica de los timers y el Focus al juego para las telas
     */
    private void reanudarJuego() {
        juegoEnPausa = false;
        timer.start();
        naveTimer.start();
        disparoEnemigosTimer.start();
        
        // Asegurar que el Canvas tenga el foco
        requestFocusInWindow();
    }

    /**
     * Pausa o reanuda el juego, y muestra el menú de pausa donde se puede guardar la partida, reanudar el juego o salir.
     */
    public void pausarJuego() {
        juegoEnPausa = !juegoEnPausa;
        if (juegoEnPausa) {
            timer.stop();
            naveTimer.stop();
            disparoEnemigosTimer.stop();
            int opcion = mostrarMenuPausa();
            if (opcion == 0) { 
                reanudarJuego();
            } else if (opcion == 1) {
                guardarPartida();
                    reanudarJuego();

            } else if (opcion == 2) { 
                cerrarJuego();
            }

        }
    }

    /**
     * Muestra un menú de pausa con opciones para reanudar, guardar la partida o salir.
     *
     * @return el índice de la opción seleccionada en el menú de pausa
     */
    private int mostrarMenuPausa() {
        String[] opciones = {"Reanudar", "Guardar Partida", "Salir"};
        return JOptionPane.showOptionDialog(this, "Juego en Pausa", "Menu de Pausa",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
    }

    /**
     * Guarda el estado actual del juego en un archivo de texto.
     */
    private void guardarPartida() {
        try (FileWriter writer = new FileWriter("partida_guardada.txt")) {
            writer.write("Jugador X: " + jugador.getX() + "\n");
            for (int i = 0; i < enemigos.length; i++) {
                Aliens enemigo = enemigos[i];
                if (enemigo != null) {
                    writer.write("Enemigo " + i + " X: " + enemigo.getBounds().x + " Y: " + enemigo.getBounds().y + "\n");
                }
            }
            writer.write("Puntuación: " + puntuacion + "\n");
            writer.write("Vidas: " + jugador.getVidas() + "\n");
            JOptionPane.showMessageDialog(this, "Partida guardada exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el estado del juego, mueve los enemigos, los disparos y detecta colisiones.
     */
    private void actualizar() {
        if (!juegoEnPausa) {
            if (izquierdaPresionada && jugador.getX() > 0) {
                jugador.moverIzquierda();
            }
            if (derechaPresionada && jugador.getX() < getWidth() - jugador.getAncho()) {
                jugador.moverDerecha();
            }
            moverDisparosEnemigos(); 
            detectarColisionesConDisparosEnemigos();  
            if (jugador.getVidas() <= 0){
                        JOptionPane.showMessageDialog(this, "¡Se te han acabado las vidas! Juego Terminado.");        
                        cerrarJuego();
            } 
                for (Aliens enemigo : enemigos) {
                if (enemigo != null) {
                    enemigo.mover();
                }
            }

            for (int i = 0; i < numDisparos; i++) {
                Disparo disparo = disparos[i];
                if (disparo != null) {
                    disparo.mover();
                    if (!disparo.estaActivo()) {
                        eliminarDisparo(i);
                    }
                }
            }
            moverNaves();
            detectarColisiones();
            renderizar(); 
        }
    }

    /**
     * Elimina un disparo del array de disparos en la posición especificada.
     *
     * @param index el índice del disparo a eliminar
     */
    private void eliminarDisparo(int index) {
        for (int i = index; i < numDisparos - 1; i++) {
            disparos[i] = disparos[i + 1];
        }
        disparos[numDisparos - 1] = null;
        numDisparos--;
    }

    /**
     * Renderiza tanto el fondo como los disparos, vidas y al jugador
     */
    public void renderizar(){
    BufferStrategy buffer = getBufferStrategy();
    if (buffer == null) {
        createBufferStrategy(3); 
        return;
    }

    Graphics g = buffer.getDrawGraphics();

    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());

    jugador.dibujar(g);
    dibujarDisparosEnemigos(g);
    for (Aliens enemigo : enemigos) {
        if (enemigo != null && enemigo.estaActivo()) {
            enemigo.dibujar(g);
        }
    }
    for (int i = 0; i < numDisparos; i++) {
        if (disparos[i] != null) {
            disparos[i].dibujar(g);
        }
    }
    for (Bloques bloque : bloques) {
        if (bloque != null && bloque.estaActivo()) {
            bloque.dibujar(g);
        }
    }

    // Mostrar puntuación
    g.setColor(Color.WHITE);
    g.drawString("Puntuación: " + puntuacion, 10, 20);

    // Mostrar vidas usando el método dibujarVidas
    int xInicio = 10; // Posición horizontal de inicio para las vidas
    int yInicio = 500; // Posición vertical para las vidas
    jugador.dibujarVidas(g, xInicio, yInicio);

    g.dispose();
    buffer.show();
    }
}
