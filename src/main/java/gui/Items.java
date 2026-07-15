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
import java.sql.SQLException;

public class Items {
    private JPanel itemsPanel;
    private JLabel goBack;
    private JButton usaButton;
    private JList listaItem;
    private JScrollPane itemsScrollPane;
    private JButton eliminaButton;
    private JLabel spazio;
    private JLabel itemsLabel;
    private JPanel pulsantiPanel;
    public static DefaultListModel<Item> modelloListaItems;
    private ImageIcon backGroundImage;
    private Controller controller;

    public void popolaListaItems() {
        modelloListaItems = new DefaultListModel<>();
        for(Item i : controller.getUtenteAttuale().getItemPosseduti()){
            modelloListaItems.addElement(i);
        }
        listaItem.setModel(modelloListaItems);
    }

    public void gestioneLista() {
        listaItem.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {

            JLabel label = new JLabel(value.toString());
            Item item = (Item) value;

            CustomGUI.caricaImmagineItem(label,item.getIconPath(),false);

            label.setOpaque(true);

            if (isSelected) {  //gestisce la selezione della jlist
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            } else {
                if (item instanceof Vestito && ((Vestito) item).isIndossato()) {  //colore diverso se l'item è un vestito indossato;
                    label.setBackground(new Color(198, 239, 206));
                    label.setForeground(new Color(0, 97, 0));
                } else { //caso standard per tutti gli altri item non selezionati
                    label.setBackground(list.getBackground());
                    label.setForeground(list.getForeground());
                }
            }

            return label;
        });
    }

    public Items(JFrame tamagotchiFrame, Controller controller, Animale animale, Tamagotchi tamagotchi){
        JFrame itemsFrame = new JFrame("I tuoi items");
        itemsFrame.setContentPane(itemsPanel);
        itemsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        itemsFrame.pack();
        itemsFrame.setSize(500, 300); //grandezza della finestra
        itemsFrame.setLocationRelativeTo(null); //finestra si apre al centro
        itemsFrame.setResizable(false); //non cambia dimensione
        this.controller = controller;

        CustomGUI.caricaIcona(itemsFrame);
        CustomGUI.caricaFont(itemsLabel,20f,true);

        itemsFrame.setVisible(true);

        //la barra di scorrimento della lista è visibile solo quando necessario
        itemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        CustomGUI.tornaIndietro(goBack,itemsFrame,tamagotchiFrame,true,tamagotchi);
        popolaListaItems();
        gestioneLista();

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
                        if(animale.isDorme()) {
                            listaItem.clearSelection();
                            JOptionPane.showMessageDialog(itemsFrame,
                                    animale.getNome() + " sta ancora dormendo!\nSveglialo per cambiare vestiti.",
                                    "Buonanotte",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        if((animale.getVestitiIndossati()).contains(itemCliccato)) { //se il vestito è attualmente indossato lo rimuove
                            try {
                                controller.rimuoviVestito(vestito, animale);
                                Tamagotchi.modelloListaVestiti.removeElement(vestito);
                                listaItem.clearSelection();
                                JOptionPane.showMessageDialog(itemsFrame,
                                        "Hai rimosso: " + itemCliccato.getNome() + "!\nL'oggetto è stato rimosso",
                                        "Vestito rimosso",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "Non è stato possibile rimuovere il vestito: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                                ex.printStackTrace();
                            }
                        }
                        else { //se il vestito è indossato ma da un altro animale dà un avviso
                            listaItem.clearSelection();
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

                    controller.usaItem(oggettoSelezionato, animale);

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
                            Tamagotchi.modelloListaVestiti.addElement((Vestito) oggettoSelezionato);
                            JOptionPane.showMessageDialog(itemsFrame,
                                "Hai usato: " + oggettoSelezionato.getNome() + "!\nL'oggetto è stato indossato.",
                                "Oggetto utilizzato",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Non è stato possibile usare l'item: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } catch(RuntimeException ex){
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
                if(oggettoSelezionato instanceof Vestito && ((Vestito) oggettoSelezionato).isIndossato()) {
                    JOptionPane.showMessageDialog(itemsFrame,
                            "Non puoi eliminare un vestito che stai indossando!",
                            "Già Indossato",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    controller.elimina(oggettoSelezionato);
                    modelloListaItems.removeElement(oggettoSelezionato);
                    JOptionPane.showMessageDialog(itemsFrame,
                            "Hai eliminato " + oggettoSelezionato.getNome(),
                            "Item eliminato",
                            JOptionPane.INFORMATION_MESSAGE);
                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Non è stato possibile eliminare l'item dal database: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });


                    //gestione pulsante indietro

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
        backGroundImage = new ImageIcon(getClass().getResource("/img/backGroundItems.png")); //immagine sfondo
        itemsPanel = new JPanel() {
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

        usaButton = new JButton("Usa") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        usaButton.setContentAreaFilled(false);
        usaButton.setFocusPainted(false);
        usaButton.setOpaque(false);
        usaButton.setForeground(Color.WHITE);

        eliminaButton = new JButton("Elimina") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        eliminaButton.setContentAreaFilled(false);
        eliminaButton.setFocusPainted(false);
        eliminaButton.setOpaque(false);
        eliminaButton.setForeground(Color.WHITE);

        pulsantiPanel = new JPanel();
        pulsantiPanel.setOpaque(false);
    }
}
