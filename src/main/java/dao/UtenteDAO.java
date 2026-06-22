package dao;

import model.Animale;
import model.Item;
import model.Utente;

import java.sql.SQLException;

public interface UtenteDAO {
    public void salvaUtente(Utente utente) throws SQLException;
    public void aggiornaPassword(String login, String passwordVecchia, String passwordNuova) throws SQLException;
    public boolean cercaUtente(String login, String password) throws SQLException;
    public boolean salvaAcquisto(String login, Item item) throws SQLException;
    public boolean aggiungiAInventarioCibo(String login, Item item) throws SQLException;
    public boolean aggiungiAInventarioVestito(String login, Item item) throws SQLException;
}

