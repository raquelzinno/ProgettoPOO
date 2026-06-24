package dao;

import model.Minigame;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MinigameDAO {
    public ArrayList<Minigame> recuperaMinigame() throws SQLException;
}
