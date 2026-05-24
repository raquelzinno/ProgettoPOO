package gui;

import controller.Controller;
import model.Animale;

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

    /*public static void main(String[] args) {
        Home home = new Home();
        frameHome = new JFrame("Home");
        frameHome.setContentPane(new Home().mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setSize(450, 350); //grandezza della finestra
        frameHome.setLocationRelativeTo(null); //finestra si apre al centro
        frameHome.setVisible(true);


        home.unAnimalePanel.setVisible(true);

        home.mainPanel.revalidate();
        home.mainPanel.repaint();
    }*/

    public Home(JFrame loginFrame, Controller controller) {
        //Home home = new Home();
        frameHome = new JFrame("Home");
        //frameHome.setContentPane(new Home().mainPanel);
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

        listaAnimali.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //prende animale selezionato dalla lista
                Animale animaleSelezionato = (Animale) listaAnimali.getSelectedValue();

                /*//get selectedValue può ritornare null, è necessario questo controllo
                if (animaleSelezionato != null) {
                    nomeCognome.setText(contattoSelezionato.getNome() + " " + contattoSelezionato.getCognome());
                    numeroTelefono.setText(contattoSelezionato.getNumTelefono());
                    email.setText(contattoSelezionato.getEmail());
                }*/
            }
        });

        //gestione pulsante crea animale
        creaAnimaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.checkAnimali(controller.getUtenteAttuale());
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
                    Tamagotchi tamagotchi = new Tamagotchi(frameHome, controller,animaleCliccato);
                    frameHome.setVisible(false);
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