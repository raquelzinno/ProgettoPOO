package dao;

import model.Item;
import model.Vestito;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VestitoDAO {
    /**
     * Recupera l'elenco di tutti i vestiti presenti nel database.
     *
     * @return un {@link ArrayList} contenente i vestiti presenti nel negozio
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public ArrayList<Vestito> recuperaListaVestiti() throws SQLException;

    /**
     * Recupera la lista dei vestiti presenti nell'inventario dell'utente.
     *
     * @param idUtente id dell'utente
     * @return un {@link ArrayList} contenente i vestiti presenti nell'inventario dell'utente
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public ArrayList<Vestito> recuperaInventarioVestiti(int idUtente) throws SQLException;

    /**
     * Recupera l'id istanza del cibo comprato dall'utente, per
     * aggiungerlo al suo inventario.
     *
     * @param idUtente id dell'utente
     * @param item     l' {@link Item} da inserire nell'inventario, in questo caso un vestito
     * @return l'id istanza del vestito
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public int aggiungiAInventarioVestito(int idUtente, Item item) throws SQLException;

    /**
     * Elimina il vestito selezionato dall'inventario dell'utente, identificandolo
     * tramite il suo id istanza.
     *
     * @param idIstanza l'id istanza del vestito
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void eliminaDaInventario(int idIstanza) throws SQLException;

    /**
     * Indossa il vestito presente nell'inventario, assegnandogli l'id dell'animale
     * che lo sta indossando.
     *
     * @param idIstanza l'id istanza del vestito
     * @param idAnimale l'id dell'animale che deve indossare il vestito
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void indossaVestito(int idIstanza,int idAnimale) throws SQLException;

    /**
     * Rimuove il vestito dall'animale, rimuovendogli l'id dell'animale
     * che lo stava indossando.
     *
     * @param idIstanza l'id istanza del vestito
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void rimuoviVestito(int idIstanza) throws SQLException;
}
