package gui;

import controller.Controller;
import model.Animale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Impostazioni {
    private JPanel impostazioniPanel;
    private JLabel goBack;
    private JTabbedPane tabbedPane1;
    private JTextField inputNome;
    private JButton confermaCambioNomeButton;
    private JPasswordField vecchiaPasswordField;
    private JTextField nuovaPasswordTextField;
    private JButton confermaCambioPasswordButton;
    private JCheckBox controlloEliminaCheckBox;
    private JButton eliminaButton;
    private JTextArea tutorialTextArea;

    public Impostazioni(JFrame tamagotchiFrame, Controller controller, Animale animale,Tamagotchi tamagotchi, JFrame frameHome, Home home) {
        JFrame impostazioniFrame = new JFrame("Impostazioni");
        impostazioniFrame.setContentPane(impostazioniPanel);
        impostazioniFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        impostazioniFrame.pack();
        impostazioniFrame.setSize(500, 300); //grandezza della finestra
        impostazioniFrame.setLocationRelativeTo(null); //finestra si apre al centro
        impostazioniFrame.setResizable(false); //non cambia dimensione
        impostazioniFrame.setVisible(true);

        tutorialTextArea.setEditable(false);

        //gestione pulsante cambia nome animale
        confermaCambioNomeButton.addActionListener(e -> {
            try {
                String nome = inputNome.getText();
                animale.setNome(nome);
                JOptionPane.showMessageDialog(impostazioniFrame,
                        "Ora il tuo animale si chiama " + animale.getNome(),
                        "Nome cambiato",
                        JOptionPane.INFORMATION_MESSAGE);
                inputNome.setText("");
            } catch(RuntimeException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        //gestione pulsante cambia password
        confermaCambioPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.cambiaPassword(vecchiaPasswordField.getText(), nuovaPasswordTextField.getText());
                    JOptionPane.showMessageDialog(impostazioniFrame,
                            "Password cambiata con successo.",
                            "Password cambiata",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //gestione pulsante elimina animale
        eliminaButton.addActionListener(e -> {
            if (controlloEliminaCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(impostazioniFrame,
                        "Hai rimosso " + animale.getNome() + ".\nVerrai rimandato alla home.",
                        "Animale eliminato",
                        JOptionPane.INFORMATION_MESSAGE);
                controller.eliminaAnimale(animale);
                home.rimuoviAnimale(animale);
                frameHome.setVisible(true);
                impostazioniFrame.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(impostazioniFrame,
                        "Devi prima selezionare la casella per poter procedere!",
                        "Attenzione",
                        JOptionPane.WARNING_MESSAGE);
            }

        });


        //gestione pulsante indietro
        goBack.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        goBack.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                tamagotchi.aggiornaLabel();
                tamagotchiFrame.setVisible(true);
                impostazioniFrame.setVisible(false);
            }
        });
    }
}
