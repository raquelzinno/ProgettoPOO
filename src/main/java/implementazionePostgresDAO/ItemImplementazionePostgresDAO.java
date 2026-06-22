package implementazionePostgresDAO;

import dao.CiboDAO;
import dao.ItemDAO;
import dao.VestitoDAO;
import database.ConnessioneDatabase;
import model.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemImplementazionePostgresDAO implements ItemDAO {
    @Override
    public ArrayList<Item> recuperaListaItem() throws SQLException{ //da valutare se mettere in negoziodao
        CiboDAO ciboDAO = new CiboImplementazionePostgresDAO();
        VestitoDAO vestitoDAO = new VestitoImplementazionePostgresDAO();
        ArrayList<Item> listaCompleta = new ArrayList<>();

        //recupera i cibi e li aggiunge alla lista completa
        listaCompleta.addAll(ciboDAO.recuperaListaCibo());

        //recupera i vestiti e li aggiunge alla lista completa
        listaCompleta.addAll(vestitoDAO.recuperaListaVestiti());

        return listaCompleta;
    }

    public ArrayList<Item> recuperaItemAggiornati(String login) throws SQLException{
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

        //CREO LA LISTA PER GLI ITEM
        ArrayList<Item> inventario = new ArrayList<>();

        //recupera i cibi posseduti dall'utente
        String queryCibo = "SELECT c.* FROM \"InventarioCibo\" ic " +
                "JOIN \"Cibo\" c ON ic.\"idCibo\" = c.\"idCibo\" " +
                "WHERE ic.\"idUtente\" = ?";

        PreparedStatement ps = connection.prepareStatement(queryCibo);

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
                inventario.add(new Cibo(nome, costo, null, tipoCibo, fame, img));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String queryVestito = "SELECT v.* FROM \"InventarioVestito\" iv " +
                "JOIN \"Vestito\" v ON iv.\"idVestito\" = v.\"idVestito\" " +
                "WHERE iv.\"idUtente\" = ?";

        PreparedStatement ps2 = connection.prepareStatement(queryVestito);
        ps2.setInt(1, idUtente);

        try (ResultSet rs = ps2.executeQuery()) {
            while (rs.next()) {
                String nome = rs.getString("nome");
                int costo = rs.getInt("costo");
                int boostFame = rs.getInt("boostFame");
                int boostEnergia = rs.getInt("boostEnergia");
                String img = rs.getString("imagePath");

                inventario.add(new Vestito(nome, costo, null, boostEnergia, boostFame, img));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventario;
    }
}
