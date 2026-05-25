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
    private JPanel minigame3Panel;
    private JRadioButton sassoRadioButton;
    private JRadioButton cartaRadioButton;
    private JRadioButton forbiciRadioButton;
    private JButton okButton;
    private JLabel labelEnergia1;
    private JLabel labelPunti1;
    private Minigame minigame1;
    private Minigame minigame2;
    private Minigame minigame3;


    public Minigames(JFrame tamagotchiFrame, Controller controller, Animale animale, Tamagotchi tamagotchi){
        JFrame minigamesFrame = new JFrame("Minigames");
        minigamesFrame.setContentPane(minigamesPanel);
        minigamesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        minigamesFrame.pack();
        minigamesFrame.setSize(500, 300); //grandezza della finestra
        minigamesFrame.setLocationRelativeTo(null); //finestra si apre al centro
        minigamesFrame.setResizable(false); //non cambia dimensione
        minigamesFrame.setVisible(true);

        //copio il riferimento ai minigame per rendere il codice più pulito
        minigame1 = (controller.getMinigamesDiDefault()).get(0);

        tabbedPane1.setTitleAt(tabbedPane1.indexOfComponent(minigame1Panel), minigame1.getNomeGioco());

        labelEnergia1.setText(String.valueOf(minigame1.getEnergiaConsumata()));
        labelPunti1.setText(String.valueOf(minigame1.getRicompensa()));

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String manoUtente = "";
                if (sassoRadioButton.isSelected()) {
                    manoUtente = "sasso";
                } else if (cartaRadioButton.isSelected()) {
                    manoUtente = "carta";
                }
                else if (forbiciRadioButton.isSelected()) {
                    manoUtente = "forbici";
                }
                String manoAvversaria = controller.casualeSassoCartaForbici();
                String esito = controller.giocaSassoCartaForbici(minigame1, manoUtente, manoAvversaria, animale);

                    JOptionPane.showMessageDialog(null, "Hai usato " + manoUtente
                            + "\nL'avversario ha usato " + manoAvversaria
                            + "\nHai " + esito + " !");
            }
        });

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
}
