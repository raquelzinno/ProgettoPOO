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
                u.setAccessoEffettuato(true);
                return true;
            }
        }
        return false;
    }

    public void creaAnimale(Utente utente, String tipo, String nome){
        if(tipo.equals("Orso")){
            Orso animale = new Orso(nome,0,0,0, false);
            aggiungiAnimale(utente, animale);
        }
        else if(tipo.equals("Pinguino")){
            Pinguino animale = new Pinguino(nome, 0, 0, 0, false);
            aggiungiAnimale(utente, animale);
        }
        System.out.println("Animale creato con successo");
    }

    public void aggiungiAnimale(Utente utente, Animale animale){
        utente.creaAnimale(animale); //crea l'animale che è legato all'utente
    }

    public ArrayList<Utente> getListaUtenti(){
        return listaUtenti;
    }

}
