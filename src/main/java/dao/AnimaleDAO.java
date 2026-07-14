package dao;

import model.Animale;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AnimaleDAO {
    public int recuperaId(int idUtente,String nomeAnimale) throws SQLException;
    public void salvaAnimale(Animale animale, int idUtente) throws SQLException;
    public ArrayList<Animale> recuperaListaAnimali(int idUtente) throws SQLException;
    public void modificaNome(int idAnimale, String nome) throws SQLException;
    public void eliminaAnimale(int idAnimale) throws SQLException;
    public void aggiornaStatoAnimale(Animale animale, int idAnimale) throws SQLException;
    public void resetStatoSonno(int idUtente) throws SQLException;
}
