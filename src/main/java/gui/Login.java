package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login {
    private JFrame loginFrame;
    private JTextField utenteTextField;
    private JButton loginButton;
    private JButton creaAccountButton;
    private JPanel loginForm;
    private JLabel titolo;
    private JPanel loginPanel;
    private JLabel loginLabel;
    private JLabel utenteLabel;
    private JLabel passwordLabel;
    private JLabel creaAccountLabel;
    private JPasswordField passwordField;
    private ImageIcon backGroundImage;
    private Controller controller;

    public Login(Controller controller) {
        loginFrame = new JFrame("Login");
        loginFrame.setContentPane(loginForm);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setSize(450, 350); //grandezza della finestra
        loginFrame.setLocationRelativeTo(null); //finestra si apre al centro
        loginFrame.setResizable(false); //non cambia dimensione
        this.controller = controller;

        CustomGUI.caricaIcona(loginFrame);
        CustomGUI.caricaFont(titolo,24f,true);
        CustomGUI.caricaFont(loginLabel,18f,true);

        loginFrame.setVisible(true);

        // gestione pulsante crea account
        creaAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utenteTextField.setText(""); //se l'utente ha scritto qualcosa in questi campi, vengono resettati
                passwordField.setText("");
                CreaAccount creaAccount = new CreaAccount(loginFrame, controller); //va alla pagina crea account
                loginFrame.setVisible(false);
            }
        });

        //gestione pulsante login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controller.checkUtente(utenteTextField.getText(), passwordField.getText())) {
                        JOptionPane.showMessageDialog(null, "Accesso effettuato correttamente.");
                        utenteTextField.setText("");
                        passwordField.setText("");
                        Home home = new Home(loginFrame, controller); //va alla home
                        loginFrame.setVisible(false);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    //per mettere immagine al bottone, ricordati di mettere custom create
    private void createUIComponents() {

        backGroundImage = new ImageIcon(
                Login.class.getResource("/img/backGroundDefault.png")
        );
        ImageIcon buttonImage = new ImageIcon(
                Login.class.getResource("/img/buttonBackground.png")
        );

        loginForm = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backGroundImage != null) {
                    g.drawImage(backGroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };

        creaAccountButton = new JButton("Crea account") {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };

        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(false);
        loginButton.setForeground(Color.WHITE);

        creaAccountButton.setContentAreaFilled(false);
        creaAccountButton.setFocusPainted(false);
        creaAccountButton.setOpaque(false);
        creaAccountButton.setForeground(Color.WHITE);
    }
}