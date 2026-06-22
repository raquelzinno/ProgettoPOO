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
        ArrayList<Vestito> listaItem = new ArrayList<>();

        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //EFFETTUO LA QUERY
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Vestito\"");
        ResultSet rs = ps.executeQuery();

        //ELABORO IL RESULT SET
        while (rs.next()) {
            String nome = rs.getString("nome");
            int costo = rs.getInt("costo");
            String img = rs.getString("imagePath");
            int boostFame = rs.getInt("boostFame");
            int boostEnergia = rs.getInt("boostEnergia");

            listaItem.add(new Vestito(nome, costo, null, boostEnergia, boostFame, img));
        }

        //CHIUDO CONNESSIONE
        rs.close();
        connection.close();

        return listaItem;
    }
}
