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
    private JLabel test;
    private Animale animale;

    public void aggiornaLabel(){

        labelPunti.setText(
                String.valueOf(animale.getPunti())
        );

        labelEnergia.setText(
                animale.getEnergia() + "/" +
                        animale.getEnergiaMax()
        );

        labelFame.setText(
                animale.getFame() + "/" +
                        animale.getFameMax()
        );
    }

    public Tamagotchi(JFrame frameHome, Controller controller, Animale animale) {
        JFrame tamagotchiFrame = new JFrame(animale.getNome());
        tamagotchiFrame.setContentPane(tamagotchiPanel);
        tamagotchiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tamagotchiFrame.pack();
        tamagotchiFrame.setSize(500, 300); //grandezza della finestra
        tamagotchiFrame.setLocationRelativeTo(null); //finestra si apre al centro
        tamagotchiFrame.setResizable(false); //non cambia dimensione
        tamagotchiFrame.setVisible(true);
        this.animale = animale;

        //i valori dell'animale vengono resi visibili
        aggiornaLabel();

        /*
        labelPunti.setText(String.valueOf(animale.getPunti()));
        labelEnergia.setText(String.valueOf(animale.getEnergia()) + "/" + String.valueOf(animale.getEnergiaMax()));
        labelFame.setText(String.valueOf(animale.getFame()) + "/" + String.valueOf(animale.getFameMax()));
        labelNomeAnimale.setText(animale.getNome());*/

        //gestisce il pulsante negozio
        negozioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NegozioPrincipale negozio = new NegozioPrincipale(tamagotchiFrame, controller, animale, Tamagotchi.this);
                tamagotchiFrame.setVisible(false);
            }
        });

        //gestisce il pulsante minigame
        minigameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Minigames minigames = new Minigames(tamagotchiFrame, controller, animale);
                tamagotchiFrame.setVisible(false);
            }
        });

        //gestisce il pulsante item
        itemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Items items = new Items(tamagotchiFrame, controller, animale, Tamagotchi.this);
                tamagotchiFrame.setVisible(false);
            }
        });

        //gestisce il pulsante dormi
        vaiADormireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dorme
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

        //test
        test.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                animale.setEnergia(5);
                animale.setFame(5);
                aggiornaLabel();
            }
        });
    }
}
