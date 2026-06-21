package implementazionePostgresDAO;

import dao.AnimaleDAO;
import database.ConnessioneDatabase;
import model.Animale;
import model.Orso;
import model.Pinguino;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnimaleImplementazionePostgresDAO implements AnimaleDAO {
    @Override
    public void salvaAnimale(Animale animale, String login, ArrayList<Animale> listaAnimali) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //RECUPERO L'ID DELL'UTENTE DAL DATABASE
        int idUtente = -1;
        String sqlCercaUtente = "SELECT \"idUtente\" FROM \"Utente\" WHERE \"login\" = ?;";

        try (PreparedStatement psUtente = connection.prepareStatement(sqlCercaUtente)) {
            psUtente.setString(1, login);
            try (ResultSet rs = psUtente.executeQuery()) {
                if (rs.next()) {
                    idUtente = rs.getInt("idUtente");
                }
            }
        }

        //EFFETTUO LA QUERY
        String sql = "INSERT INTO \"Animale\" (\"nome\", \"fame\", \"energia\", \"punti\", " +
                "\"dorme\", \"fameMax\", \"energiaMax\", \"specie\", \"idUtente\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, animale.getNome());
        ps.setInt(2, animale.getFame());
        ps.setInt(3, animale.getEnergia());
        ps.setInt(4, animale.getPunti());
        ps.setBoolean(5, animale.isDorme());
        ps.setInt(6, animale.getFameMax());
        ps.setInt(7, animale.getEnergiaMax());
        if(animale instanceof Orso)
            ps.setString(8, "Orso");
        else if(animale instanceof Pinguino)
            ps.setString(8, "Pinguino");
        ps.setInt(9, idUtente);

        listaAnimali.add(animale);


        //CONTROLLO CHE LA QUERY SIA STATA ESEGUITA
        int righeInserite = ps.executeUpdate();

        if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
            System.out.println("Animale salvato nel Database con successo!");
        }

        //CHIUDO CONNESSIONE
        connection.close();
    }

    @Override
    public ArrayList<Animale> recuperaListaAnimali(String login) throws SQLException{
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //CREO LA LISTA PER GLI ANIMALI
        ArrayList<Animale> listaAnimali = new ArrayList<>();

        //RECUPERO L'ID DELL'UTENTE DAL DATABASE
        int idUtente = -1;
        String sqlCercaUtente = "SELECT \"idUtente\" FROM \"Utente\" WHERE \"login\" = ?;";

        try (PreparedStatement psUtente = connection.prepareStatement(sqlCercaUtente)) {
            psUtente.setString(1, login);
            try (ResultSet rs = psUtente.executeQuery()) {
                if (rs.next()) {
                    idUtente = rs.getInt("idUtente");
                }
            }
        }

        //EFFETTUO LA QUERY
        String sql = "SELECT * FROM \"Animale\" WHERE \"idUtente\" = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUtente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Animale animale;
                    String specie = rs.getString("specie");
                    String nomeAnimale = rs.getString("nome");

                    //istanza la sottoclasse corretta in base alla stringa nel database
                    if ("Orso".equals(specie)) {
                        animale = new Orso(nomeAnimale);
                    } else if ("Pinguino".equals(specie)) {
                        animale = new Pinguino(nomeAnimale);
                    } else {
                        continue;
                    }

                    //ricostruisco l'oggetto dai dati del database
                    animale.setFame(rs.getInt("fame"));
                    animale.setEnergia(rs.getInt("energia"));
                    animale.setPunti(rs.getInt("punti"));
                    animale.setDorme(rs.getBoolean("dorme"));
                    animale.setFameMax(rs.getInt("fameMax"));
                    animale.setEnergiaMax(rs.getInt("energiaMax"));

                    listaAnimali.add(animale);
                }
            }
        }

        //CHIUDO CONNESSIONE
        connection.close();

        return listaAnimali;
    }

    @Override
    public void modificaNome(String login, Animale animale, String nome) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //RECUPERO L'ID DELL'UTENTE DAL DATABASE
        int idUtente = -1;
        String sqlCercaUtente = "SELECT \"idUtente\" FROM \"Utente\" WHERE \"login\" = ?;";

        try (PreparedStatement psUtente = connection.prepareStatement(sqlCercaUtente)) {
            psUtente.setString(1, login);
            try (ResultSet rs = psUtente.executeQuery()) {
                if (rs.next()) {
                    idUtente = rs.getInt("idUtente");
                }
            }
        }

        //EFFETTUO LA QUERY
        String sql = "UPDATE \"Animale\" SET \"nome\" = ? WHERE \"nome\" = ? AND \"idUtente\" = ?;";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, nome);
        ps.setString(2, animale.getNome());
        ps.setInt(3, idUtente);

        int righeInserite = ps.executeUpdate();

        if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
            System.out.println("Nome animale aggiornato nel Database con successo!");
        }

        //CHIUDO CONNESSIONE
        connection.close();
    }

    @Override
    public void eliminaAnimale(Animale animale, String login) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //RECUPERO L'ID DELL'UTENTE DAL DATABASE
        int idUtente = -1;
        String sqlCercaUtente = "SELECT \"idUtente\" FROM \"Utente\" WHERE \"login\" = ?;";

        try (PreparedStatement psUtente = connection.prepareStatement(sqlCercaUtente)) {
            psUtente.setString(1, login);
            try (ResultSet rs = psUtente.executeQuery()) {
                if (rs.next()) {
                    idUtente = rs.getInt("idUtente");
                }
            }
        }

        //EFFETTUO LA QUERY
        String sql = "DELETE FROM \"Animale\" WHERE \"nome\" = ? AND \"idUtente\" = ?";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, animale.getNome());
        ps.setInt(2, idUtente);

        int righeInserite = ps.executeUpdate();

        if (righeInserite > 0) {
            System.out.println("Animale eliminato dal Database con successo!");
        }

        //CHIUDO CONNESSIONE
        connection.close();
    }

    @Override
    public void aggiornaStatoAnimale(Animale animale, String login) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //CREO LA LISTA PER GLI ANIMALI
        ArrayList<Animale> listaAnimali = new ArrayList<>();

        //RECUPERO L'ID DELL'UTENTE DAL DATABASE
        int idUtente = -1;
        String sqlCercaUtente = "SELECT \"idUtente\" FROM \"Utente\" WHERE \"login\" = ?;";

        try (PreparedStatement psUtente = connection.prepareStatement(sqlCercaUtente)) {
            psUtente.setString(1, login);
            try (ResultSet rs = psUtente.executeQuery()) {
                if (rs.next()) {
                    idUtente = rs.getInt("idUtente");
                }
            }
        }

        //EFFETTUO LA QUERY
        String sql = "UPDATE \"Animale\" SET \"fame\" = ?, \"energia\" = ?, \"punti\" = ?, \"dorme\" = ?, \"fameMax\" = ?, \"energiaMax\" = ? WHERE \"nome\" = ? AND \"idUtente\" = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, animale.getFame());
        ps.setInt(2, animale.getEnergia());
        ps.setInt(3, animale.getPunti());
        ps.setBoolean(4, animale.isDorme());
        ps.setInt(5, animale.getFameMax());
        ps.setInt(6, animale.getEnergiaMax());
        ps.setString(7, animale.getNome());
        ps.setInt(8, idUtente);

        //CONTROLLO CHE LA QUERY SIA STATA ESEGUITA
        int righeInserite = ps.executeUpdate();

        if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
            System.out.println("Animale aggiornato nel Database con successo!");
        }

        //CHIUDO CONNESSIONE
        connection.close();
    }
}
