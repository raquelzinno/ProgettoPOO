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
    public int recuperaId(int idUtente, String nomeAnimale) throws SQLException {
        Connection connection = ConnessioneDatabase.getInstance().connection;
        
        String sql = "SELECT \"idAnimale\" FROM \"Animale\" WHERE \"idUtente\" = ? AND \"nome\" = ?;";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,idUtente);
            ps.setString(2,nomeAnimale);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idAnimale");
                }
            }
        }  
        return -1;
    }

    @Override
    public void salvaAnimale(Animale animale, int idUtente) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        // EFFETTUO LA QUERY
        String sql = "INSERT INTO \"Animale\" (\"nome\", \"fame\", \"energia\", \"punti\", " +
                "\"dorme\", \"fameMax\", \"energiaMax\", \"specie\", \"idUtente\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, animale.getNome());
            ps.setInt(2, animale.getFame());
            ps.setInt(3, animale.getEnergia());
            ps.setInt(4, animale.getPunti());
            ps.setBoolean(5, animale.isDorme());
            ps.setInt(6, animale.getFameMax());
            ps.setInt(7, animale.getEnergiaMax());
            if (animale instanceof Orso) {
                ps.setString(8, "Orso");
            } else if (animale instanceof Pinguino) {
                ps.setString(8, "Pinguino");
            }
            ps.setInt(9, idUtente);

            /* --- debug ---
            int righeInserite = ps.executeUpdate();
            if (righeInserite > 0) {
                System.out.println("Animale salvato nel Database con successo!");
            }*/
        }
    }

    @Override
    public ArrayList<Animale> recuperaListaAnimali(int idUtente) throws SQLException{
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //CREO LA LISTA PER GLI ANIMALI
        ArrayList<Animale> listaAnimali = new ArrayList<>();

        //QUERY PER RECUPERARE GLI ANIMALI
        String sql = "SELECT * FROM \"Animale\" WHERE \"idUtente\" = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUtente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Animale animale;
                    String specie = rs.getString("specie");
                    String nomeAnimale = rs.getString("nome");

                    if ("Orso".equals(specie)) {
                        animale = new Orso(nomeAnimale);
                    } else if ("Pinguino".equals(specie)) {
                        animale = new Pinguino(nomeAnimale);
                    } else {
                        continue;
                    }

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
        return listaAnimali;
    }

    @Override
    public void modificaNome(int idAnimale, String nome) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //EFFETTUO LA QUERY
        String sql = "UPDATE \"Animale\" SET \"nome\" = ? WHERE \"idAnimale\" = ?;";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nome); 
            ps.setInt(2, idAnimale);

            /* --- debug ---
            int righeModificate = ps.executeUpdate();
            if (righeModificate > 0) { //verifichiamo se le righe sono state effettivamente inserite
                System.out.println("Nome animale aggiornato nel Database con successo!");           
            }*/
        }                                                                                                                                                                                      
    }

    @Override
    public void eliminaAnimale(int idAnimale) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //EFFETTUO LA QUERY
        String sql = "DELETE FROM \"Animale\" WHERE \"idAnimale\" = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAnimale);

            /* --- debug ---
            int righeInserite = ps.executeUpdate();
            if (righeInserite > 0) {
                System.out.println("Animale eliminato dal Database con successo!");
            }*/
        }
    }

    @Override
    public void aggiornaStatoAnimale(Animale animale, int idAnimale) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;
        
        //EFFETTUO LA QUERY
        String sql = "UPDATE \"Animale\" SET \"fame\" = ?, \"energia\" = ?, \"punti\" = ?, \"dorme\" = ?, \"fameMax\" = ?, \"energiaMax\" = ? WHERE \"idAnimale\" = ?;";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, animale.getFame());
            ps.setInt(2, animale.getEnergia());
            ps.setInt(3, animale.getPunti());
            ps.setBoolean(4, animale.isDorme());
            ps.setInt(5, animale.getFameMax());
            ps.setInt(6, animale.getEnergiaMax());
            ps.setInt(7, idAnimale);

            /* --- debug ---
            int righeInserite = ps.executeUpdate();
            if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
                System.out.println("Animale aggiornato nel Database con successo!");
            }*/
        }
    }
    public void resetStatoSonno(int idUtente) throws SQLException {  //il programma potrebbe avere un arresto anomalo nel momento di salvataggio dello stato di sonno
        Connection connection = ConnessioneDatabase.getInstance().connection;     //per assicurarci che all'avvio lo stato sonno sia sempre false, resettiamo questi dati  
        String sqlReset = "UPDATE \"Animale\" SET \"dorme\" = FALSE WHERE \"idUtente\" = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlReset)) {
            ps.setInt(1, idUtente);

            /* --- debug ---
            int righeInserite = ps.executeUpdate();
            if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite  
                System.out.println("Animale aggiornato nel Database con successo!");
            } */
        }
    }
}
