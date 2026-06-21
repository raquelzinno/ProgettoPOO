package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;
import model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteImplementazionePostgresDAO implements UtenteDAO {
    @Override
    public void salvaUtente(Utente utente) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //EFFETTUO LA QUERY
        PreparedStatement ps = connection.prepareStatement("INSERT INTO \"Utente\" (\"login\", \"password\") VALUES (?, ?);");

        ps.setString(1, utente.getLogin());
        ps.setString(2, utente.getPassword());

        //CONTROLLO CHE LA QUERY SIA STATA ESEGUITA
        int righeInserite = ps.executeUpdate();

        if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
            System.out.println("Utente salvato nel Database con successo!");
        }

        //CHIUDO CONNESSIONE
        connection.close();
    }

    @Override
    public void aggiornaPassword(String login, String passwordVecchia, String passwordNuova) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //EFFETTUO LA QUERY
        String sql = "UPDATE \"Utente\" SET \"password\" = ? WHERE \"login\" = ? AND \"password\" = ?;";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, passwordNuova);
        ps.setString(2, login);
        ps.setString(3, passwordVecchia);


        int righeInserite = ps.executeUpdate();

        if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
            System.out.println("Utente aggiornato nel Database con successo!");
        }

        //CHIUDO CONNESSIONE
        connection.close();
    }

    @Override
    public boolean cercaUtente(String login, String password) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //EFFETTUO LA QUERY
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Utente\" WHERE \"login\" = ? AND \"password\" = ?;");

        ps.setString(1, login);
        ps.setString(2, password);

        //RESULT SET
        ResultSet rs = ps.executeQuery();

        //CONTROLLO CHE LA QUERY SIA STATA ESEGUITA
        if (rs.next()) {
            System.out.println("Utente trovato nel Database con successo!");
            return true;
        }

        //CHIUDO CONNESSIONE
        rs.close();
        connection.close();
        return false;
    }
}
