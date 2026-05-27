package gui;

import controller.Controller;
import model.Animale;
import model.Orso;
import model.Pinguino;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Home {
    private JPanel mainPanel;
    private JButton creaAnimaleButton;
    private JPanel vuotoPanel;
    private JPanel creaAnimalePanel;
    private JList listaAnimali;
    private JLabel exit;
    private JFrame frameHome;
    private Controller controller;
    public static DefaultListModel<Animale> modelloListaAnimali;

    public void rimuoviAnimale(Animale animale){
        modelloListaAnimali.removeElement(animale);
    }

    public Home(JFrame loginFrame, Controller controller) {
        frameHome = new JFrame("Home");
        frameHome.setContentPane(mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setSize(450, 350); //grandezza della finestra
        frameHome.setLocationRelativeTo(null); //finestra si apre al centro
        frameHome.setVisible(true);

        //lista dove sono riportati gli animali dell'utente
        modelloListaAnimali = new DefaultListModel<>();

        for(Animale a : controller.getUtenteAttuale().getAnimaliPosseduti()){
            modelloListaAnimali.addElement(a);
        }

        listaAnimali.setModel(modelloListaAnimali);

        //icone animali
        listaAnimali.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {

            JLabel label = new JLabel(value.toString());

            Animale animale = (Animale) value;

            if(animale instanceof Orso){

                ImageIcon icon = new ImageIcon(
                        getClass().getClassLoader()
                                .getResource("img/orso.png")
                );

                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

                label.setIcon(new ImageIcon(img));
            }

            else if(animale instanceof Pinguino){

                ImageIcon icon = new ImageIcon(
                        getClass().getClassLoader()
                                .getResource("img/pinguino.png")
                );

                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

                label.setIcon(new ImageIcon(img));
            }
            return label;
        });


        listaAnimali.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //prende animale selezionato dalla lista
                Animale animaleSelezionato = (Animale) listaAnimali.getSelectedValue();
            }
        });

        //gestione pulsante crea animale
        creaAnimaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.checkAnimali();
                    CreaAnimale creaAnimale = new CreaAnimale(frameHome, controller);
                    frameHome.setVisible(false);
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        //gestione selezione animale dalla lista
        listaAnimali.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Animale animaleCliccato = (Animale) listaAnimali.getSelectedValue();
                if (animaleCliccato != null) {
                    Tamagotchi tamagotchi = new Tamagotchi(frameHome, controller, animaleCliccato, Home.this);
                    frameHome.setVisible(false);
                    listaAnimali.clearSelection();
                }
            }
        });

        //gestione pulsante esci dall'account
        exit.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        exit.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                controller.esciUtente();
                loginFrame.setVisible(true);
                frameHome.setVisible(false);
            }
        });

    }

}