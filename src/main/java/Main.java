import controller.Controller;
import gui.Login;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        try {
            controller.inizializzaDati();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Impossibile connettersi al database di gioco.\nVerifica che PostgreSQL sia attivo e riprova.",
                    "Errore di Connessione",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }
        Login login = new Login(controller);
    }
}