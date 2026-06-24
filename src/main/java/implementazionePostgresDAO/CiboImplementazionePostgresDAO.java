package implementazionePostgresDAO;

import dao.CiboDAO;
import database.ConnessioneDatabase;
import model.Cibo;
import model.Item;
import model.TipoCibo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CiboImplementazionePostgresDAO implements CiboDAO {
    @Override
    public ArrayList<Cibo> recuperaListaCibo() throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        ArrayList<Cibo> listaItem = new ArrayList<>();

        String sql = "SELECT * FROM \"Cibo\"";
        //EFFETTUO LA QUERY
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try(ResultSet rs = ps.executeQuery()) {

                //ELABORO IL RESULT SET
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    int prezzo = rs.getInt("costo");
                    String img = rs.getString("imagePath");
                    String tipo = rs.getString("tipo");
                    int punti = rs.getInt("puntiFame");
                    TipoCibo tipoCibo = TipoCibo.valueOf(tipo);

                    listaItem.add(new Cibo(nome, prezzo, null, tipoCibo, punti, img));
                }
            }
        }
        return listaItem;
    }

    @Override
    public ArrayList<Cibo> recuperaInventarioCibo(int idUtente) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        ArrayList<Cibo> inventarioCibo = new ArrayList<Cibo>();

        String queryCibo = "SELECT c.* FROM \"InventarioCibo\" ic " +
                "JOIN \"Cibo\" c ON ic.\"idCibo\" = c.\"idCibo\" " +
                "WHERE ic.\"idUtente\" = ?";

        try(PreparedStatement ps = connection.prepareStatement(queryCibo)) {
            ps.setInt(1, idUtente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    int costo = rs.getInt("costo");
                    String tipo = rs.getString("tipo");
                    int fame = rs.getInt("puntiFame");
                    String img = rs.getString("imagePath");

                    TipoCibo tipoCibo = TipoCibo.valueOf(tipo);

                    //crea l'oggetto Cibo (il negozio può rimanere null nell'inventario)
                    inventarioCibo.add(new Cibo(nome, costo, null, tipoCibo, fame, img));
                }
            }
        }
        return inventarioCibo;
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

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            ps.setInt(2, idCibo);

            //CONTROLLO CHE LA QUERY SIA STATA ESEGUITA
            int righeInserite = ps.executeUpdate();
            if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
                System.out.println("Inventario aggiornato nel Database con successo!");
                return true;
            }
        }
        return false;
    }
}
