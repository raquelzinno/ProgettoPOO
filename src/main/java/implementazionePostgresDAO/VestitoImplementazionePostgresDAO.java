package implementazionePostgresDAO;

import dao.VestitoDAO;
import database.ConnessioneDatabase;
import model.Item;
import model.Vestito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VestitoImplementazionePostgresDAO implements VestitoDAO {
    @Override
    public ArrayList<Vestito> recuperaListaVestiti() throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        ArrayList<Vestito> listaItem = new ArrayList<>();

        String sql = "SELECT * FROM \"Vestito\"";
        //EFFETTUO LA QUERY
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            try(ResultSet rs = ps.executeQuery()) {
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

        String queryVestito = "SELECT v.* FROM \"InventarioVestito\" iv " +
                "JOIN \"Vestito\" v ON iv.\"idVestito\" = v.\"idVestito\" " +
                "WHERE iv.\"idUtente\" = ?";

        try(PreparedStatement ps2 = connection.prepareStatement(queryVestito)) {
            ps2.setInt(1, idUtente);

            try (ResultSet rs = ps2.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    int costo = rs.getInt("costo");
                    int boostFame = rs.getInt("boostFame");
                    int boostEnergia = rs.getInt("boostEnergia");
                    String img = rs.getString("imagePath");

                    inventarioVestiti.add(new Vestito(nome, costo, null, boostEnergia, boostFame, img));
                }
            }
        }
        return inventarioVestiti;
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

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            ps.setInt(2, idVestito);

            //CONTROLLO CHE LA QUERY SIA STATA ESEGUITA
            int righeInserite = ps.executeUpdate();
            if (righeInserite > 0) { //verifichiamo se le righe sono state effettivamente inserite
                return true;
            }
        }
        return false;
    }
}
