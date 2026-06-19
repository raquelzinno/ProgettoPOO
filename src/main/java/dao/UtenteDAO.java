package dao;

import model.Utente;

import java.sql.SQLException;

public interface UtenteDAO {
    public void salvaUtente(Utente utente) throws SQLException;
    public void aggiornaNomeUtente(String uservecchio, String userNuovo) throws SQLException;
}
