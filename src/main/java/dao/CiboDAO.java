package dao;

import model.Cibo;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CiboDAO {
    public ArrayList<Cibo> recuperaListaCibo() throws SQLException;
}
