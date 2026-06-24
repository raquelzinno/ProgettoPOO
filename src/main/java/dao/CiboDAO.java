package dao;

import model.Cibo;
import model.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CiboDAO {
    public ArrayList<Cibo> recuperaListaCibo() throws SQLException;
    public ArrayList<Cibo> recuperaInventarioCibo(int idUtente) throws SQLException;
    public boolean aggiungiAInventarioCibo(int idUtente, Item item) throws SQLException;
}
