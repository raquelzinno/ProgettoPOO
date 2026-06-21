package dao;

import model.Utente;

import java.sql.SQLException;

public interface UtenteDAO {
    public void salvaUtente(Utente utente) throws SQLException;
    public void aggiornaPassword(String login, String passwordVecchia, String passwordNuova) throws SQLException;
    public boolean cercaUtente(String login, String password) throws SQLException;
}
