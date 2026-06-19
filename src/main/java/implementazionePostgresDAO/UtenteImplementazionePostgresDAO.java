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
        PreparedStatement ps = connection.prepareStatement("INSERT INTO \"Utente\" (\"user\", \"password\") VALUES (?, ?);");
        //ResultSet rs = ps.executeQuery();

        ps.setString(1, utente.getLogin());
        ps.setString(2, utente.getPassword());

        //CONTROLLO CHE LA QUERY SIA STATA ESEGUITA
        int righeInserite = ps.executeUpdate();

        if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
            System.out.println("Utente salvato nel Database con successo!");
        }

        /*//ELABORO IL RESULT SET
        while(rs.next())
        {
            ps.setString(1, utente.getLogin());
            ps.setString(2, utente.getPassword());
        }*/

        //CHIUDO CONNESSIONE
        //rs.close();
        connection.close();
    }

    @Override
    public void aggiornaNomeUtente(String userVecchio, String userNuovo) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //EFFETTUO LA QUERY
        String sql = "UPDATE \"Utente\" " + "SET \"user\" = "+userNuovo+" WHERE \"user\" = '"+userVecchio+"' ;";

        PreparedStatement updatePrezzoPS = connection.prepareStatement(sql);


        int righeInserite = updatePrezzoPS.executeUpdate();

        if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
            System.out.println("Utente aggiornato nel Database con successo!");
        }

        //CHIUDO CONNESSIONE
        connection.close();
    }
}
