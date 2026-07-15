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
    private Controller controller;

    private void popolaListaItem() {
        modelloListaItem = new DefaultListModel<Item>();
        for (Item item : controller.getNegozioBase().getListaItem()) {
            modelloListaItem.addElement(item);
        }
        listaItem.setModel(modelloListaItem);
    }

    private void gestioneLista() {
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
    }

    public NegozioPrincipale(JFrame tamagotchiFrame, Controller controller, Animale animale, Tamagotchi tamagotchi) {
        JFrame negozioFrame = new JFrame("Negozio");
        negozioFrame.setContentPane(negozioPanel);
        negozioFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        negozioFrame.pack();
        negozioFrame.setSize(500, 310); //grandezza della finestra
        negozioFrame.setLocationRelativeTo(null); //finestra si apre al centro
        negozioFrame.setResizable(false); //non cambia dimensione
        this.controller = controller;

        CustomGUI.caricaIcona(negozioFrame);
        CustomGUI.caricaFont(negozioLabel,22f,true);

        negozioFrame.setVisible(true);

        //i valori dell'animale vengono resi visibili
        LabelPunti.setText(String.valueOf(animale.getPunti()));

        CustomGUI.tornaIndietro(goBack,negozioFrame,tamagotchiFrame,true,tamagotchi);
        CustomGUI.salvaEdEsci(negozioFrame,animale,controller);

        //item di default
        popolaListaItem();
        gestioneLista();

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
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Non è stato possibile salvare l'acquisto: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch(RuntimeException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
