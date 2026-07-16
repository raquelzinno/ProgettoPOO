package gui;

import controller.Controller;
import model.Animale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.SQLException;

public class CustomGUI {

    /**
     * Carica l'immagine di un elemento tramite il suo image path.
     *
     * @param percorso path dell'immagine
     * @return l' {@link ImageIcon} dell'elemento
     */
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

    /**
     * Carica l'icona del sistema tramite il suo icon path.
     *
     * @param frame il frame dove viene visualizzata l'icona
     */
    public static void caricaIcona(JFrame frame) {
            frame.setIconImage((caricaImmagine("img/tamagotchiIcon.png")).getImage());
    }

    /**
     * Carica l'immagine centrale  sopra a un label.
     *
     * @param label    il label
     * @param percorso il path dell'immagine
     */
    public static void caricaImmagineSopraTesto(JLabel label,String percorso) {
        label.setIcon(caricaImmagine(percorso));
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
    }

    /**
     * Carica l'immagine dell'animale selezionato.
     * Lo stesso metodo può essere usato per caricare l'icona della testa degli animali.
     *
     * @param label    il label dell'animale
     * @param percorso il path dell'immagine
     * @param isIcon   {@code true} se è un'icona, {@code false} se è un'immagine
     */
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

    /**
     * Carica l'immagine di un item.
     *
     * @param label       il label dell'item
     * @param percorso    il path dell'immagine
     * @param isIndossato {@code true} se il vestito è indossato, {@code false} altrimenti
     */
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

    /**
     * Gestione grafica dei pulsanti dei minigame, a cui viene assegnata un'immagine
     * in base all'opzione che restituiscono.
     *
     * @param button   il pulsante
     * @param percorso il path delle immagini
     */
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

    /**
     * Carica il custom font.
     *
     * @param label  il label a cui si vuole cambiare il font
     * @param size   la grandezza desiderata
     * @param isBold {@code true} se il testo deve essere in grassetto, {@code false} altrimenti
     */
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

    /**
     * Gestisce il tasto per tornare indietro alla finestra recedente.
     *
     * @param label        il label
     * @param frameInizio  il frame attuale
     * @param frameRitorno il frame di ritorno
     * @param isDisposable {@code true} se il frame è disposable all'uscita, {@code false} altrimenti
     */
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

    /**
     * Gestisce il tasto per tornare indietro alla finestra precedente e aggiorna i dati dell'animale.
     *
     * @param label        il label
     * @param frameInizio  il frame attuale
     * @param frameRitorno il frame di ritorno
     * @param isDisposable {@code true} se il frame è disposable all'uscita, {@code false} altrimenti
     * @param tamagotchi   istanza di {@link Tamagotchi} per aggiornare i dati dell'animale a schermo
     */
    public static void tornaIndietro(JLabel label,JFrame frameInizio, JFrame frameRitorno, boolean isDisposable, Tamagotchi tamagotchi) {
        label.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        label.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                tamagotchi.aggiornaLabel(); //aggiorna i dati dell'animale
                frameRitorno.setVisible(true);
                frameInizio.setVisible(false);
                if(isDisposable) {
                    frameInizio.dispose();
                }
            }
        });
    }

    /**
     * Salva i dati prima di uscire dal programma.
     *
     * @param frame      il frame attuale
     * @param animale    l' {@link Animale}
     * @param controller il controller principale che gestisce la logica di sistema.
     */
    public static void salvaEdEsci(JFrame frame, Animale animale, Controller controller) {
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
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
                    JOptionPane.showMessageDialog(null, "Errore nel tantativo di salvare i dati: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                finally {
                    System.exit(0);
                }
            }
        });
    }
}
