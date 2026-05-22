package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JPanel mainPanel;
    private JButton creaAnimaleButton;
    private JPanel vuotoPanel;
    private JPanel creaAnimalePanel;
    private JPanel unAnimalePanel;
    private JLabel orso;
    private JFrame frameHome;
    private Controller controller;

    /*public static void main(String[] args) {
        Home home = new Home();
        frameHome = new JFrame("Home");
        frameHome.setContentPane(new Home().mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setSize(450, 350); //grandezza della finestra
        frameHome.setLocationRelativeTo(null); //finestra si apre al centro
        frameHome.setVisible(true);


        home.unAnimalePanel.setVisible(true);

        home.mainPanel.revalidate();
        home.mainPanel.repaint();
    }*/

    public Home(JFrame loginFrame, Controller controller) {
        // controller = new Controller();
        // Add action listeners or other initialization code here
        //Home home = new Home();
        frameHome = new JFrame("Home");
        //frameHome.setContentPane(new Home().mainPanel);
        frameHome.setContentPane(mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setSize(450, 350); //grandezza della finestra
        frameHome.setLocationRelativeTo(null); //finestra si apre al centro
        frameHome.setVisible(true);

        //gestione pulsante crea animale
        creaAnimaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreaAnimale creaAnimale = new CreaAnimale(frameHome, controller);
                frameHome.setVisible(false);
            }
        });

    }


}
