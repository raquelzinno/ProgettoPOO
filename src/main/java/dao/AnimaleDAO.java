package dao;

import model.Animale;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AnimaleDAO {
    public boolean salvaAnimale(Animale animale, int idUtente) throws SQLException;
    public ArrayList<Animale> recuperaListaAnimali(int idUtente) throws SQLException;
    public void modificaNome(int idUtente, Animale animale, String nome) throws SQLException;
    public void eliminaAnimale(Animale animale, int idUtente) throws SQLException;
    public void aggiornaStatoAnimale(Animale animale, int idUtente) throws SQLException;

}
