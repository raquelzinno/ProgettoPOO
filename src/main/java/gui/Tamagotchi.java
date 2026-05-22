package gui;

import controller.Controller;
import model.Animale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tamagotchi {
    private JPanel tamagotchiPanel;
    private JLabel labelEnergia;
    private JLabel labelFame;
    private JLabel labelPunti;
    private JLabel labelNomeAnimale;
    private JLabel ImmagineAnimale;
    private JButton negozioButton;
    private JButton minigameButton;
    private JButton itemButton;
    private JButton vaiADormireButton;
    private JLabel goBack;


    public Tamagotchi(  JFrame frameHome, Controller controller, Animale animale) {
        JFrame tamagotchiFrame = new JFrame(animale.getNome());
        tamagotchiFrame.setContentPane(tamagotchiPanel);
        tamagotchiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tamagotchiFrame.pack();
        tamagotchiFrame.setSize(500, 300); //grandezza della finestra
        tamagotchiFrame.setLocationRelativeTo(null); //finestra si apre al centro
        tamagotchiFrame.setResizable(false); //non cambia dimensione
        tamagotchiFrame.setVisible(true);

        //i valori dell'animale vengono resi visibili
        labelPunti.setText(String.valueOf(animale.getPunti()));
        labelEnergia.setText(String.valueOf(animale.getEnergia()) + "/" + String.valueOf(animale.getEnergiaMax()));
        labelFame.setText(String.valueOf(animale.getFame()) + "/" + String.valueOf(animale.getFameMax()));
        labelNomeAnimale.setText(animale.getNome());

        negozioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Negozio negozio = new Negozio(tamagotchiFrame, controller, animale);
                tamagotchiFrame.setVisible(false);
            }
        });

        goBack.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        goBack.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                frameHome.setVisible(true);
                tamagotchiFrame.setVisible(false);
            }
        });

    }
}
