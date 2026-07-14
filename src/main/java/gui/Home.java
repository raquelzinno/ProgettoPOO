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
import java.sql.SQLException;

public class Home {
    private JPanel mainPanel;
    private JButton creaAnimaleButton;
    private JPanel vuotoPanel;
    private JPanel creaAnimalePanel;
    private JList listaAnimali;
    private JLabel exit;
    private JLabel titolo;
    private JFrame frameHome;
    private Controller controller;
    public static DefaultListModel<Animale> modelloListaAnimali;
    private ImageIcon backGroundImage;

    public void rimuoviAnimale(Animale animale){
        modelloListaAnimali.removeElement(animale);
    }

    public void caricaDati(Controller controller) {
        try {
            controller.puliziaDati();
            controller.sincronizzaListaAnimali();
            controller.caricaInventarioUtente();
            controller.caricaVestitiIndossati();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            System.err.println("Errore critico nel tentativo di caricare i dati dal Database.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Home(JFrame loginFrame, Controller controller) {
        frameHome = new JFrame("Home");
        frameHome.setContentPane(mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setSize(450, 350); //grandezza della finestra
        frameHome.setLocationRelativeTo(null);//finestra si apre al centro
        frameHome.setResizable(false);
        vuotoPanel.setOpaque(false); //rende i pannelli opachi per far vedere lo sfondo
        creaAnimalePanel.setOpaque(false);
        vuotoPanel.setBorder(null);
        frameHome.setVisible(true);

        //icona della finestra
        ImageIcon iconFrame = new ImageIcon(getClass().getResource("/img/tamagotchiIcon.png"));
        frameHome.setIconImage(iconFrame.getImage());

        //fonts
        try {
            titolo.setFont(Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fonts/pixel-bold.ttf")
            ).deriveFont(22f));

        } catch (Exception e) {
            e.printStackTrace();
        }

        caricaDati(controller);

        modelloListaAnimali = new DefaultListModel<>();
        for(Animale a : controller.getUtenteAttuale().getAnimaliPosseduti()){
            modelloListaAnimali.addElement(a);
        }
        listaAnimali.setModel(modelloListaAnimali);
        listaAnimali.setBorder(
                BorderFactory.createLineBorder(Color.BLUE, 3)
        );

        listaAnimali.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        //icone animali
        listaAnimali.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.toString());
            Animale animale = (Animale) value;

            if(animale instanceof Orso){
                ImageIcon icon = new ImageIcon(
                        getClass().getClassLoader()
                                .getResource("img/orsoIcona.png")
                );

                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

                label.setIcon(new ImageIcon(img));
            }

            else if(animale instanceof Pinguino){
                ImageIcon icon = new ImageIcon(
                        getClass().getClassLoader()
                                .getResource("img/pinguinoIcona.png")
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
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
                catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        //gestione selezione animale dalla lista
        listaAnimali.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Animale animaleCliccato = (Animale) listaAnimali.getSelectedValue();
                if (animaleCliccato != null) {
                    try {
                        controller.selezionaAnimale(animaleCliccato);
                        Tamagotchi tamagotchi = new Tamagotchi(frameHome, controller, animaleCliccato, Home.this);
                        frameHome.setVisible(false);
                        listaAnimali.clearSelection();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //gestione pulsante esci dall'account
        exit.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        exit.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    //aggiorna i parametri degli animali dell'utente nel database
                    for (Animale a : controller.getUtenteAttuale().getAnimaliPosseduti()) {
                        controller.salvaStatoAnimale(a);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Errore nel tentativo di salvare i dati dal Database.");
                    ex.printStackTrace();
                }
                finally {
                    controller.esciUtente();
                    loginFrame.setVisible(true);
                    frameHome.setVisible(false);
                }
            }
        });

    }

    private void createUIComponents() { //custom create del panel per gestire lo sfondo
        backGroundImage = new ImageIcon(getClass().getResource("/img/backGroundHome.png")); //immagine sfondo
        mainPanel = new JPanel() {
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

        creaAnimaleButton = new JButton("Crea animale") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };

        creaAnimaleButton.setContentAreaFilled(false);
        creaAnimaleButton.setFocusPainted(false);
        creaAnimaleButton.setOpaque(false);
        creaAnimaleButton.setForeground(Color.WHITE);
    }
}