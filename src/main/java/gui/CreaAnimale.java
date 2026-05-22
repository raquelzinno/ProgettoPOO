package gui;

import controller.Controller;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreaAnimale {
    private JRadioButton orsoRadioButton;
    private JPanel creaAnimalePanel;
    private JRadioButton pinguinoRadioButton;
    private JTextField nomeTextField;
    private JButton okButton;
    private JLabel goBack;

    public CreaAnimale(JFrame frameHome, Controller controller){
        JFrame creaAnimaleFrame = new JFrame("Crea un nuovo animale");
        creaAnimaleFrame.setContentPane(creaAnimalePanel);
        creaAnimaleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        creaAnimaleFrame.pack();
        creaAnimaleFrame.setSize(400, 300); //grandezza della finestra
        creaAnimaleFrame.setLocationRelativeTo(null); //finestra si apre al centro
        creaAnimaleFrame.setResizable(false); //non cambia dimensione
        creaAnimaleFrame.setVisible(true);

        ButtonGroup gruppoAnimali = new ButtonGroup();
        gruppoAnimali.add(orsoRadioButton);
        gruppoAnimali.add(pinguinoRadioButton);

        //gestione pulsante ok
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = "";
                if(orsoRadioButton.isSelected()){
                    tipo = "Orso";
                }else if (pinguinoRadioButton.isSelected()){
                    tipo = "Pinguino";
                }
                String nome = nomeTextField.getText();

                for(Utente u : controller.getListaUtenti()){
                    if(u.isAccessoEffettuato())
                        controller.creaAnimale(u, tipo, nome);
                }
                JOptionPane.showMessageDialog(null, "Animale creato con successo.");

                Home.modelloListaAnimali.addElement(controller.getUtenteAttuale().getAnimaliPosseduti().getLast());

                //torna alla home
                frameHome.setVisible(true);
                creaAnimaleFrame.setVisible(false);
            }
        });

        //gestone pulsante torna alla home
        goBack.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        goBack.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                frameHome.setVisible(true);
                creaAnimaleFrame.setVisible(false);
            }
        });
    }

}
