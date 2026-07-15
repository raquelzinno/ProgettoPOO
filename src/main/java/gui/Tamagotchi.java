package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Tamagotchi {
    private JPanel tamagotchiPanel;
    private JLabel labelEnergia;
    private JLabel labelFame;
    private JLabel labelPunti;
    private JLabel labelNomeAnimale;
    private JLabel immagineAnimale;
    private JButton negozioButton;
    private JButton minigameButton;
    private JButton itemButton;
    private JButton vaiADormireButton;
    private JLabel goBack;
    private JButton impostazioniButton;
    private JList listaVestitiIndossati;
    private JPanel animalePanel;
    private JPanel pulsantiPanel;
    private JPanel vestititPanel;
    private Animale animale;
    private Timer gameTime;
    public static DefaultListModel<Vestito> modelloListaVestiti;
    private ImageIcon backGroundImage;
    private Controller controller;

    public void popolaListaVestiti() {
        modelloListaVestiti = new DefaultListModel<Vestito>();
        for(Vestito v : animale.getVestitiIndossati()){
            modelloListaVestiti.addElement(v);
        }
        listaVestitiIndossati.setModel(modelloListaVestiti);
    }

    public void gestioneLista() {
        listaVestitiIndossati.setBorder(
                BorderFactory.createLineBorder(Color.BLUE, 4)
        );

        listaVestitiIndossati.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {

            JLabel label = new JLabel(value.toString());
            label.setText(null); //nella jlist sarà visibile solo l'icona
            Item item = (Item) value;

            CustomGUI.caricaImmagineItem(label,item.getIconPath(),true);

            return label;
        });

    }

    public void aggiornaLabel(){
        labelPunti.setText(String.valueOf(animale.getPunti()));
        labelEnergia.setText(animale.getEnergia() + "/" + animale.getEnergiaMax());
        labelFame.setText(animale.getFame() + "/" + animale.getFameMax());
        labelNomeAnimale.setText(animale.getNome());
    }

    public void immagineAnimale(){
        if(animale instanceof Orso) {
            if(animale.isDorme()){
                CustomGUI.caricaImmagineAnimale(immagineAnimale,"img/orsoDorme.png",false);
            }else {
                CustomGUI.caricaImmagineAnimale(immagineAnimale,"img/orso.png",false);
            }
        }else if(animale instanceof Pinguino) {
            if(animale.isDorme()) {
                CustomGUI.caricaImmagineAnimale(immagineAnimale,"img/pinguinoDorme.png",false);
            } else{
                CustomGUI.caricaImmagineAnimale(immagineAnimale,"img/pinguino.png",false);
            }
        }
    }

    public Tamagotchi(JFrame frameHome, Controller controller, Animale animale, Home home) {
        JFrame tamagotchiFrame = new JFrame("Tamagotchi");
        tamagotchiFrame.setContentPane(tamagotchiPanel);
        tamagotchiFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        tamagotchiFrame.pack();
        tamagotchiFrame.setSize(550, 350); //grandezza della finestra
        tamagotchiFrame.setLocationRelativeTo(null); //finestra si apre al centro
        tamagotchiFrame.setResizable(false); //non cambia dimensione
        animalePanel.setOpaque(false);
        vestititPanel.setOpaque(false);
        this.controller = controller;

        CustomGUI.caricaIcona(tamagotchiFrame);
        CustomGUI.caricaFont(labelNomeAnimale,20f,true);

        tamagotchiFrame.setVisible(true);
        this.animale = animale;

        aggiornaLabel();

        controller.setHomeFrame(this);
        controller.iniziaTimer(animale);

        popolaListaVestiti();
        gestioneLista();

        immagineAnimale();

        //gestisce il pulsante negozio
        negozioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NegozioPrincipale negozio = new NegozioPrincipale(tamagotchiFrame, controller, animale, Tamagotchi.this);
                tamagotchiFrame.setVisible(false);
            }
        });

        //gestisce il pulsante minigame
        minigameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(animale.isDorme()) {
                    JOptionPane.showMessageDialog(tamagotchiFrame,
                            animale.getNome() + " sta ancora dormendo!\nSveglialo per giocare ai minigames",
                            "Buonanotte",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Minigames minigames = new Minigames(tamagotchiFrame, controller, animale, Tamagotchi.this);
                tamagotchiFrame.setVisible(false);
            }
        });

        //gestisce il pulsante item
        itemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Items items = new Items(tamagotchiFrame, controller, animale, Tamagotchi.this);
                tamagotchiFrame.setVisible(false);
            }
        });

        //gestisce il pulsante dormi
        vaiADormireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (animale.isDorme()) {
                        controller.sveglia(animale);
                        vaiADormireButton.setText("Vai a dormire");
                        immagineAnimale();
                    } else {
                        controller.addormenta(animale);
                        vaiADormireButton.setText("Svegliati");
                        immagineAnimale();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Non è stato possibile aggiornare lo stato dell'animale nel database: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        //gestione pulsante impostazioni
        impostazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Impostazioni impostazioni = new Impostazioni(tamagotchiFrame, controller, animale, Tamagotchi.this, frameHome, home);
                tamagotchiFrame.setVisible(false);
            }
        });

        goBack.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        goBack.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    if (animale.isDorme()) {
                        controller.sveglia(animale);
                    } else { //se non sta dormendo, mi occupo semplicemente di salvare
                        controller.salvaStatoAnimale(animale);
                    }
                    controller.fermaTimer();
                    controller.deselezionaAnimale();
                    frameHome.setVisible(true);
                    tamagotchiFrame.setVisible(false);
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Errore nel tentativo di salvare i dati: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

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

    //MODIFICHE GRAFICHE -------------------------------------------------------------------------------

    private void createUIComponents() { //custom create del panel per gestire lo sfondo
        backGroundImage = new ImageIcon(getClass().getResource("/img/backGroundTamagotchi.png")); //immagine sfondo
        tamagotchiPanel = new JPanel() {
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

        negozioButton = new JButton("Negozio") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        negozioButton.setContentAreaFilled(false);
        negozioButton.setFocusPainted(false);
        negozioButton.setOpaque(false);
        negozioButton.setForeground(Color.WHITE);

        minigameButton = new JButton("Minigames") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        minigameButton.setContentAreaFilled(false);
        minigameButton.setFocusPainted(false);
        minigameButton.setOpaque(false);
        minigameButton.setForeground(Color.WHITE);

        itemButton = new JButton("Items") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        itemButton.setContentAreaFilled(false);
        itemButton.setFocusPainted(false);
        itemButton.setOpaque(false);
        itemButton.setForeground(Color.WHITE);

        vaiADormireButton = new JButton("Vai a dormire") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        vaiADormireButton.setContentAreaFilled(false);
        vaiADormireButton.setFocusPainted(false);
        vaiADormireButton.setOpaque(false);
        vaiADormireButton.setForeground(Color.WHITE);

        impostazioniButton = new JButton("Impostazione") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        impostazioniButton.setContentAreaFilled(false);
        impostazioniButton.setFocusPainted(false);
        impostazioniButton.setOpaque(false);
        impostazioniButton.setForeground(Color.WHITE);

        pulsantiPanel = new JPanel();
        pulsantiPanel.setOpaque(false);
    }
}
