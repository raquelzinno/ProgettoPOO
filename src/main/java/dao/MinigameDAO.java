package dao;

import model.Minigame;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Minigame dao.
 */
public interface MinigameDAO {
    /**
     * Recupera la lista dei minigame presenti nel database.
     *
     * @return un {@link ArrayList} contenente i minigames di default
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public ArrayList<Minigame> recuperaMinigame() throws SQLException;
}
