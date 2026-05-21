package gui;

import javax.swing.*;

public class CreaAccount {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton creaAccountButton;
    private JPanel creaAccountPanel;

    public CreaAccount(JFrame loginFrame){
        JFrame creaAccount = new JFrame("Crea un nuovo account");
        creaAccount.setContentPane(creaAccountPanel);
        creaAccount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        creaAccount.pack();
        creaAccount.setSize(400, 300); //grandezza della finestra
        creaAccount.setLocationRelativeTo(null); //finestra si apre al centro
        creaAccount.setResizable(false); //non cambia dimensione
        creaAccount.setVisible(true);

    }

    /*public static void main(String[] args) {
        JFrame creaAccount = new JFrame("Crea un nuovo account");
        creaAccount.setContentPane(new CreaAccount().creaAccountPanel);
        creaAccount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        creaAccount.pack();
        creaAccount.setSize(400, 300); //grandezza della finestra
        creaAccount.setLocationRelativeTo(null); //finestra si apre al centro
        creaAccount.setResizable(false); //non cambia dimensione
        creaAccount.setVisible(true);
    }*/
}
