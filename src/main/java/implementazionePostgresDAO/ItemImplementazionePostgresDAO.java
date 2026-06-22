package implementazionePostgresDAO;

import dao.CiboDAO;
import dao.ItemDAO;
import dao.VestitoDAO;
import model.Item;


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
}
