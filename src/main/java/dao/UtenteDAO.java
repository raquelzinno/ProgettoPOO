package dao;

import model.Utente;

import java.sql.SQLException;

public interface UtenteDAO {
    public void salvaUtente(Utente utente) throws SQLException;
    public void aggiornaNomeUtente(String uservecchio, String userNuovo) throws SQLException;
    public boolean cercaUtente(String login, String password) throws SQLException;
}
