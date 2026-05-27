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
    private JButton eliminaButton;
    private JLabel spazio;
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
                Item itemSelezionato = (Item) listaItem.getSelectedValue();
            }
        });

        //gestione rimozione vestito dall'animale
        listaItem.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Item itemCliccato = (Item) listaItem.getSelectedValue();

                if(itemCliccato instanceof Vestito) { //funziona solo se l'item cliccato è un vestito
                    Vestito vestito = (Vestito) itemCliccato;

                    if (vestito != null && vestito.isIndossato()) {
                        if((animale.getVestititIndossati()).contains(itemCliccato)) { //se il vestito è attualmente indossato lo rimuove
                            controller.rimuoviVestito(vestito, animale);
                            Tamagotchi.modelloListaVestiti.removeElement(vestito.getNome());
                            listaItem.clearSelection();
                            JOptionPane.showMessageDialog(itemsFrame,
                                    "Hai rimosso: " + itemCliccato.getNome() + "!\nL'oggetto è stato rimosso",
                                    "Vestito rimosso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        else { //se il vestito è indossato ma da un altro animale dà un avviso
                            JOptionPane.showMessageDialog(itemsFrame,
                                    itemCliccato.getNome() + " è già indossato da un altro animale!",
                                    "Già indossato",
                                    JOptionPane.WARNING_MESSAGE);
                        }
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
                    if(animale.isDorme()) {
                        JOptionPane.showMessageDialog(itemsFrame,
                                animale.getNome() + " sta ancora dormendo!\nSveglialo per usare gli item.",
                                "Buonanotte",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    controller.usaItem(controller.getUtenteAttuale(), oggettoSelezionato, animale);

                    if(oggettoSelezionato instanceof Cibo) { //se cibo
                        controller.elimina(oggettoSelezionato);
                        modelloListaItems.removeElement(oggettoSelezionato); //elimina l'item dalla lista se è un cibo

                        JOptionPane.showMessageDialog(itemsFrame,
                                "Hai usato: " + oggettoSelezionato.getNome() + "!\nL'oggetto è stato rimosso dal tuo inventario.",
                                "Oggetto utilizzato",
                                JOptionPane.INFORMATION_MESSAGE);
                    }else
                        if(oggettoSelezionato instanceof Vestito){ //se vestito
                            listaItem.clearSelection();
                            JOptionPane.showMessageDialog(itemsFrame,
                                "Hai usato: " + oggettoSelezionato.getNome() + "!\nL'oggetto è stato indossato.",
                                "Oggetto utilizzato",
                                JOptionPane.INFORMATION_MESSAGE);
                            Tamagotchi.modelloListaVestiti.addElement((Vestito) oggettoSelezionato);

                    }
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        eliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item oggettoSelezionato = (Item) listaItem.getSelectedValue();
                if (oggettoSelezionato == null) {
                    JOptionPane.showMessageDialog(itemsFrame,
                            "Devi prima selezionare un oggetto!",
                            "Nessuna selezione",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                controller.elimina(oggettoSelezionato);
                modelloListaItems.removeElement(oggettoSelezionato);
                JOptionPane.showMessageDialog(itemsFrame,
                        "Hai eliminato " + oggettoSelezionato.getNome(),
                        "Item eliminato",
                        JOptionPane.INFORMATION_MESSAGE);

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
