package dao;

import model.Cibo;
import model.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CiboDAO {
    public ArrayList<Cibo> recuperaListaCibo() throws SQLException;
    public ArrayList<Cibo> recuperaInventarioCibo(int idUtente) throws SQLException;
    public int aggiungiAInventarioCibo(int idUtente, Item item) throws SQLException;
    public void eliminaDaInventario(int idIstanza) throws SQLException;
}
