package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    public void aggiornaLabel(){
        labelPunti.setText(String.valueOf(animale.getPunti()));
        labelEnergia.setText(animale.getEnergia() + "/" + animale.getEnergiaMax());
        labelFame.setText(animale.getFame() + "/" + animale.getFameMax());
        labelNomeAnimale.setText(animale.getNome());
    }

    public Tamagotchi(JFrame frameHome, Controller controller, Animale animale, Home home) {
        JFrame tamagotchiFrame = new JFrame("Tamagotchi");
        tamagotchiFrame.setContentPane(tamagotchiPanel);
        tamagotchiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tamagotchiFrame.pack();
        tamagotchiFrame.setSize(550, 350); //grandezza della finestra
        tamagotchiFrame.setLocationRelativeTo(null); //finestra si apre al centro
        tamagotchiFrame.setResizable(false); //non cambia dimensione
        animalePanel.setOpaque(false);
        vestititPanel.setOpaque(false);
        tamagotchiFrame.setVisible(true);
        this.animale = animale;

        //icona della finestra
        ImageIcon iconFrame = new ImageIcon(getClass().getResource("/img/tamagotchiIcon.png"));
        tamagotchiFrame.setIconImage(iconFrame.getImage());

        //i valori dell'animale vengono resi visibili
        aggiornaLabel();

        //passo al controller la home e inizio il timer
        controller.setHomeFrame(this);
        controller.iniziaTimer(animale);

        //fonts
        try {
            labelNomeAnimale.setFont(Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fonts/pixel-bold.ttf")
            ).deriveFont(20f));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //lista vestiti
        modelloListaVestiti = new DefaultListModel<Vestito>();

        for(Vestito v : animale.getVestititIndossati()){
            modelloListaVestiti.addElement(v);
        }

        listaVestitiIndossati.setModel(modelloListaVestiti);

        listaVestitiIndossati.setBorder(
                BorderFactory.createLineBorder(Color.BLUE, 4)
        );

        listaVestitiIndossati.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {

            JLabel label = new JLabel(value.toString());
            label.setText(null); //nella jlist sarà visibile solo l'icona
            Item item = (Item) value;

            String path = item.getIconPath();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
            Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(img));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);

            return label;
        });

        //immagine dell'animale
        if(animale instanceof Orso) {
            immagineAnimale.setIcon(new ImageIcon(
                    new ImageIcon(
                            getClass().getClassLoader().getResource("img/orso.png")
                    ).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)
            ));
        }else if(animale instanceof Pinguino) {
            immagineAnimale.setIcon(new ImageIcon(
                    new ImageIcon(
                            getClass().getClassLoader().getResource("img/pinguino.png")
                    ).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)
            ));
        }

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
                if(animale.isDorme()) {
                    controller.sveglia(animale);
                    vaiADormireButton.setText("Vai a dormire");
                }
                else {
                    controller.addormenta(animale);
                    vaiADormireButton.setText("Svegliati");

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
                if(animale.isDorme())
                controller.sveglia(animale);
                controller.fermaTimer();
                frameHome.setVisible(true);
                tamagotchiFrame.setVisible(false);
            }
        });

    }

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
