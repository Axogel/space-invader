package com.mycompany.space_invader;

import javax.swing.JButton;
import javax.swing.JFrame;
import Logica.Logica;
import java.awt.BorderLayout;

public class Space_Invader {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Space Invaders");
        Logica logica = new Logica();

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
    }
}