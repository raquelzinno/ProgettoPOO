package controller;

import dao.AnimaleDAO;
import dao.ItemDAO;
import dao.UtenteDAO;
import exceptions.*;
import gui.Tamagotchi;
import implementazionePostgresDAO.AnimaleImplementazionePostgresDAO;
import implementazionePostgresDAO.ItemImplementazionePostgresDAO;
import implementazionePostgresDAO.UtenteImplementazionePostgresDAO;
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

import java.sql.SQLException;
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

    public Controller() throws SQLException {
        listaUtenti = new ArrayList<Utente>();
        negozioBase = new Negozio(popolaNegozio()); //istanzio un oggetto negozio che avrà già tutti gli item di default
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

        if(login.length() > 15) throw new ExceptionUtente("Il nome utente deve essere di massimo 15 caratteri.");
        if(password.length() > 15) throw new ExceptionPassword("La password deve essere di massimo 15 caratteri.");

        for(Utente u : listaUtenti){ //controlla se il nome utente esiste già
            if(u.getLogin().equals(login)){
                throw new ExceptionUtente("Nome utente già esistente.");
            }
        }

        Utente utente = new Utente(login, password);
        aggiungiUtente(utente);

        //DA RIVEDERE O OTTIMIZZARE
        try {
            UtenteDAO utenteDAO = new UtenteImplementazionePostgresDAO();
            utenteDAO.salvaUtente(utente);
        }
        catch (SQLException e){
            System.err.println("Errore durante l'inserimento dell'utente nel database!");
            e.printStackTrace();
        }
    }

    public boolean checkUtente(String utente, String password){
        if(utente.isBlank()) throw new ExceptionUtente("Il campo nome utente è vuoto.");
        if(password.isBlank()) throw new ExceptionUtente("Il campo nome utente è vuoto.");

        try {
            UtenteDAO utenteDAO = new UtenteImplementazionePostgresDAO();
            if(utenteDAO.cercaUtente(utente, password)) {
                Utente u = new Utente(utente, password);
                utenteAttuale = u;
                utenteAttuale.setAccessoEffettuato(true);
                return true;
            }
        }
        catch (SQLException e){
            System.err.println("Errore durante la ricerca dell'utente nel database!");
            e.printStackTrace();
        }

        //DA RIMUOVERE, USA ARRAYLIST DI CONTROLLER E NON IL DB
        /*for(Utente u : listaUtenti){
            if(u.getLogin().equals(utente) && u.getPassword().equals(password)){
                u.setAccessoEffettuato(true);
                utenteAttuale = u;
                return true;
            }
        }*/
        throw new ExceptionUtente("Utente non trovato.");
    }

    public void checkAnimali() throws RuntimeException{
        // 1. Forza il controller a leggere gli animali reali dal DB PostgreSQL
        this.sincronizzaListaAnimali();

        // 2. Stampa di debug in console per vedere quanti animali rileva IntelliJ in questo momento
        System.out.println("Animali rilevati nel DB per il controllo: " + utenteAttuale.getAnimaliPosseduti().size());
        if((utenteAttuale.getAnimaliPosseduti()).size() >= 2) throw new ExceptionTroppiAnimali("Hai il massimo di animali consentiti!");
    }

    public void creaAnimale(String tipo, String nome) throws RuntimeException{
        checkAnimali(); //controlli
        if(tipo.isBlank()) throw new ExceptionAnimale("Nessun tipo selezionato.");
        if(nome.isBlank()) throw new ExceptionAnimale("Nessun nome inserito.");

        for(Animale a : utenteAttuale.getAnimaliPosseduti()){ //controlla se il nome dell'animale esiste già
            if(a.getNome().equals(nome)){
                throw new ExceptionAnimale("Nome animale già esistente.");
            }
        }

        if(tipo.equals("Orso")){
            Orso animale = new Orso(nome);
            aggiungiAnimale(animale);
        }
        else if(tipo.equals("Pinguino")){
            Pinguino animale = new Pinguino(nome);
            aggiungiAnimale(animale);
        }

        try {
            AnimaleDAO animaleDAO = new AnimaleImplementazionePostgresDAO();
            if(tipo.equals("Orso")){
                Orso animale = new Orso(nome);
                animaleDAO.salvaAnimale(animale, utenteAttuale.getLogin(), utenteAttuale.getAnimaliPosseduti());
                //aggiungiAnimale(animale);
            }
            else if(tipo.equals("Pinguino")){
                Pinguino animale = new Pinguino(nome);
                animaleDAO.salvaAnimale(animale, utenteAttuale.getLogin(), utenteAttuale.getAnimaliPosseduti());
                //aggiungiAnimale(animale);
            }
        }
        catch (SQLException e){
            System.err.println("Errore durante l'inserimento dell'animale nel database!");
            e.printStackTrace();
        }
    }

    public void sincronizzaListaAnimali() {
        try {
            String usernameUtente = this.getUtenteAttuale().getLogin();

            AnimaleDAO animaleDAO = new AnimaleImplementazionePostgresDAO();
            ArrayList<Animale> animaliDalDb = animaleDAO.recuperaListaAnimali(usernameUtente);

            this.getUtenteAttuale().setAnimaliPosseduti(animaliDalDb);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvaStatoAnimale(Animale animale) {
        try {
            AnimaleDAO animaleDAO = new AnimaleImplementazionePostgresDAO();
            animaleDAO.aggiornaStatoAnimale(animale, getUtenteAttuale().getLogin());

            sincronizzaListaAnimali(); //sincronizzo la memoria locale con il DB

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void modificaNomeAnimale(String nome, Animale animale) throws SQLException {
        AnimaleDAO animaleDAO = new AnimaleImplementazionePostgresDAO();
        animaleDAO.modificaNome(utenteAttuale.getLogin(), animale, nome); //lo modifico prima nel database
        animale.setNome(nome);
    }

    public void aggiungiAnimale(Animale animale){
        utenteAttuale.creaAnimale(animale); //crea l'animale che è legato all'utente
    }

    public ArrayList<Item> popolaNegozio() throws SQLException {
        ItemDAO itemDAO = new ItemImplementazionePostgresDAO();

        ArrayList<Item> listaItemDB = itemDAO.recuperaListaItem();

        return listaItemDB;
    }

    public void caricaInventarioUtente() throws SQLException {
        ItemDAO itemDAO = new ItemImplementazionePostgresDAO();
        if (utenteAttuale!= null) {
            //prende i dati aggiornati dal database
            ArrayList<Item> itemDalDB = itemDAO.recuperaItemAggiornati(utenteAttuale.getLogin());

            //sostituisce la vecchia lista locale con quella reale del DB
            utenteAttuale.setItemPosseduti(itemDalDB);

            System.out.println("Inventario sincronizzato! Elementi trovati: " + itemDalDB.size());
        }
    }

    public void compra(Item item, Animale animale) throws RuntimeException, SQLException{
        UtenteDAO utenteDAO = new UtenteImplementazionePostgresDAO();

        boolean salvataggioRiuscito = utenteDAO.salvaAcquisto(utenteAttuale.getLogin(), item);

        if (salvataggioRiuscito) {
            //se il DB si aggiorna, aggiorna anche la lista locale in Java
            utenteAttuale.compraItem(item, animale);
            System.out.println("Acquisto completato con successo sul DB e in Java!");
        } else {
            System.out.println("Errore durante il salvataggio dell'acquisto nel database.");
        }




    }

    public Utente getUtenteAttuale() {
        return utenteAttuale;
    }

    public void esciUtente() {
        this.utenteAttuale.setAccessoEffettuato(false);
        this.utenteAttuale = null;
    }

    public ArrayList<Utente> getListaUtenti(){
        return listaUtenti;
    }

    public void usaItem(Item item, Animale animale) throws RuntimeException{
        if(item instanceof Cibo)
            utenteAttuale.daiCibo((Cibo) item, animale);
        else if(item instanceof Vestito)
            utenteAttuale.vesti((Vestito) item, animale);
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

    public void sveglia(Animale animale) {
        animale.setDorme(false);
        if(sonnoTimer != null && sonnoTimer.isRunning()) //se il timer del sonno è attivo, questo viene fermato
            sonnoTimer.stop();
    }

    public void eliminaAnimale(Animale animale) throws SQLException{
        if (gameTimer != null && gameTimer.isRunning())  //gestione di possibili timer attivi che devono essere fermati
            gameTimer.stop();
        if(sonnoTimer != null && sonnoTimer.isRunning())
            sonnoTimer.stop();

        AnimaleDAO animaleDAO = new AnimaleImplementazionePostgresDAO();
        animaleDAO.eliminaAnimale(animale, utenteAttuale.getLogin());
        utenteAttuale.eliminaAnimale(animale);
    }

    public void cambiaPassword(String vecchiaPass, String nuovaPass) throws RuntimeException, SQLException {
        if(nuovaPass.isBlank()) throw new ExceptionPassword("Nessuna password inserita.");
        if(utenteAttuale.getPassword().equals(vecchiaPass)){
            utenteAttuale.setPassword(nuovaPass);
            UtenteDAO utenteDAO = new UtenteImplementazionePostgresDAO();
            utenteDAO.aggiornaPassword(utenteAttuale.getLogin(), vecchiaPass,nuovaPass);
        }else throw new ExceptionPassword("La password è errata.");
    }

    public String giocaSassoCartaForbici(Minigame minigame, String manoUtente, String manoAvversaria, Animale animale) throws RuntimeException {
        if(manoUtente.isBlank()) throw new ExceptionMinigame("Nessuna opzione selezionata.");
        if(animale.getEnergia() < minigame.getEnergiaConsumata()) throw new ExceptionMinigame("Energia non sufficiente.");

        if(manoAvversaria.equals(manoUtente)) { //casi di pareggio
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

    public String casualeLancioMoneta() {
        String testa = "testa";
        String croce = "croce";
        String[] opzioni = {testa,croce};

        Random random = new Random();
        int indiceCasuale = random.nextInt(opzioni.length);
        return opzioni[indiceCasuale];
    }

    public String giocaLancioMoneta(Minigame minigame, String inputUtente, String risultatoLancio, Animale animale) throws RuntimeException{
        if(inputUtente.isBlank()) throw new ExceptionMinigame("Nessuna opzione selezionata.");
        if(animale.getEnergia() < minigame.getEnergiaConsumata()) throw new ExceptionMinigame("Energia non sufficiente.");

        if(inputUtente.equals(risultatoLancio)) {
            animale.gioca(minigame,true);
            return "vinto";
        }
        else {
            animale.gioca(minigame,false);
            return "perso";
        }
    }

    public int casualeSlotMachine() {
        Random random = new Random();
        return random.nextInt(4);
    }

    public String giocaSlotMachine(Minigame minigame, Animale animale, int slot1, int slot2, int slot3) throws RuntimeException {
        if(animale.getEnergia() < minigame.getEnergiaConsumata()) throw new ExceptionMinigame("Energia non sufficiente.");

        if(slot1 == slot2 && slot2 == slot3) {
            animale.gioca(minigame,true);
            return "vinto";
        }
        else {
            animale.gioca(minigame,false);
            return "perso";
        }
    }

    public void elimina(Item item) {
        utenteAttuale.eliminaItem(item);
    }

}
