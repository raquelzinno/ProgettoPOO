package dao;

import model.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO {
    public ArrayList<Item> recuperaListaItem() throws SQLException;
    public ArrayList<Item> recuperaItemAggiornati(String login) throws SQLException;
}
