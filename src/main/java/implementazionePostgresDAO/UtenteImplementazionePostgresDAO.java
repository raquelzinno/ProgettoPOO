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

    //metodo chiamato dal controller quando l'utente compra un Item
    public boolean salvaAcquisto(int idUtente, Item item) throws SQLException{
        if (item instanceof Cibo) {
            return aggiungiAInventarioCibo(idUtente, item);
        }
        else if (item instanceof Vestito) {
            return aggiungiAInventarioVestito(idUtente, item);
        }
        return false;
    }

    public boolean aggiungiAInventarioCibo(int idUtente, Item item) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //RECUPERO L'ID DEL CIBO DAL DATABASE
        int idCibo = -1;
        String sqlCercaCibo = "SELECT \"idCibo\" FROM \"Cibo\" WHERE \"nome\" = ?;";

        try (PreparedStatement psCibo = connection.prepareStatement(sqlCercaCibo)) {
            psCibo.setString(1, item.getNome());
            try (ResultSet rs = psCibo.executeQuery()) {
                if (rs.next()) {
                    idCibo = rs.getInt("idCibo");
                }
            }
        }

        //ESEGUO LA QUERY
        String sql = "INSERT INTO \"InventarioCibo\" (\"idUtente\", \"idCibo\") VALUES (?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, idUtente);
        ps.setInt(2, idCibo);

        //CONTROLLO CHE LA QUERY SIA STATA ESEGUITA
        int righeInserite = ps.executeUpdate();

        if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
            System.out.println("Inventario aggiornato nel Database con successo!");

            //CHIUDO CONNESSIONE
            connection.close();
            return true;
        }else{
            //CHIUDO CONNESSIONE
            connection.close();
            return false;
        }

    }

    public boolean aggiungiAInventarioVestito(int idUtente, Item item) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //RECUPERO L'ID DEL VESTITO DAL DATABASE
        int idVestito = -1;
        String sqlCercaVestito = "SELECT \"idVestito\" FROM \"Vestito\" WHERE \"nome\" = ?;";

        try (PreparedStatement psVestito = connection.prepareStatement(sqlCercaVestito)) {
            psVestito.setString(1, item.getNome());
            try (ResultSet rs = psVestito.executeQuery()) {
                if (rs.next()) {
                    idVestito = rs.getInt("idVestito");
                }
            }
        }

        //ESEGUO LA QUERY (idAnimale è impostato a NULL dato che nessun animale sta indossando il vestito)
        String sql = "INSERT INTO \"InventarioVestito\" (\"idUtente\", \"idVestito\") VALUES (?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, idUtente);
        ps.setInt(2, idVestito);

        //CONTROLLO CHE LA QUERY SIA STATA ESEGUITA
        int righeInserite = ps.executeUpdate();

        if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
            //CHIUDO CONNESSIONE
            connection.close();
            return true;
        }else{
            //CHIUDO CONNESSIONE
            connection.close();
            return false;
        }
    }
}

