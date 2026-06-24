package dao;

import model.Animale;
import model.Item;
import model.Utente;

import java.sql.SQLException;

public interface UtenteDAO {
    public int recuperaId(String login) throws SQLException;
    public void salvaUtente(String login,String password) throws SQLException;
    public boolean controlloLogin(String login) throws SQLException;
    public void aggiornaPassword(int idUtente, String passwordNuova) throws SQLException;
    public boolean cercaUtente(String login, String password) throws SQLException;
}

