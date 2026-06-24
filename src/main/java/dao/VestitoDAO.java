package dao;

import model.Item;
import model.Vestito;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

public interface VestitoDAO {
    public ArrayList<Vestito> recuperaListaVestiti() throws SQLException;
    public ArrayList<Vestito> recuperaInventarioVestiti(int idUtente) throws SQLException;
    public boolean aggiungiAInventarioVestito(int idUtente, Item item) throws SQLException;
}
