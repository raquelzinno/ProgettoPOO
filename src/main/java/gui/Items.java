package gui;

import controller.Controller;
import model.Animale;
import model.Cibo;
import model.Item;
import model.Vestito;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Items {
    private JPanel itemsPanel;
    private JLabel goBack;
    private JButton usaButton;
    private JList listaItem;
    private JScrollPane itemsScrollPane;
    public static DefaultListModel<Item> modelloListaItems;

    public Items(JFrame tamagotchiFrame, Controller controller, Animale animale, Tamagotchi tamagotchi){
        JFrame itemsFrame = new JFrame("I tuoi items");
        itemsFrame.setContentPane(itemsPanel);
        itemsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        itemsFrame.pack();
        itemsFrame.setSize(500, 300); //grandezza della finestra
        itemsFrame.setLocationRelativeTo(null); //finestra si apre al centro
        itemsFrame.setResizable(false); //non cambia dimensione
        itemsFrame.setVisible(true);

        //la barra di scorrimento della lista è visibile solo quando necessario
        itemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //lista dove sono riportati gli item dell'utente
        modelloListaItems = new DefaultListModel<>();

        for(Item i : controller.getUtenteAttuale().getItemPosseduti()){
            modelloListaItems.addElement(i);
        }

        listaItem.setModel(modelloListaItems);

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

        //gestione rimozione vestito dall'animale
        listaItem.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Item itemCliccato = (Item) listaItem.getSelectedValue();

                if(itemCliccato instanceof Vestito) { //funziona solo se l'item cliccato è un vestito
                    Vestito vestito = (Vestito) itemCliccato;

                    if (vestito != null && vestito.isIndossato()) { //se il vestito è attualmente indossato lo rimuove
                        controller.rimuoviVestito(vestito, animale);
                        JOptionPane.showMessageDialog(itemsFrame,
                                "Hai rimosso: " + itemCliccato.getNome() + "!\nL'oggetto è stato rimosso.",
                                "Vestito rimosso",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        //gestione pulsante usa
        usaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Item oggettoSelezionato = (Item) listaItem.getSelectedValue();
                    if (oggettoSelezionato == null) {
                        JOptionPane.showMessageDialog(itemsFrame,
                                "Devi prima selezionare un oggetto!",
                                "Nessuna selezione",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    controller.usaItem(controller.getUtenteAttuale(), oggettoSelezionato, animale);

                    if(oggettoSelezionato instanceof Cibo) { //se cibo
                        modelloListaItems.removeElement(oggettoSelezionato); //elimina l'item dalla lista se è un cibo

                        JOptionPane.showMessageDialog(itemsFrame,
                                "Hai usato: " + oggettoSelezionato.getNome() + "!\nL'oggetto è stato rimosso dal tuo inventario.",
                                "Oggetto utilizzato",
                                JOptionPane.INFORMATION_MESSAGE);
                    }else
                        if(oggettoSelezionato instanceof Vestito){ //se vestito
                        JOptionPane.showMessageDialog(itemsFrame,
                                "Hai usato: " + oggettoSelezionato.getNome() + "!\nL'oggetto è stato indossato.",
                                "Oggetto utilizzato",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //gestione pulsante indietro
        goBack.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        goBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tamagotchi.aggiornaLabel(); //aggiorna fame e energia
                tamagotchiFrame.setVisible(true);
                itemsFrame.setVisible(false);
            }
        });
    }
}
