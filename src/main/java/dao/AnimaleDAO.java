package dao;

import model.Animale;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AnimaleDAO {
    /**
     * Recupera l'id dell'animale dal database.
     *
     * @param idUtente    id dell'utente a cui appartiene l'animale
     * @param nomeAnimale nome dell'animale
     * @return il valore dell'id dell'animale
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public int recuperaId(int idUtente, String nomeAnimale) throws SQLException;

    /**
     * Inserisce il nuovo animale nel database.
     *
     * @param animale  l'oggetto {@link Animale} da salvare
     * @param idUtente l'id dell'utente a cui appartiene l'animale
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void salvaAnimale(Animale animale, int idUtente) throws SQLException;

    /**
     * Recupera la lista degli animali posseduti da un utente dal database
     * utilizzando l'id utente come identificativo.
     *
     * @param idUtente id utente
     * @return lista degli animali dell'utente
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public ArrayList<Animale> recuperaListaAnimali(int idUtente) throws SQLException;

    /**
     * Aggiorna il nome di un animale nel database.
     *
     * @param idAnimale id dell'animale
     * @param nome      il nome da impostare
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void modificaNome(int idAnimale, String nome) throws SQLException;

    /**
     * Elimina l'animale dal database.
     *
     * @param idAnimale id dell'animale
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void eliminaAnimale(int idAnimale) throws SQLException;

    /**
     * Aggiorna lo stato e i parametri dell'animale nel databsase in seguito a
     * delle modifiche per evitare che i cambiamenti vengano persi.
     *
     * @param animale   l'oggetto {@link Animale} di cui bisogna aggiornare i parametri
     * @param idAnimale id dell'animale
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void aggiornaStatoAnimale(Animale animale, int idAnimale) throws SQLException;

    /**
     * Resetta lo stato del sonno a tutti gli animali dell'utente.
     *
     * @param idUtente id dell'utente
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void resetStatoSonno(int idUtente) throws SQLException;
}
