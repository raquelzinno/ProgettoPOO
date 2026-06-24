package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteImplementazionePostgresDAO implements UtenteDAO {
    @Override
    public int recuperaId(String login) throws SQLException {
        Connection connection = ConnessioneDatabase.getInstance().connection;

        String sql = "SELECT \"idUtente\" FROM \"Utente\" WHERE \"login\" = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idUtente");
                }
            }
        }
        return -1;
    }

    @Override
    public void salvaUtente(String login, String password) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        String query = "INSERT INTO \"Utente\" (\"login\", \"password\") VALUES (?, ?);";
        //EFFETTUO LA QUERY
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, login);
            ps.setString(2, password);

            int righeInserite = ps.executeUpdate();
            if (righeInserite > 0) {
                System.out.println("Utente salvato nel Database con successo!");
            }

        }
    }

    public boolean controlloLogin(String login) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        String query = "SELECT * FROM \"Utente\" WHERE \"login\" = ?;";
        //EFFETTUO LA QUERY
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Login già esistente nel database");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void aggiornaPassword(int idUtente, String passwordNuova) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //EFFETTUO LA QUERY
        String sql = "UPDATE \"Utente\" SET \"password\" = ? WHERE \"idUtente\" = ?;";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, passwordNuova);
            ps.setInt(2, idUtente);
            int righeModificate = ps.executeUpdate();

            if (righeModificate > 0) { //verifichiamo se le righe sono state effettivamente inserite
                System.out.println("Utente aggiornato nel Database con successo!");
            }
        }
    }

    @Override
    public boolean cercaUtente(String login, String password) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        String sqlCercaUtente = "SELECT * FROM \"Utente\" WHERE \"login\" = ? AND \"password\" = ?;";

        try(PreparedStatement ps = connection.prepareStatement(sqlCercaUtente)) {
            ps.setString(1, login);
            ps.setString(2, password);

            try( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Utente trovato nel Database con successo!");
                    return true;
                }
            }
        }
        return false;
    }
}

