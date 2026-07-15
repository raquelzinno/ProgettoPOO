package dao;

import java.sql.SQLException;

public interface UtenteDAO {
    /**
     * Recupera l'id dell'utente identificandolo attraverso il suo login.
     *
     * @param login login dell'utente
     * @return l'id dell'utente
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public int recuperaId(String login) throws SQLException;

    /**
     * Inserisce il nuovo utente nel database.
     *
     * @param login    login dell'utente
     * @param password password dell'utente
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void salvaUtente(String login, String password) throws SQLException;

    /**
     * Controlla se il login dell'utente che si vuole creare è gia presente nel database.
     *
     * @param login login dell'utente
     * @return {@code true} se è già presente, {@code false} altrimenti
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public boolean controlloLogin(String login) throws SQLException;

    /**
     * Aggiorna la password dell'utente nel database identificandolo attraverso il login.
     *
     * @param idUtente      id dell'utente
     * @param passwordNuova password nuova
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void aggiornaPassword(int idUtente, String passwordNuova) throws SQLException;

    /**
     * Cerca l'utente nel database per poter effettuare l'accesso, identificandolo attraverso il login.
     *
     * @param login    login
     * @param password password
     * @return {@code true} se l'utente è presente nel database, {@code false} altrimenti
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public boolean cercaUtente(String login, String password) throws SQLException;
}

