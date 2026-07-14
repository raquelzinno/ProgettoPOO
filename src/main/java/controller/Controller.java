package controller;

import dao.*;
import exceptions.*;
import gui.Tamagotchi;
import implementazionePostgresDAO.*;
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
    private Utente utenteAttuale = null;
    private int idUtenteAttuale = -1;
    private int idAnimaleAttuale = -1;
    private Timer gameTimer;
    private Timer sonnoTimer;
    private Tamagotchi tamagotchiFrame;
    private Negozio negozioBase;
    private ArrayList<Minigame> minigamesDiDefault;
    private UtenteDAO utenteDAO;
    private CiboDAO ciboDAO;
    private VestitoDAO vestitoDAO;
    private AnimaleDAO animaleDAO;
    private MinigameDAO minigameDAO;

    public Controller() {
        negozioBase = null;
        minigamesDiDefault = new ArrayList<Minigame>();
        utenteDAO = new UtenteImplementazionePostgresDAO();
        ciboDAO = new CiboImplementazionePostgresDAO();
        vestitoDAO = new VestitoImplementazionePostgresDAO();
        animaleDAO = new AnimaleImplementazionePostgresDAO();
        minigameDAO = new MinigameImplementazionePostgresDAO();
    }

    public void inizializzaDati() throws SQLException{
        negozioBase = new Negozio(popolaNegozio()); //istanzio un oggetto negozio che avrà già tutti gli item di default
        minigamesDiDefault = popolaMinigames();
    }

    public ArrayList<Minigame> getMinigamesDiDefault() {
        return minigamesDiDefault;
    }

    public Negozio getNegozioBase() {
        return negozioBase;
    }

    public void creaUtente(String login, String password) throws RuntimeException, SQLException{
        if(login.isBlank()) throw new ExceptionUtente("Il campo nome utente è vuoto.");
        if(password.isBlank()) throw new ExceptionPassword("Il campo password è vuoto.");

        if(login.length() > 15) throw new ExceptionUtente("Il nome utente deve essere di massimo 15 caratteri.");
        if(password.length() > 15) throw new ExceptionPassword("La password deve essere di massimo 15 caratteri.");

        if(utenteDAO.controlloLogin(login)) throw new ExceptionUtente("Nome utente già esistente.");

        utenteDAO.salvaUtente(login, password);
    }

    public boolean checkUtente(String login, String password) throws RuntimeException, SQLException{
        if(login.isBlank()) throw new ExceptionUtente("Il campo nome utente è vuoto.");
        if(password.isBlank()) throw new ExceptionUtente("Il campo password utente è vuoto.");

        if(utenteDAO.cercaUtente(login, password)) {  //controlla se l'utente esiste nel database
            Utente u = new Utente(login, password);
            utenteAttuale = u;
            utenteAttuale.setAccessoEffettuato(true);
            idUtenteAttuale = utenteDAO.recuperaId(login);
            return true;
        }

        throw new ExceptionUtente("Utente non trovato."); //se si arriva a questo punto, l'utente non è stato trovato
    }

    public void checkAnimali() throws RuntimeException, SQLException {
        //Forza il controller a leggere gli animali dal db
        this.sincronizzaListaAnimali();

        //Stampa di debug in console per vedere quanti animali rileva IntelliJ in questo momento
        System.out.println("Animali rilevati nel DB per il controllo: " + utenteAttuale.getAnimaliPosseduti().size());
        if((utenteAttuale.getAnimaliPosseduti()).size() >= 2) throw new ExceptionTroppiAnimali("Hai il massimo di animali consentiti!");
    }

    public void creaAnimale(String tipo, String nome) throws RuntimeException, SQLException {
        checkAnimali(); //controlli

        if(tipo.isBlank()) throw new ExceptionAnimale("Nessun tipo selezionato.");
        if(nome.isBlank()) throw new ExceptionAnimale("Nessun nome inserito.");

        if(nome.length() > 15) throw new ExceptionUtente("Il nome dell'animale deve essere di massimo 15 caratteri.");

        for(Animale a : utenteAttuale.getAnimaliPosseduti()){
            if(a.getNome().equals(nome)){
                throw new ExceptionAnimale("Nome animale già esistente.");
            }
        }

        Animale animale = null;
        if("Orso".equals(tipo)){
            animale = new Orso(nome);
        }
        else if("Pinguino".equals(tipo)){
            animale = new Pinguino(nome);
        }

        animaleDAO.salvaAnimale(animale, idUtenteAttuale);
        aggiungiAnimale(animale);
        System.out.println("Animale creato con successo!");
    }

    public void sincronizzaListaAnimali() throws SQLException {
        ArrayList<Animale> animaliDalDb = animaleDAO.recuperaListaAnimali(idUtenteAttuale);
        utenteAttuale.setAnimaliPosseduti(animaliDalDb);
    }

    public void selezionaAnimale(Animale animaleAttuale) throws SQLException {
            idAnimaleAttuale = animaleDAO.recuperaId(idUtenteAttuale, animaleAttuale.getNome());
    }

    public void deselezionaAnimale() {
        idAnimaleAttuale = -1;
    }

    public void salvaStatoAnimale(Animale animale) throws SQLException {
        animaleDAO.aggiornaStatoAnimale(animale, idAnimaleAttuale);
        sincronizzaListaAnimali(); //sincronizzo la memoria locale con il DB

    }

    public void puliziaDati() throws SQLException{
        animaleDAO.resetStatoSonno(idUtenteAttuale);
    }

    public void modificaNomeAnimale(String nome, Animale animale) throws RuntimeException, SQLException {
        if(nome.isBlank()) throw new ExceptionPassword("Il campo nome è vuoto.");
        if(nome.length() > 15) throw new ExceptionUtente("Il nome dell'animale deve essere di massimo 15 caratteri.");

        for(Animale a : utenteAttuale.getAnimaliPosseduti()){
            if(a.getNome().equals(nome)){
                throw new ExceptionAnimale("Nome animale già esistente.");
            }
        }
        animaleDAO.modificaNome(idAnimaleAttuale, nome); //lo modifico prima nel database
        animale.setNome(nome);
    }

    public void aggiungiAnimale(Animale animale){
        utenteAttuale.creaAnimale(animale); //crea l'animale che è legato all'utente
    }

    public ArrayList<Item> popolaNegozio() throws SQLException {
        ArrayList<Item> listaCompleta = new ArrayList<>();

        //recupera i cibi e li aggiunge alla lista completa
        listaCompleta.addAll(ciboDAO.recuperaListaCibo());

        //recupera i vestiti e li aggiunge alla lista completa
        listaCompleta.addAll(vestitoDAO.recuperaListaVestiti());

        return listaCompleta;
    }

    public ArrayList<Minigame> popolaMinigames() throws SQLException {
        return minigameDAO.recuperaMinigame();
    }

    public void caricaInventarioUtente() throws SQLException {
        if (utenteAttuale!= null) {
            ArrayList<Item> inventarioCompleto = new ArrayList<>();

            //recupera i cibi e i vestiti e li aggiunge alla lista completa
            inventarioCompleto.addAll(ciboDAO.recuperaInventarioCibo(idUtenteAttuale));
            inventarioCompleto.addAll(vestitoDAO.recuperaInventarioVestiti(idUtenteAttuale));

            //sostituisce la vecchia lista locale con quella reale del DB
            utenteAttuale.setItemPosseduti(inventarioCompleto);

            System.out.println("Inventario sincronizzato! Elementi trovati: " + inventarioCompleto.size());
        }
    }

    public void caricaVestitiIndossati() throws SQLException {
        ArrayList<Animale> listaAnimali = utenteAttuale.getAnimaliPosseduti();
        ArrayList<Item> inventario = utenteAttuale.getItemPosseduti();

        for(Animale a : listaAnimali) {
            ArrayList<Vestito> vestitiIndossati = a.getVestitiIndossati();
            int idAnimaleCorrente = animaleDAO.recuperaId(idUtenteAttuale, a.getNome());

            for(Item i : inventario) {
                if(i instanceof Vestito) {
                    Vestito v = (Vestito) i;
                    if (v.getIdAnimale() == idAnimaleCorrente) {
                        vestitiIndossati.add(v);
                    }
                }
            }
        }
    }


    public void compra(Item item, Animale animale) throws RuntimeException, SQLException{
        int idIstanza = -1;
        if (item instanceof Cibo) {
                idIstanza = ciboDAO.aggiungiAInventarioCibo(idUtenteAttuale, item);
            }
        else if (item instanceof Vestito) {
                idIstanza = vestitoDAO.aggiungiAInventarioVestito(idUtenteAttuale, item);
        }
            utenteAttuale.compraItem(item, animale, idIstanza);
            System.out.println("Acquisto completato con successo sul DB e in Java!");
    }

    public Utente getUtenteAttuale() {
        return utenteAttuale;
    }

    public void esciUtente() {
        if(utenteAttuale!=null) {
            this.utenteAttuale.setAccessoEffettuato(false);
            this.utenteAttuale = null;
        }
        idUtenteAttuale = -1;
    }

    public void usaItem(Item item, Animale animale) throws RuntimeException, SQLException{
        if(item instanceof Cibo) {
            ciboDAO.eliminaDaInventario(item.getIdIstanza());
            utenteAttuale.daiCibo((Cibo) item, animale);
        } else if(item instanceof Vestito)
            if(utenteAttuale.vesti((Vestito) item, animale)) {
                vestitoDAO.indossaVestito(item.getIdIstanza(), idAnimaleAttuale);
                ((Vestito) item).setIdAnimale(idAnimaleAttuale);
            }
        salvaStatoAnimale(animale);
    }

    public void rimuoviVestito(Vestito vestito, Animale animale) throws SQLException{
        if(utenteAttuale.rimuoviVestito(vestito, animale)) {
            vestitoDAO.rimuoviVestito(vestito.getIdIstanza());
            vestito.setIdAnimale(-1);
            salvaStatoAnimale(animale);
        }
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

    public void addormenta(Animale animale) throws SQLException {  //istanzia il nuovo timer del sonno, l'energia aumenta ogni secondo
        animale.setDorme(true);
        salvaStatoAnimale(animale);
        sonnoTimer = new Timer(1000, e -> {
            animale.caricaEnergia();
            tamagotchiFrame.aggiornaLabel();
        });
        sonnoTimer.start();
    }

    public void sveglia(Animale animale) throws SQLException {
        animale.setDorme(false);
        if(sonnoTimer != null && sonnoTimer.isRunning()) //se il timer del sonno è attivo, questo viene fermato
            sonnoTimer.stop();
        salvaStatoAnimale(animale);
    }

    public void eliminaAnimale(Animale animale) throws SQLException{
        if (gameTimer != null && gameTimer.isRunning())  //gestione di possibili timer attivi che devono essere fermati
            gameTimer.stop();
        if(sonnoTimer != null && sonnoTimer.isRunning())
            sonnoTimer.stop();

        ArrayList<Vestito> vestitiIndossati = animale.getVestitiIndossati();
        for (Vestito v : vestitiIndossati) {
            v.setIndossato(false);
        }

        animaleDAO.eliminaAnimale(idAnimaleAttuale);
        this.deselezionaAnimale();
        utenteAttuale.eliminaAnimale(animale);
    }

    public void cambiaPassword(String vecchiaPass, String nuovaPass) throws RuntimeException, SQLException {
        if(nuovaPass.isBlank()) throw new ExceptionPassword("Nessuna password inserita.");
        if(nuovaPass.length() > 15) throw new ExceptionPassword("La password deve essere di massimo 15 caratteri.");
        if(utenteAttuale.getPassword().equals(vecchiaPass)){
            utenteDAO.aggiornaPassword(idUtenteAttuale, nuovaPass);
            utenteAttuale.setPassword(nuovaPass);
        } else throw new ExceptionPassword("La password è errata.");
    }

    public String giocaSassoCartaForbici(Minigame minigame, String manoUtente, String manoAvversaria, Animale animale) throws RuntimeException {
        if(manoUtente.isBlank()) throw new ExceptionMinigame("Nessuna opzione selezionata.");
        if(animale.getEnergia() < minigame.getEnergiaConsumata()) throw new ExceptionEnergia("Energia non sufficiente.");

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
        if(animale.getEnergia() < minigame.getEnergiaConsumata()) throw new ExceptionEnergia("Energia non sufficiente.");

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
        if(animale.getEnergia() < minigame.getEnergiaConsumata()) throw new ExceptionEnergia("Energia non sufficiente.");

        if(slot1 == slot2 && slot2 == slot3) {
            animale.gioca(minigame,true);
            return "vinto";
        }
        else {
            animale.gioca(minigame,false);
            return "perso";
        }
    }

    public void elimina(Item item) throws SQLException{
        if(item instanceof Cibo) {
            ciboDAO.eliminaDaInventario(item.getIdIstanza());
        }
        else if(item instanceof Vestito)
        {
            vestitoDAO.eliminaDaInventario(item.getIdIstanza());
        }
        utenteAttuale.eliminaItem(item);
    }

}
