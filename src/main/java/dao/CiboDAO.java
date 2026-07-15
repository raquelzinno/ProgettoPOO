package dao;

import model.Cibo;
import model.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CiboDAO {
    /**
     * Recupera l'elenco di tutti i cibi presenti nel database.
     *
     * @return un {@link ArrayList} contenente i cibi presenti nel negozio
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public ArrayList<Cibo> recuperaListaCibo() throws SQLException;

    /**
     * Recupera la lista dei cibi presenti nell'inventario dell'utente.
     *
     * @param idUtente id dell'utente
     * @return un {@link ArrayList} contenente i cibi presenti nell'inventario dell'utente
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public ArrayList<Cibo> recuperaInventarioCibo(int idUtente) throws SQLException;

    /**
     * Recupera l'id istanza del cibo comprato dall'utente, per
     * aggiungerlo al suo inventario.
     *
     * @param idUtente id dell'utente
     * @param item     l' {@link Item} da inserire nell'inventario, in questo caso un cibo
     * @return l'id istanza del cibo
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public int aggiungiAInventarioCibo(int idUtente, Item item) throws SQLException;

    /**
     * Elimina il cibo selezionato dall'inventario dell'utente, identificandolo
     * tramite il suo id istanza.
     *
     * @param idIstanza l'id istanza del cibo
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void eliminaDaInventario(int idIstanza) throws SQLException;
}
