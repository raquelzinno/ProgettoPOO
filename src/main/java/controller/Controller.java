package controller;

import exceptions.ExceptionAnimale;
import exceptions.ExceptionPassword;
import exceptions.ExceptionTroppiAnimali;
import exceptions.ExceptionUtente;
import gui.Tamagotchi;
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
import javax.swing.Timer;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    private ArrayList<Utente> listaUtenti;
    private Utente utenteAttuale = null;
    private Timer gameTimer;
    private Timer sonnoTimer;
    private Tamagotchi tamagotchiFrame;
    private Negozio negozioBase;
    private ArrayList<Minigame> minigamesDiDefault;

    public Controller() {
        listaUtenti = new ArrayList<>();
        negozioBase = new Negozio(); //istanzio un oggetto negozio che avrà già tutti gli item di default
        minigamesDiDefault = Minigame.getMinigamesDiDefault(); //grazie al metodo statico, stabilisco l'arrayList che conterrà tutti i minigames di default
    }

    public ArrayList<Minigame> getMinigamesDiDefault() {
        return minigamesDiDefault;
    }

    public Negozio getNegozioBase() {
        return negozioBase;
    }

    public void aggiungiUtente(Utente utente){
        listaUtenti.add(utente);
    }

    public void creaUtente(String login, String password) throws RuntimeException{
        if(login.isBlank()) throw new ExceptionUtente("Il campo nome utente è vuoto.");
        if(password.isBlank()) throw new ExceptionPassword("Il campo password è vuoto.");

        for(Utente u : listaUtenti){ //controlla se il nome utente esiste già
            if(u.getLogin().equals(login)){
                throw new ExceptionUtente("Nome utente già esistente.");
            }
        }

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
            }
        }
        throw new ExceptionUtente("Utente non trovato.");
    }

    public void checkAnimali(Utente utente) throws RuntimeException{
        if((utente.getAnimaliPosseduti()).size() >= 2) throw new ExceptionTroppiAnimali("Hai il massimo di animali consentiti!");
    }

    public void creaAnimale(Utente utente, String tipo, String nome) throws RuntimeException{
        checkAnimali(utente); //controlli
        if(tipo.isBlank()) throw new ExceptionAnimale("Nessun tipo selezionato.");
        if(nome.isBlank()) throw new ExceptionAnimale("Nessun nome inserito.");

        if(tipo.equals("Orso")){
            Orso animale = new Orso(nome);
            aggiungiAnimale(utente, animale);
        }
        else if(tipo.equals("Pinguino")){
            Pinguino animale = new Pinguino(nome);
            aggiungiAnimale(utente, animale);
        }
    }

    public void aggiungiAnimale(Utente utente, Animale animale){
        utente.creaAnimale(animale); //crea l'animale che è legato all'utente
    }

    public void compra(Utente utente, Item item, Animale animale) {
        utente.compraItem(item, animale);
    }

    public Utente getUtenteAttuale() {
        return utenteAttuale;
    }

    public void esciUtente() {
        this.utenteAttuale.setAccessoEffettuato(false);
        //this.utenteAttuale = null;
    }

    public ArrayList<Utente> getListaUtenti(){
        return listaUtenti;
    }

    public void usaItem(Utente utente, Item item, Animale animale){
        if(item instanceof Cibo)
            utente.daiCibo((Cibo) item, animale);
        else if(item instanceof Vestito)
            utente.vesti((Vestito) item, animale);
    }

    public void rimuoviVestito(Vestito vestito, Animale animale){
        utenteAttuale.rimuoviVestito(vestito, animale);
    }

    public void setHomeFrame(Tamagotchi tamagotchiFrame) {
        this.tamagotchiFrame = tamagotchiFrame;
    }

    public void iniziaTimer(Animale animale) {
        if (gameTimer != null && gameTimer.isRunning())  //gestione di possibili timer attivi
            gameTimer.stop();
        gameTimer = new Timer(60000, e -> {  //viene istanziato il timer di gioco, ogni minuto i valori vengono consumati
            animale.consumaEnergia();
            animale.consumaFame();
            tamagotchiFrame.aggiornaLabel();
        });
        gameTimer.start();
    }

    public void fermaTimer() {
        gameTimer.stop();
    }

    public void addormenta(Animale animale) {  //istanzia il nuovo timer del sonno, l'energia aumenta ogni secondo
        animale.setDorme(true);
        sonnoTimer = new Timer(1000, e -> {
            animale.caricaEnergia();
            tamagotchiFrame.aggiornaLabel();
        });
        sonnoTimer.start();
    }

    public void sveglia(Animale animale)
    {
        animale.setDorme(false);
        if(sonnoTimer != null && sonnoTimer.isRunning()) //se il timer del sonno è attivo, questo viene fermato
        sonnoTimer.stop();
    }

    public void eliminaAnimale(Animale animale)
    {
        if (gameTimer != null && gameTimer.isRunning())  //gestione di possibili timer attivi che devono essere fermati
            gameTimer.stop();
        if(sonnoTimer != null && sonnoTimer.isRunning())
            sonnoTimer.stop();
        utenteAttuale.eliminaAnimale(animale);
    }

    public void cambiaPassword(String vecchiaPass, String nuovaPass) throws RuntimeException{
        if(nuovaPass.isBlank()) throw new ExceptionPassword("Nessuna password inserita.");
        if(utenteAttuale.getPassword().equals(vecchiaPass)){
            utenteAttuale.setPassword(nuovaPass);
        }else throw new ExceptionPassword("La password è errata.");
    }

    public String giocaSassoCartaForbici(Minigame minigame, String manoUtente, String manoAvversaria, Animale animale) {
            if(manoAvversaria.equals(manoUtente)) {  //se le mani sono uguali è il caso del pareggio, non viene cambiato nessun valore
                return "pareggiato";
            }
            else if ((manoAvversaria.equals("sasso") && manoUtente.equals("forbici")) || (manoAvversaria.equals("forbici") && manoUtente.equals("carta")) || (manoAvversaria.equals("carta") && manoUtente.equals("sasso"))) {
                animale.gioca(minigame,false); //casi di sconfitta
                return "perso";
            }
            else {
                animale.gioca(minigame,true); //i restanti sono casi di vittoria
                return "vinto";
            }
    }

    public String casualeSassoCartaForbici() { //calcolo una stringa a caso fra sasso carta e forbici
        String sasso = "sasso";
        String carta = "carta";
        String forbici = "forbici";
        String[] opzioni = {sasso,carta,forbici};

        Random random = new Random();
        int indiceCasuale = random.nextInt(opzioni.length);
        return opzioni[indiceCasuale];
    }

}
