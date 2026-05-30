package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private static JFrame loginFrame;
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

    public static void main(String[] args) {
        Controller controller = new Controller();
        Login login = new Login();
        loginFrame = new JFrame("Login");
        loginFrame.setContentPane(login.loginForm);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setSize(450, 350); //grandezza della finestra
        loginFrame.setLocationRelativeTo(null); //finestra si apre al centro
        loginFrame.setResizable(false); //non cambia dimensione

        ImageIcon icon = new ImageIcon(Login.class.getResource("/img/creaAccountIcona.png")); //icona della finestra
        loginFrame.setIconImage(icon.getImage());

        loginFrame.setVisible(true);

        //fonts
        try {
            login.titolo.setFont(Font.createFont(
                    Font.TRUETYPE_FONT,
                    login.getClass().getResourceAsStream("/fonts/pixel-bold.ttf")
            ).deriveFont(24f));

            login.loginLabel.setFont(Font.createFont(
                    Font.TRUETYPE_FONT,
                    login.getClass().getResourceAsStream("/fonts/pixel-bold.ttf")
            ).deriveFont(18f));

        } catch (Exception e) {
            e.printStackTrace();
        }


        // gestione pulsante crea account
        login.creaAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login.utenteTextField.setText(""); //se l'utente ha scritto qualcosa in questi campi, vengono resettati
                login.passwordField.setText("");
                CreaAccount creaAccount = new CreaAccount(loginFrame, controller); //va alla pagina crea account
                loginFrame.setVisible(false);
            }
        });

        //gestione pulsante login
        login.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controller.checkUtente(login.utenteTextField.getText(), login.passwordField.getText())) {
                        JOptionPane.showMessageDialog(null, "Accesso effettuato correttamente.");
                        login.utenteTextField.setText("");
                        login.passwordField.setText("");
                        Home home = new Home(loginFrame, controller); //va alla home
                        loginFrame.setVisible(false);
                    }
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() {
        backGroundImage = new ImageIcon(Login.class.getResource("/img/backGroundDefault.png"));

        loginForm = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backGroundImage != null) {
                    g.drawImage(backGroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
    }
}