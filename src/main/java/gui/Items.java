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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Items {
    private JPanel itemsPanel;
    private JLabel goBack;
    private JButton usaButton;
    private JList listaItem;

    public Items(JFrame tamagotchiFrame, Controller controller, Animale animale){
        JFrame itemsFrame = new JFrame("I tuoi items");
        itemsFrame.setContentPane(itemsPanel);
        itemsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        itemsFrame.pack();
        itemsFrame.setSize(500, 300); //grandezza della finestra
        itemsFrame.setLocationRelativeTo(null); //finestra si apre al centro
        itemsFrame.setResizable(false); //non cambia dimensione
        itemsFrame.setVisible(true);


        goBack.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        goBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tamagotchiFrame.setVisible(true);
                itemsFrame.setVisible(false);
            }
        });
    }
}
