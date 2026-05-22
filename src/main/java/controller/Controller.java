package controller;

import model.Animale;
import model.Utente;
import model.Vestito;
import model.Item;
import model.Cibo;
import model.Orso;
import model.Pinguino;
import model.TipoCibo;
import model.Minigame;
import model.Negozio;

import java.util.ArrayList;

public class Controller {
    ArrayList<Utente> listaUtenti;

    public Controller(){
        listaUtenti = new ArrayList<>();
    }

    public void aggiungiUtente(Utente utente){
        listaUtenti.add(utente);
    }

    public void creaUtente(String login, String password){
        Utente utente = new Utente(login, password);
        aggiungiUtente(utente);
    }

    public boolean checkUtente(String utente, String password){
        for(Utente u : listaUtenti){
            if(u.getLogin().equals(utente) && u.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

}
