package com.mycompany.space_invader;

import javax.swing.JFrame;
import Logica.Logica;

public class Space_Invader {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Space Invaders");
        Logica logica = new Logica();
        
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.add(logica);
        ventana.setVisible(true);
        
        logica.requestFocusInWindow();
    }
}
