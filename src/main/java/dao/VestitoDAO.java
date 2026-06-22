package dao;

import model.Vestito;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VestitoDAO {
    public ArrayList<Vestito> recuperaListaVestiti() throws SQLException;
}
