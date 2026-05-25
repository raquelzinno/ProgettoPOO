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

        //gestione icone
        listaItem.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {

            JLabel label = new JLabel(value.toString());

            Item item = (Item) value;

            if(item instanceof Cibo){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("img/pizza.png"));
                Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
            }

            else if(item instanceof Vestito){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("img/magliaEmo.png")
                );

                Image img = icon.getImage().getScaledInstance(
                        24,
                        24,
                        Image.SCALE_SMOOTH
                );

                label.setIcon(new ImageIcon(img));
            }

            // mantiene i colori della selezione
            if(isSelected){

                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
                label.setOpaque(true);
            }

            return label;
        });

        listaItem.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {

            JLabel label = new JLabel(value.toString());

            Item item = (Item) value;

            if(item instanceof Cibo){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("img/pizza.png"));
                Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
            }

            else if(item instanceof Vestito){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("img/magliaEmo.png")
                );

                Image img = icon.getImage().getScaledInstance(
                        24,
                        24,
                        Image.SCALE_SMOOTH
                );

                label.setIcon(new ImageIcon(img));
            }

            // mantiene i colori della selezione
            if(isSelected){

                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
                label.setOpaque(true);
            }

            return label;
        });

        listaItem.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //prende animale selezionato dalla lista
                Item itemSelezionato = (Item) listaItem.getSelectedValue();

                /*//get selectedValue può ritornare null, è necessario questo controllo
                if (animaleSelezionato != null) {
                    nomeCognome.setText(contattoSelezionato.getNome() + " " + contattoSelezionato.getCognome());
                    numeroTelefono.setText(contattoSelezionato.getNumTelefono());
                    email.setText(contattoSelezionato.getEmail());
                }*/
            }
        });

        //gestione bottone compra
        compraButton.addActionListener(e -> {
            Item oggettoSelezionato = (Item) listaItem.getSelectedValue();
            if (oggettoSelezionato == null) {
                JOptionPane.showMessageDialog(negozioFrame,
                        "Seleziona un oggetto prima di acquistare!",
                        "Nessuna selezione",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            controller.compra(controller.getUtenteAttuale(), oggettoSelezionato, animale);

            JOptionPane.showMessageDialog(negozioFrame,
                    "Hai acquistato: " + oggettoSelezionato.getNome() + "!\nL'oggetto è stato aggiunto al tuo inventario.",
                    "Acquisto Completato",
                    JOptionPane.INFORMATION_MESSAGE);

            LabelPunti.setText(String.valueOf(animale.getPunti()));
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
