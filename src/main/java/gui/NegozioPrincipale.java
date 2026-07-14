package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class NegozioPrincipale {
    private JPanel negozioPanel;
    private JLabel LabelPunti;
    private JLabel goBack;
    private JButton compraButton;
    private JList listaItem;
    private JLabel negozioLabel;
    public static DefaultListModel<Item> modelloListaItem;
    private ImageIcon backGroundImage;

    private void popolaListaItem(Negozio negozio) {
        modelloListaItem = new DefaultListModel<Item>();
        for (Item item : negozio.getListaItem()) {
            modelloListaItem.addElement(item);
        }
        listaItem.setModel(modelloListaItem);
    }

    public NegozioPrincipale(JFrame tamagotchiFrame, Controller controller, Animale animale, Tamagotchi tamagotchi) {
        JFrame negozioFrame = new JFrame("Negozio");
        negozioFrame.setContentPane(negozioPanel);
        negozioFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        negozioFrame.pack();
        negozioFrame.setSize(500, 310); //grandezza della finestra
        negozioFrame.setLocationRelativeTo(null); //finestra si apre al centro
        negozioFrame.setResizable(false); //non cambia dimensione

        CustomGUI.caricaIcona(negozioFrame);
        CustomGUI.caricaFont(negozioLabel,22f,true);

        negozioFrame.setVisible(true);

        //i valori dell'animale vengono resi visibili
        LabelPunti.setText(String.valueOf(animale.getPunti()));

        //item di default
        popolaListaItem(controller.getNegozioBase());

        //gestione icone items
        listaItem.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {

            JLabel label = new JLabel(value.toString());
            Item item = (Item) value;

            CustomGUI.caricaImmagineItem(label,item.getIconPath(),false);

            label.setOpaque(true); //permette di configurare il comportamento in caso di selezione
            if (isSelected) {
                label.setBackground(list.getSelectionBackground()); //colore blu di selezione
                label.setForeground(list.getSelectionForeground()); //testo bianco quando selezionato
            } else {
                label.setBackground(list.getBackground());  //sfondo normale
                label.setForeground(list.getForeground());  //testo normale
            }

            return label;
        });

        listaItem.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //prende animale selezionato dalla lista
                Item itemSelezionato = (Item) listaItem.getSelectedValue();
            }
        });

        //gestione bottone compra
        compraButton.addActionListener(e -> {
            try {
                Item oggettoSelezionato = (Item) listaItem.getSelectedValue();
                if (oggettoSelezionato == null) {
                    JOptionPane.showMessageDialog(negozioFrame,
                            "Seleziona un oggetto prima di acquistare!",
                            "Nessuna selezione",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                controller.compra(oggettoSelezionato, animale);

                JOptionPane.showMessageDialog(negozioFrame,
                        "Hai acquistato: " + oggettoSelezionato.getNome() + "!\nL'oggetto è stato aggiunto al tuo inventario.",
                        "Acquisto Completato",
                        JOptionPane.INFORMATION_MESSAGE);

                LabelPunti.setText(String.valueOf(animale.getPunti()));
            } catch(RuntimeException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        //gestione pulsante indietro
        CustomGUI.tornaIndietro(goBack,negozioFrame,tamagotchiFrame,true,tamagotchi);

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
        backGroundImage = new ImageIcon(Login.class.getResource("/img/backGroundNegozio.png"));

        negozioPanel = new JPanel() {
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

        compraButton = new JButton("Compra") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        compraButton.setContentAreaFilled(false);
        compraButton.setFocusPainted(false);
        compraButton.setOpaque(false);
        compraButton.setForeground(Color.WHITE);
    }
}
