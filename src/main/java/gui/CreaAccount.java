package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreaAccount {
    private JTextField utenteTextField;
    private JPasswordField passwordField;
    private JButton creaAccountButton;
    private JPanel creaAccountPanel;
    private JLabel tornaLogin;
    private JLabel titolo;

    public CreaAccount(JFrame loginFrame, Controller controller){
        JFrame creaAccountFrame = new JFrame("Crea un nuovo account");
        creaAccountFrame.setContentPane(creaAccountPanel);
        creaAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        creaAccountFrame.pack();
        creaAccountFrame.setSize(400, 300); //grandezza della finestra
        creaAccountFrame.setLocationRelativeTo(null); //finestra si apre al centro
        creaAccountFrame.setResizable(false); //non cambia dimensione
        ImageIcon icon = new ImageIcon(Login.class.getResource("/img/creaAccountIcona.png")); //icona della finestra
        creaAccountFrame.setIconImage(icon.getImage());
        creaAccountFrame.setVisible(true);

        //immagine account
        titolo.setIcon(new ImageIcon(getClass().getResource("/img/creaAccountIcona.png")));
        titolo.setHorizontalTextPosition(SwingConstants.CENTER);
        titolo.setVerticalTextPosition(SwingConstants.BOTTOM);

        //fonts
        try {
            titolo.setFont(Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fonts/pixel-bold.ttf")
            ).deriveFont(22f));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //gestone pulsante torna alla pagina di login
        tornaLogin.setCursor(new Cursor(Cursor.HAND_CURSOR)); //cambia il cursore quando ci passa sopra

        tornaLogin.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                loginFrame.setVisible(true);
                creaAccountFrame.setVisible(false);
            }
        });

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
                    creaAccountFrame.setVisible(false);
                }
                catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
