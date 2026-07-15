package gui;

import controller.Controller;
import model.Animale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

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
    private JLabel tutorialLabel;
    private JLabel impostazioniLabel;
    private ImageIcon backGroundImage;
    private Controller controller;

    public Impostazioni(JFrame tamagotchiFrame, Controller controller, Animale animale,Tamagotchi tamagotchi, JFrame frameHome, Home home) {
        JFrame impostazioniFrame = new JFrame("Impostazioni");
        impostazioniFrame.setContentPane(impostazioniPanel);
        impostazioniFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        impostazioniFrame.pack();
        impostazioniFrame.setSize(500, 330); //grandezza della finestra
        impostazioniFrame.setLocationRelativeTo(null); //finestra si apre al centro
        impostazioniFrame.setResizable(false); //non cambia dimensione
        this.controller = controller;

        CustomGUI.caricaIcona(impostazioniFrame);
        CustomGUI.caricaFont(impostazioniLabel,20f,true);

        impostazioniFrame.setVisible(true);

        CustomGUI.tornaIndietro(goBack,impostazioniFrame,tamagotchiFrame,true,tamagotchi);

        //gestione pulsante cambia nome animale
        confermaCambioNomeButton.addActionListener(e -> {
            try {
                String nome = inputNome.getText();
                controller.modificaNomeAnimale(nome,animale);
                JOptionPane.showMessageDialog(impostazioniFrame,
                        "Ora il tuo animale si chiama " + animale.getNome(),
                        "Nome cambiato",
                        JOptionPane.INFORMATION_MESSAGE);
                inputNome.setText("");
            } catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }catch(RuntimeException ex){
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
                    vecchiaPasswordField.setText("");
                    nuovaPasswordTextField.setText("");
                } catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //gestione pulsante elimina animale
        eliminaButton.addActionListener(e -> {
            try {
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
            } catch(SQLException ex){
                ex.printStackTrace();
            }

        });

        tamagotchiFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {// prima di chiudere l'app forzo l'animale a svegliarsi e salvo le informazioni relative (avviene nel metodo sveglia stesso)
                    if (animale.isDorme()) {
                        controller.sveglia(animale);
                    } else { //se non sta dormendo, mi occupo semplicemente di salvare
                        controller.salvaStatoAnimale(animale);
                    }
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Errore nel tentativo di salvare i dati nel Database.");
                    ex.printStackTrace();
                }
                finally {
                    System.exit(0);
                }
            }
        });
    }

    private void createUIComponents() {
        backGroundImage = new ImageIcon(Login.class.getResource("/img/backGroundDefault.png"));

        impostazioniPanel = new JPanel() {
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

        confermaCambioNomeButton = new JButton("Conferma") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        confermaCambioNomeButton.setContentAreaFilled(false);
        confermaCambioNomeButton.setFocusPainted(false);
        confermaCambioNomeButton.setOpaque(false);
        confermaCambioNomeButton.setForeground(Color.WHITE);

        confermaCambioPasswordButton = new JButton("Conferma") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        confermaCambioPasswordButton.setContentAreaFilled(false);
        confermaCambioPasswordButton.setFocusPainted(false);
        confermaCambioPasswordButton.setOpaque(false);
        confermaCambioPasswordButton.setForeground(Color.WHITE);

        eliminaButton = new JButton("Conferma") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        eliminaButton.setContentAreaFilled(false);
        eliminaButton.setFocusPainted(false);
        eliminaButton.setOpaque(false);
        eliminaButton.setForeground(Color.WHITE);
    }
}
