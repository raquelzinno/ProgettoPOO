package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreaAnimale {
    private JRadioButton orsoRadioButton;
    private JPanel creaAnimalePanel;
    private JRadioButton pinguinoRadioButton;
    private JTextField nomeTextField;
    private JButton okButton;
    private JLabel goBack;
    private ImageIcon backGroundImage;
    private Controller controller;

    /**
     * Crea una nuova istanza della finestra di creazione dell'animale.
     * Inizializza l'interfaccia, configura i listener per il pulsante di conferma
     * e prepara la finestra per l'input dell'utente.
     *
     * @param frameHome il frame della pagina Home
     * @param controller il controller principale che gestisce la logica di sistema.
     */
    public CreaAnimale(JFrame frameHome, Controller controller){
        JFrame creaAnimaleFrame = new JFrame("Crea un nuovo animale");
        creaAnimaleFrame.setContentPane(creaAnimalePanel);
        creaAnimaleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        creaAnimaleFrame.pack();
        creaAnimaleFrame.setSize(400, 300); //grandezza della finestra
        creaAnimaleFrame.setLocationRelativeTo(null); //finestra si apre al centro
        creaAnimaleFrame.setResizable(false); //non cambia dimensione
        this.controller = controller;

        CustomGUI.caricaIcona(creaAnimaleFrame);

        creaAnimaleFrame.setVisible(true);

        CustomGUI.tornaIndietro(goBack,creaAnimaleFrame,frameHome,true);

        //pulsanti del tipo
        ButtonGroup gruppoAnimali = new ButtonGroup();
        gruppoAnimali.add(orsoRadioButton);
        gruppoAnimali.add(pinguinoRadioButton);

        //gestione pulsante ok
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String tipo = "";
                    if (orsoRadioButton.isSelected()) {
                        tipo = "Orso";
                    } else if (pinguinoRadioButton.isSelected()) {
                        tipo = "Pinguino";
                    }
                    String nome = nomeTextField.getText();

                    controller.creaAnimale(tipo, nome);

                    JOptionPane.showMessageDialog(null, "Animale creato con successo.");

                    Home.modelloListaAnimali.addElement(controller.getUtenteAttuale().getAnimaliPosseduti().getLast());

                    //torna alla home
                    frameHome.setVisible(true);
                    creaAnimaleFrame.setVisible(false);
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Non è stato possibile creare l'animale nel Database: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() { //custom create del panel per gestire lo sfondo
        backGroundImage = new ImageIcon(getClass().getResource("/img/backGroundDefault.png")); //immagine sfondo
        creaAnimalePanel = new JPanel() {
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

        okButton = new JButton("Ok") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };

        okButton.setContentAreaFilled(false);
        okButton.setFocusPainted(false);
        okButton.setOpaque(false);
        okButton.setForeground(Color.WHITE);
    }

}