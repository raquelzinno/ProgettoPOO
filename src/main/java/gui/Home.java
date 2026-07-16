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

    /**
     * Elimina un animale selezionato dall'utente dalla lista degli animali posseduti.
     *
     * @param animale l' {@link Animale} da eliminare
     */
    public void rimuoviAnimale(Animale animale){
        modelloListaAnimali.removeElement(animale);
    }

    /**
     * Carica i dati aggiornati degli animali dal database nella lista.
     */
    public void caricaDati() {
        try {
            controller.puliziaDati();
            controller.sincronizzaListaAnimali();
            controller.caricaInventarioUtente();
            controller.caricaVestitiIndossati();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore critico nel tentativo di recuperare i dati: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Popola la lista animali con gli animali appartenenti all'utente, recuperandoli dal database.
     */
    public void popolaListaAnimali() {
        modelloListaAnimali = new DefaultListModel<>();
        for(Animale a : controller.getUtenteAttuale().getAnimaliPosseduti()){
            modelloListaAnimali.addElement(a);
        }
        listaAnimali.setModel(modelloListaAnimali);
    }

    /**
     * Gestione grafica della lista degli animali.
     */
    public void gestioneLista() {
        listaAnimali.setBorder(
                BorderFactory.createLineBorder(Color.BLUE, 3)
        );

        listaAnimali.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        //icone animali
        listaAnimali.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.toString());
            Animale animale = (Animale) value;

            if(animale instanceof Orso) {
                CustomGUI.caricaImmagineAnimale(label,"img/orsoIcona.png", true);
            }
            else if(animale instanceof Pinguino) {
                CustomGUI.caricaImmagineAnimale(label,"img/pinguinoIcona.png",true);
            }
            return label;
        });
    }

    /**
     * Crea una nuova istanza della finestra Home.
     * Inizializza l'interfaccia, configura i listener per i pulsanti
     * e prepara la finestra per l'input dell'utente.
     *
     * @param loginFrame il frame della pagina del login
     * @param controller il controller principale che gestisce la logica di sistema.
     */
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
        this.controller = controller;

        CustomGUI.caricaIcona(frameHome);
        CustomGUI.caricaFont(titolo,22f,true);

        frameHome.setVisible(true);

        caricaDati();
        popolaListaAnimali();
        gestioneLista();

        listaAnimali.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Animale animaleSelezionato = (Animale) listaAnimali.getSelectedValue(); //prende animale selezionato dalla lista
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
                    JOptionPane.showMessageDialog(null, "Non è stato possibile risalire agli animali posseduti: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                catch (RuntimeException ex){
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
                        JOptionPane.showMessageDialog(null, "Non è stato possibile risalire all'animale selezionato: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
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
                    JOptionPane.showMessageDialog(null, "Errore nel tentativo di salvare i dati: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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