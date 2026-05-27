package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NegozioPrincipale {
    private JPanel negozioPanel;
    private JLabel LabelPunti;
    private JLabel goBack;
    private JButton compraButton;
    private JList listaItem;
    public static DefaultListModel<Item> modelloListaItem;

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
        negozioFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        negozioFrame.pack();
        negozioFrame.setSize(500, 300); //grandezza della finestra
        negozioFrame.setLocationRelativeTo(null); //finestra si apre al centro
        negozioFrame.setResizable(false); //non cambia dimensione
        negozioFrame.setVisible(true);

        //i valori dell'animale vengono resi visibili
        LabelPunti.setText(String.valueOf(animale.getPunti()));

        //item di default
        popolaListaItem(controller.getNegozioBase());

        //gestione icone items
        listaItem.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {

            JLabel label = new JLabel(value.toString());

            Item item = (Item) value;

            String path = item.getIconPath();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
            Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(img));

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

            }
        });

        //gestione pulsante indietro
        goBack.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        goBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tamagotchi.aggiornaLabel(); //aggiorna i punti
                tamagotchiFrame.setVisible(true);
                negozioFrame.setVisible(false);
            }
        });
    }
}
