package com.mycompany.space_invader;

import Menu.Menu;

/**
 * La clase {@code Space_Invader} es el punto de entrada principal de la aplicación.
 * <p>
 * Esta clase contiene el método {@code main}, que se ejecuta cuando se inicia la aplicación.
 * Su función principal es mostrar el menú principal del juego.
 */
public class Space_Invader {

    /**
     * El método principal de la aplicación.
     * <p>
     * Este método se ejecuta al iniciar la aplicación y crea una instancia del menú principal.
     * Esto permite al usuario interactuar con el menú para iniciar una nueva partida, cargar
     * una partida guardada, ver instrucciones, o consultar los créditos.
     *
     * @param args los argumentos de la línea de comandos (no se utilizan en esta aplicación)
     */
    public static void main(String[] args) {
        // Mostrar el menú principal
        Menu menu = new Menu();
    }
}
