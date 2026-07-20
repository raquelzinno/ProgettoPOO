package implementazionePostgresDAO;

import dao.MinigameDAO;
import database.ConnessioneDatabase;
import model.Minigame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MinigameImplementazionePostgresDAO implements MinigameDAO {
    @Override
    public ArrayList<Minigame> recuperaMinigame() throws SQLException {
        //INSTAURO LA CONNESSIONE
        Connection connection = ConnessioneDatabase.getInstance().connection;

        ArrayList<Minigame> listaMinigames = new ArrayList<>();

        String sql = "SELECT * FROM \"Minigame\"";
        //EFFETTUO LA QUERY
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try(ResultSet rs = ps.executeQuery()) {

                //ELABORO IL RESULT SET
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    int ricompensa = rs.getInt("ricompensa");
                    int energiaConsumata = rs.getInt("energiaConsumata");

                    listaMinigames.add(new Minigame(nome, ricompensa, energiaConsumata));
                }
            }
        }
        return listaMinigames;
    }
}
