package gui;

import controller.Controller;
import model.Animale;
import model.Minigame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Minigames {
    private JPanel minigamesPanel;
    private JLabel goBack;
    private JTabbedPane tabbedPane1;
    private JPanel minigame1Panel;
    private JPanel minigame2Panel;
    private JRadioButton sassoRadioButton;
    private JRadioButton cartaRadioButton;
    private JRadioButton forbiciRadioButton;
    private JButton okButton1;
    private JLabel labelEnergia1;
    private JLabel labelPunti1;
    private JButton okButton2;
    private JRadioButton testaRadioButton;
    private JRadioButton croceRadioButton;
    private JLabel labelEnergia2;
    private JLabel labelPunti2;
    private JPanel minigame3Panel;
    private JLabel labelEnergia3;
    private JLabel labelPunti3;
    private JButton okButton3;
    private Minigame minigame1;
    private Minigame minigame2;
    private Minigame minigame3;
    private ImageIcon backGroundImage;

    public Minigames(JFrame tamagotchiFrame, Controller controller, Animale animale, Tamagotchi tamagotchi){
        JFrame minigamesFrame = new JFrame("Minigames");
        minigamesFrame.setContentPane(minigamesPanel);
        minigamesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        minigamesFrame.pack();
        minigamesFrame.setSize(500, 300); //grandezza della finestra
        minigamesFrame.setLocationRelativeTo(null); //finestra si apre al centro
        minigamesFrame.setResizable(false); //non cambia dimensione
        minigamesFrame.setVisible(true);

        //icona della finestra
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/tamagotchiIcon.png"));
        minigamesFrame.setIconImage(icon.getImage());

        //copio il riferimento ai minigame per rendere il codice più pulito
        minigame1 = (controller.getMinigamesDiDefault()).get(0);
        minigame2 = (controller.getMinigamesDiDefault()).get(1);
        minigame3 = (controller.getMinigamesDiDefault()).get(2);

        //aggiorno i label e i titoli con i valori necessari
        tabbedPane1.setTitleAt(tabbedPane1.indexOfComponent(minigame1Panel), minigame1.getNomeGioco());
        tabbedPane1.setTitleAt(tabbedPane1.indexOfComponent(minigame2Panel), minigame2.getNomeGioco());
        tabbedPane1.setTitleAt(tabbedPane1.indexOfComponent(minigame3Panel), minigame3.getNomeGioco());
        labelEnergia1.setText(String.valueOf(minigame1.getEnergiaConsumata()));
        labelPunti1.setText(String.valueOf(minigame1.getRicompensa()));
        labelEnergia2.setText(String.valueOf(minigame2.getEnergiaConsumata()));
        labelPunti2.setText(String.valueOf(minigame2.getRicompensa()));
        labelEnergia3.setText(String.valueOf(minigame3.getEnergiaConsumata()));
        labelPunti3.setText(String.valueOf(minigame3.getRicompensa()));

        //MINIGAME1 --------------------------------------------------------------------------------------------

        //pulsanti minigame1
        ButtonGroup gruppoMinigame1 = new ButtonGroup();
        gruppoMinigame1.add(sassoRadioButton);
        gruppoMinigame1.add(cartaRadioButton);
        gruppoMinigame1.add(forbiciRadioButton);

        okButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String manoUtente = "";
                    if (sassoRadioButton.isSelected()) {
                        manoUtente = "sasso";
                    } else if (cartaRadioButton.isSelected()) {
                        manoUtente = "carta";
                    } else if (forbiciRadioButton.isSelected()) {
                        manoUtente = "forbici";
                    }
                    String manoAvversaria = controller.casualeSassoCartaForbici();
                    String esito = controller.giocaSassoCartaForbici(minigame1, manoUtente, manoAvversaria, animale);

                    JOptionPane.showMessageDialog(null, "Hai usato " + manoUtente
                            + "\nL'avversario ha usato " + manoAvversaria + "."
                            + "\nHai " + esito + "!");
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //MINIGAME2 --------------------------------------------------------------------------------------------

        ButtonGroup gruppoMinigame2 = new ButtonGroup();
        gruppoMinigame2.add(testaRadioButton);
        gruppoMinigame2.add(croceRadioButton);

        okButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String inputUtente = "";
                    if (testaRadioButton.isSelected()) {
                        inputUtente = "testa";
                    } else if (croceRadioButton.isSelected()) {
                        inputUtente = "croce";
                    }
                    String risultatoLancio = controller.casualeLancioMoneta();
                    String esito = controller.giocaLancioMoneta(minigame2, inputUtente, risultatoLancio, animale);

                    JOptionPane.showMessageDialog(null, "Hai scommesso su " + inputUtente
                            + "\nLa moneta è caduta su " + risultatoLancio + "."
                            + "\nHai " + esito + "!");
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //MINIGAME3 --------------------------------------------------------------------------------------------

        okButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int slot1 = controller.casualeSlotMachine();
                    int slot2 = controller.casualeSlotMachine();
                    int slot3 = controller.casualeSlotMachine();
                    String esito = controller.giocaSlotMachine(minigame3, animale, slot1, slot2, slot3);
                    JOptionPane.showMessageDialog(null, "E' uscito:  " + slot1 + " " + slot2 + " " + slot3
                            + "\nHai " + esito + "!");
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //gestione pulsante indietro
        goBack.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        goBack.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                tamagotchi.aggiornaLabel();
                tamagotchiFrame.setVisible(true);
                minigamesFrame.setVisible(false);
            }
        });


    }

    private void createUIComponents() {
        backGroundImage = new ImageIcon(Login.class.getResource("/img/backGroundMinigames.png"));

        minigamesPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backGroundImage != null) {
                    g.drawImage(backGroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        ImageIcon buttonImage = new ImageIcon(
                Login.class.getResource("/img/buttonBackground.png")
        );

        okButton1 = new JButton("Gioca!") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        okButton1.setContentAreaFilled(false);
        okButton1.setFocusPainted(false);
        okButton1.setOpaque(false);
        okButton1.setForeground(Color.WHITE);

        okButton2 = new JButton("Lancia!") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        okButton2.setContentAreaFilled(false);
        okButton2.setFocusPainted(false);
        okButton2.setOpaque(false);
        okButton2.setForeground(Color.WHITE);

        okButton3 = new JButton("Tira la leva!") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        okButton3.setContentAreaFilled(false);
        okButton3.setFocusPainted(false);
        okButton3.setOpaque(false);
        okButton3.setForeground(Color.WHITE);

    }
}
