package dao;

import model.Animale;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AnimaleDAO {
    public void salvaAnimale(Animale animale, String loginUtente, ArrayList<Animale> listaAnimali) throws SQLException;
    public ArrayList<Animale> recuperaListaAnimali(String login) throws SQLException;
    public void modificaNome(String login, Animale animale, String nome) throws SQLException;
    public void eliminaAnimale(Animale animale, String loginUtente) throws SQLException;
    public void aggiornaStatoAnimale(Animale animale, String loginUtente) throws SQLException;

}
