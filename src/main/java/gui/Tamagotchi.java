package gui;

import controller.Controller;
import model.Animale;
import model.Orso;

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
    private JLabel immagineAnimale;
    private JButton negozioButton;
    private JButton minigameButton;
    private JButton itemButton;
    private JButton vaiADormireButton;
    private JLabel goBack;
    private JLabel test;
    private JButton impostazioniButton;
    private Animale animale;
    private Timer gameTime;

    public void aggiornaLabel(){
        labelPunti.setText(String.valueOf(animale.getPunti()));
        labelEnergia.setText(animale.getEnergia() + "/" + animale.getEnergiaMax());
        labelFame.setText(animale.getFame() + "/" + animale.getFameMax());
        labelNomeAnimale.setText(animale.getNome());
    }

    public Tamagotchi(JFrame frameHome, Controller controller, Animale animale, Home home) {
        JFrame tamagotchiFrame = new JFrame("Tamagotchi");
        tamagotchiFrame.setContentPane(tamagotchiPanel);
        tamagotchiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tamagotchiFrame.pack();
        tamagotchiFrame.setSize(500, 350); //grandezza della finestra
        tamagotchiFrame.setLocationRelativeTo(null); //finestra si apre al centro
        tamagotchiFrame.setResizable(false); //non cambia dimensione
        tamagotchiFrame.setVisible(true);
        this.animale = animale;

        //i valori dell'animale vengono resi visibili
        aggiornaLabel();
        //passo al controller la home e inizio il timer
        controller.setHomeFrame(this);
        controller.iniziaTimer(animale);

        //immagine dell'animale
        if(animale instanceof Orso){
            immagineAnimale.setIcon(new ImageIcon(
                    new ImageIcon(
                            getClass().getClassLoader().getResource("img/orsoProva.png")
                    ).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)
            ));
        }

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
                if(animale.isDorme()) {
                    JOptionPane.showMessageDialog(tamagotchiFrame,
                            animale.getNome() + " sta ancora dormendo!\nSveglialo per giocare ai minigames",
                            "Buonanotte",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
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
                if(animale.isDorme()) {
                    controller.sveglia(animale);
                    vaiADormireButton.setText("Vai a dormire");
                }
                else {
                    controller.addormenta(animale);
                    vaiADormireButton.setText("Svegliati");

                }
            }
        });

        //gestione pulsante impostazioni
        impostazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Impostazioni impostazioni = new Impostazioni(tamagotchiFrame, controller, animale, Tamagotchi.this, frameHome, home);
                tamagotchiFrame.setVisible(false);
            }
        });

        goBack.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        goBack.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(animale.isDorme())
                controller.sveglia(animale);
                controller.fermaTimer();
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
