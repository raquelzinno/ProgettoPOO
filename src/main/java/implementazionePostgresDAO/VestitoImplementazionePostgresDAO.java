package implementazionePostgresDAO;

import dao.VestitoDAO;
import database.ConnessioneDatabase;
import model.Item;
import model.Vestito;

import java.sql.*;
import java.util.ArrayList;

public class VestitoImplementazionePostgresDAO implements VestitoDAO {
    @Override
    public ArrayList<Vestito> recuperaListaVestiti() throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        ArrayList<Vestito> listaItem = new ArrayList<>();

        String sql = "SELECT * FROM \"Vestito\"";
        //EFFETTUO LA QUERY
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                //ELABORO IL RESULT SET
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    int costo = rs.getInt("costo");
                    String img = rs.getString("imagePath");
                    int boostFame = rs.getInt("boostFame");
                    int boostEnergia = rs.getInt("boostEnergia");

                    listaItem.add(new Vestito(nome, costo, null, boostEnergia, boostFame, img));
                }
            }
        }
        return listaItem;
    }

    @Override
    public ArrayList<Vestito> recuperaInventarioVestiti(int idUtente) throws SQLException {
        Connection connection = ConnessioneDatabase.getInstance().connection;

        ArrayList<Vestito> inventarioVestiti = new ArrayList<Vestito>();

        String queryVestito = "SELECT v.*, iv.\"idIstanza\", iv.\"idAnimale\" FROM \"InventarioVestito\" iv " +
                "JOIN \"Vestito\" v ON iv.\"idVestito\" = v.\"idVestito\" " +
                "WHERE iv.\"idUtente\" = ?";

        try (PreparedStatement ps2 = connection.prepareStatement(queryVestito)) {
            ps2.setInt(1, idUtente);

            try (ResultSet rs = ps2.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    int costo = rs.getInt("costo");
                    int idIstanza = rs.getInt("idIstanza");
                    int boostFame = rs.getInt("boostFame");
                    int boostEnergia = rs.getInt("boostEnergia");
                    String img = rs.getString("imagePath");
                    Integer oggettoIdAnimale = (Integer) rs.getObject("idAnimale");

                    if (oggettoIdAnimale == null) {
                        inventarioVestiti.add(new Vestito(nome, costo, null, idIstanza, boostEnergia, boostFame, img));
                    } else {
                        inventarioVestiti.add(new Vestito(nome, costo, null, idIstanza, boostEnergia, boostFame, img, oggettoIdAnimale));
                    }
                }
            }
        }
        return inventarioVestiti;
    }

    public int aggiungiAInventarioVestito(int idUtente, Item item) throws SQLException {
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

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idUtente);
            ps.setInt(2, idVestito);
            ps.executeUpdate();

            //RECUPERO L'ID APPENA GENERATO
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idIstanza = generatedKeys.getInt(1); // Prende il valore della prima colonna generata (la PK)
                    return idIstanza; // Restituiamo l'ID al Controller
                }
            }
        }
        throw new SQLException("Inserimento riuscito, ma non è stato possibile recuperare l'idIstanza.");
    }

    @Override
    public void eliminaDaInventario(int idIstanza) throws SQLException{
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        String sql = "DELETE FROM \"InventarioVestito\" WHERE \"idIstanza\" = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idIstanza);

            /* --- debug ---
            int righeInserite = ps.executeUpdate();
            if (righeInserite > 0) {
                System.out.println("l'item è stato eliminato dal Database con successo!");
            }*/
        }
    }

    public void indossaVestito(int idIstanza,int idAnimale) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //EFFETTUO LA QUERY
        String sql = "UPDATE \"InventarioVestito\" SET \"idAnimale\" = ? WHERE \"idIstanza\" = ?;";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAnimale);
            ps.setInt(2, idIstanza);

            /* --- debug ---
            int righeInserite = ps.executeUpdate();
            if (righeInserite > 0) {
                System.out.println("l'item è stato indossato con successo");
            }*/
        }
    }

    public void rimuoviVestito(int idIstanza) throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //EFFETTUO LA QUERY
        String sql = "UPDATE \"InventarioVestito\" SET \"idAnimale\" = NULL WHERE \"idIstanza\" = ?;";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idIstanza);

            /* --- debug ---
            int righeInserite = ps.executeUpdate();
            if (righeInserite > 0) {
                System.out.println("l'item è stato rimosso con successo");
            }*/
        }
    }
}

