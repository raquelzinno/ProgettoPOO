package dao;

import model.Animale;
import model.Item;
import model.Utente;

import java.sql.SQLException;

public interface UtenteDAO {
    public int recuperaId(String login) throws SQLException;
    public void salvaUtente(String login,String password) throws SQLException;
    public boolean controlloLogin(String login) throws SQLException;
    public void aggiornaPassword(String login, String passwordVecchia, String passwordNuova) throws SQLException;
    public boolean cercaUtente(String login, String password) throws SQLException;
    public boolean salvaAcquisto(int idUtente, Item item) throws SQLException;
    public boolean aggiungiAInventarioCibo(int idUtente, Item item) throws SQLException;
    public boolean aggiungiAInventarioVestito(int idUtente, Item item) throws SQLException;
}

