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
        ArrayList<Cibo> listaItem = new ArrayList<>();

        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        //EFFETTUO LA QUERY
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Cibo\"");
        ResultSet rs = ps.executeQuery();

        //ELABORO IL RESULT SET
        while (rs.next()) {
            String nome = rs.getString("nome");
            int prezzo = rs.getInt("costo");
            String img = rs.getString("imagePath");
            String tipo = rs.getString("tipo");
            int punti = rs.getInt("puntiFame");

            if(tipo.equals("salato"))
                listaItem.add(new Cibo(nome, prezzo, null, TipoCibo.salato, punti, img));
            else if(tipo.equals("dolce"))
                listaItem.add(new Cibo(nome, prezzo, null, TipoCibo.dolce, punti, img));
            else if(tipo.equals("bevanda"))
                listaItem.add(new Cibo(nome, prezzo, null, TipoCibo.bevanda, punti, img));
        }

        //CHIUDO CONNESSIONE
        rs.close();
        connection.close();

        return listaItem;
    }
}
