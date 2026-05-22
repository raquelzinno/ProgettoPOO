package controller;

import exceptions.ExceptionPassword;
import exceptions.ExceptionTroppiAnimali;
import exceptions.ExceptionUtente;
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
    private Utente utenteAttuale = null;

    public Controller(){
        listaUtenti = new ArrayList<>();
    }

    public void aggiungiUtente(Utente utente){
        listaUtenti.add(utente);
    }

    public void creaUtente(String login, String password) throws RuntimeException{
        if(login.isBlank()) throw new ExceptionUtente("Il campo nome utente è vuoto.");
        if(password.isBlank()) throw new ExceptionPassword("Il campo password è vuoto.");
        Utente utente = new Utente(login, password);
        aggiungiUtente(utente);
    }

    public boolean checkUtente(String utente, String password){
        if(utente.isBlank()) throw new ExceptionUtente("Il campo nome utente è vuoto.");
        if(password.isBlank()) throw new ExceptionPassword("Il campo password è vuoto.");
        for(Utente u : listaUtenti){
            if(u.getLogin().equals(utente) && u.getPassword().equals(password)){
                u.setAccessoEffettuato(true);
                utenteAttuale = u;
                return true;
            } else {
                throw new ExceptionUtente("Utente non trovato.");
            }
        }
        return false;
    }

    public void creaAnimale(Utente utente, String tipo, String nome) throws RuntimeException{
        if((utente.getAnimaliPosseduti()).size() > 2) throw new ExceptionTroppiAnimali("Hai il massimo di animali consentiti!");
        if(tipo.equals("Orso")){
            Orso animale = new Orso(nome,10,20,0, false);
            aggiungiAnimale(utente, animale);
        }
        else if(tipo.equals("Pinguino")){
            Pinguino animale = new Pinguino(nome, 10, 30, 1, false);
            aggiungiAnimale(utente, animale);
        }
        System.out.println("Animale creato con successo");
    }

    public void aggiungiAnimale(Utente utente, Animale animale){
        utente.creaAnimale(animale); //crea l'animale che è legato all'utente
    }

    public Utente getUtenteAttuale() {
        return utenteAttuale;
    }

    public ArrayList<Utente> getListaUtenti(){
        return listaUtenti;
    }

    /*public ArrayList<Animale> getListaAnimali(){
        return utente.
    }*/

}
