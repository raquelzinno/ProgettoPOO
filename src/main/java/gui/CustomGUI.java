package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

public class CustomGUI {

    public static ImageIcon caricaImmagine(String percorso) {
        URL risorsa = CustomGUI.class.getClassLoader().getResource(percorso);
        if(risorsa != null) {
            return (new ImageIcon(risorsa));
        }
        else {
            System.err.println("Immagine non trovata!");
            return new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        }
    }

    public static void caricaIcona(JFrame frame) {
            frame.setIconImage((caricaImmagine("img/tamagotchiIcon.png")).getImage());
    }

    public static void caricaImmagineSopraTesto(JLabel label,String percorso) {
        label.setIcon(caricaImmagine(percorso));
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
    }

    public static void caricaImmagineAnimale(JLabel label, String percorso, boolean isIcon) {
        ImageIcon icon = caricaImmagine(percorso);
        Image img;
        if(isIcon) {
            img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        } else {
            img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        }
        label.setIcon(new ImageIcon(img));
    }

    public static void caricaImmagineItem(JLabel label, String percorso, boolean isIndossato) {
        ImageIcon icon = caricaImmagine(percorso);
        Image img;
        if(isIndossato) {
            img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
        } else {
            img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        }
        label.setIcon(new ImageIcon(img));
    }

    public static void gestionePulsantiMinigame(JRadioButton button, String percorso) {
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra
        button.setIcon(caricaImmagine(percorso));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                button.setForeground(new Color(0,50,219));
            } else {
                button.setForeground(new Color(25,26,28));
            }
        });
    }

    public static void caricaFont(JLabel label, float size, boolean isBold) {
        try {
            if(isBold) {
                label.setFont(Font.createFont(
                        Font.TRUETYPE_FONT,
                        CustomGUI.class.getResourceAsStream("/fonts/pixel-bold.ttf")
                ).deriveFont(size));
            }
            else {
                label.setFont(Font.createFont(
                        Font.TRUETYPE_FONT,
                        CustomGUI.class.getResourceAsStream("/fonts/pixel.ttf")
                ).deriveFont(size));
            }
        } catch (Exception e) {
            System.err.println("Font non trovato!");
        }
    }

    public static void tornaIndietro(JLabel label,JFrame frameInizio, JFrame frameRitorno, boolean isDisposable) {
        label.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        label.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                frameRitorno.setVisible(true);
                frameInizio.setVisible(false);
                if(isDisposable) {
                    frameInizio.dispose();
                }
            }
        });
    }

    public static void tornaIndietro(JLabel label,JFrame frameInizio, JFrame frameRitorno, boolean isDisposable, Tamagotchi tamagotchi) {
        label.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        label.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                tamagotchi.aggiornaLabel();
                frameRitorno.setVisible(true);
                frameInizio.setVisible(false);
                if(isDisposable) {
                    frameInizio.dispose();
                }
            }
        });
    }
}
