package gui;

import controller.Controller;
import model.Animale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Minigames {
    private JPanel minigamesPanel;
    private JLabel goBack;

    public Minigames(JFrame tamagotchiFrame, Controller controller, Animale animale){
        JFrame minigamesFrame = new JFrame("I tuoi items");
        minigamesFrame.setContentPane(minigamesPanel);
        minigamesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        minigamesFrame.pack();
        minigamesFrame.setSize(500, 300); //grandezza della finestra
        minigamesFrame.setLocationRelativeTo(null); //finestra si apre al centro
        minigamesFrame.setResizable(false); //non cambia dimensione
        minigamesFrame.setVisible(true);

        goBack.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        goBack.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                tamagotchiFrame.setVisible(true);
                minigamesFrame.setVisible(false);
            }
        });
    }
}
