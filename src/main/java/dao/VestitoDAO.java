package dao;

import model.Item;
import model.Vestito;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

public interface VestitoDAO {
    public ArrayList<Vestito> recuperaListaVestiti() throws SQLException;
    public ArrayList<Vestito> recuperaInventarioVestiti(int idUtente) throws SQLException;
    public int aggiungiAInventarioVestito(int idUtente, Item item) throws SQLException;
    public void eliminaDaInventario(int idIstanza) throws SQLException;
    public void indossaVestito(int idIstanza,int idAnimale) throws SQLException;
    public void rimuoviVestito(int idIstanza) throws SQLException;
}
