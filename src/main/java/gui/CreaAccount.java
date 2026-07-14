package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class CreaAccount {
    private JTextField utenteTextField;
    private JPasswordField passwordField;
    private JButton creaAccountButton;
    private JPanel creaAccountPanel;
    private JLabel tornaLogin;
    private JLabel titolo;
    private ImageIcon backGroundImage;

    public CreaAccount(JFrame loginFrame, Controller controller){
        JFrame creaAccountFrame = new JFrame("Crea un nuovo account");
        creaAccountFrame.setContentPane(creaAccountPanel);
        creaAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        creaAccountFrame.pack();
        creaAccountFrame.setSize(400, 300); //grandezza della finestra
        creaAccountFrame.setLocationRelativeTo(null); //finestra si apre al centro
        creaAccountFrame.setResizable(false); //non cambia dimensione

        CustomGUI.caricaIcona(creaAccountFrame);
        CustomGUI.caricaFont(titolo,22f,true);
        CustomGUI.caricaImmagineSopraTesto(titolo,"img/creaAccountIcona.png");

        creaAccountFrame.setVisible(true);

        CustomGUI.tornaIndietro(tornaLogin,creaAccountFrame,loginFrame,true);

        //gestione pulsante crea account
        creaAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String nomeUtente = utenteTextField.getText();
                    String password = passwordField.getText();
                    controller.creaUtente(nomeUtente, password);
                    JOptionPane.showMessageDialog(null, "Account creato con successo!");

                    //torna alla pagina di login
                    loginFrame.setVisible(true);
                    creaAccountFrame.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                } catch(RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() { //custom create del panel per gestire lo sfondo
        backGroundImage = new ImageIcon(getClass().getResource("/img/backGroundDefault.png")); //immagine sfondo
        creaAccountPanel = new JPanel() {
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

        creaAccountButton = new JButton("Crea account") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };

        creaAccountButton.setContentAreaFilled(false);
        creaAccountButton.setFocusPainted(false);
        creaAccountButton.setOpaque(false);
        creaAccountButton.setForeground(Color.WHITE);
    }
}
