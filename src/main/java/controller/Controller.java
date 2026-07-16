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

    /**
     * Istanzia un oggetto negozio che avrà già tutti gli item di default e
     * riempie la lista con i minigames di default.
     *
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void inizializzaDati() throws SQLException{
        negozioBase = new Negozio(popolaNegozio());
        minigamesDiDefault = popolaMinigames();
    }

    /** @return un {@link ArrayList} contenente i minigames di default */
    public ArrayList<Minigame> getMinigamesDiDefault() {
        return minigamesDiDefault;
    }

    /** @return il {@link Negozio} base */
    public Negozio getNegozioBase() {
        return negozioBase;
    }

    /**
     * Crea un nuovo utente assicurandosi che non sia già esistente e lo inserisce nel database.
     *
     * @param login    login dell'utente
     * @param password password dell'utente
     * @throws RuntimeException se si verifica un errore durante la creazione dell'utente
     * @throws SQLException     se si verifica un errore durante l'interazione con il database
     */
    public void creaUtente(String login, String password) throws RuntimeException, SQLException{
        if(login.isBlank()) throw new ExceptionUtente("Il campo nome utente è vuoto.");
        if(password.isBlank()) throw new ExceptionPassword("Il campo password è vuoto.");

        if(login.length() > 15) throw new ExceptionUtente("Il nome utente deve essere di massimo 15 caratteri.");
        if(password.length() > 15) throw new ExceptionPassword("La password deve essere di massimo 15 caratteri.");

        if(utenteDAO.controlloLogin(login)) throw new ExceptionUtente("Nome utente già esistente.");

        utenteDAO.salvaUtente(login, password);
    }

    /**
     * Controlla se l'utente è presente nel database per poter effettuare l'accesso,
     * se esiste lo segna come l'utente attualmente loggato nel sistema.
     *
     * @param login    login dell'utente
     * @param password password dell'utente
     * @return {@code true} se l'accesso è stato effettuato, {@code false} altrimenti
     * @throws RuntimeException se si verifica un errore durante la ricerca dell'utente
     * @throws SQLException     se si verifica un errore durante l'interazione con il database
     */
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

        throw new ExceptionUtente("Utente non trovato.");
    }

    /**
     * Controlla se l'utente possiede già il numero massimo di animali consentiti,
     * sincronizzando la lista degli animali posseduti dal database.
     *
     * @throws RuntimeException se l'utente possiede già il massimo degli animali
     * @throws SQLException     se si verifica un errore durante l'interazione con il database
     */
    public void checkAnimali() throws RuntimeException, SQLException {
        //Forza il controller a leggere gli animali dal db
        this.sincronizzaListaAnimali();

        //Stampa di debug in console per vedere quanti animali rileva IntelliJ in questo momento
        System.out.println("Animali rilevati nel DB per il controllo: " + utenteAttuale.getAnimaliPosseduti().size());
        if((utenteAttuale.getAnimaliPosseduti()).size() >= 2) throw new ExceptionTroppiAnimali("Hai il massimo di animali consentiti!");
    }

    /**
     * Crea un nuovo animale assicurandosi che l'utente non possegga già
     * un animale con lo stesso nome e lo inserisce nel database.
     *
     * @param tipo tipo dell'animale
     * @param nome nome dell'animale
     * @throws RuntimeException se si verifica un errore durante la creazione dell'animale
     * @throws SQLException     se si verifica un errore durante l'interazione con il database
     */
    public void creaAnimale(String tipo, String nome) throws RuntimeException, SQLException {
        checkAnimali(); //controlla se l'utente possiede già il numero massimo di animali

        if(tipo.isBlank()) throw new ExceptionAnimale("Nessun tipo selezionato.");
        if(nome.isBlank()) throw new ExceptionAnimale("Nessun nome inserito.");

        if(nome.length() > 15) throw new ExceptionAnimale("Il nome dell'animale deve essere di massimo 15 caratteri.");

        for(Animale a : utenteAttuale.getAnimaliPosseduti()){
            if(a.getNome().equals(nome)){
                throw new ExceptionAnimale("Nome animale già esistente.");
            }
        }

        //crea l'animale in base al tipo selezionato
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

    /**
     * Sincronizza la lista locale degli animali posseduti dall'utente attuale
     * con quella del database.
     *
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void sincronizzaListaAnimali() throws SQLException {
        ArrayList<Animale> animaliDalDb = animaleDAO.recuperaListaAnimali(idUtenteAttuale);
        utenteAttuale.setAnimaliPosseduti(animaliDalDb);
    }

    /**
     * Recupera l'id dell'animale selezionato.
     *
     * @param animaleAttuale l' {@link Animale} selezionato
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void selezionaAnimale(Animale animaleAttuale) throws SQLException {
            idAnimaleAttuale = animaleDAO.recuperaId(idUtenteAttuale, animaleAttuale.getNome());
    }

    /**
     * Deseleziona l'animale.
     */
    public void deselezionaAnimale() {
        idAnimaleAttuale = -1;
    }

    /**
     * Salva lo stato attuale dell'animale nel database e lo sincronizza con la memoria locale.
     *
     * @param animale l' {@link Animale} da aggiornare
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void salvaStatoAnimale(Animale animale) throws SQLException {
        animaleDAO.aggiornaStatoAnimale(animale, idAnimaleAttuale);
        sincronizzaListaAnimali(); //sincronizzo la memoria locale con il DB

    }

    /**
     * Se l'utente esce dal gioco e ha degli animali che dormono,
     * il loro stato è aggiornato a sveglio nel database.
     *
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void puliziaDati() throws SQLException{
        animaleDAO.resetStatoSonno(idUtenteAttuale);
    }

    /**
     * Aggiorna il nome dell'animale selezionato nel database e nella memoria locale,
     * assicurandosi che l'utente non possegga già un animale con lo stesso nome.
     *
     * @param nome    nome da impostare
     * @param animale l' {@link Animale} a cui cambiare il nome
     * @throws RuntimeException se si verifica un errore durante l'aggiornamento del nome
     * @throws SQLException     se si verifica un errore durante l'interazione con il database
     */
    public void modificaNomeAnimale(String nome, Animale animale) throws RuntimeException, SQLException {
        if(nome.isBlank()) throw new ExceptionPassword("Il campo nome è vuoto.");
        if(nome.length() > 15) throw new ExceptionUtente("Il nome dell'animale deve essere di massimo 15 caratteri.");

        for(Animale a : utenteAttuale.getAnimaliPosseduti()){
            if(a.getNome().equals(nome)){
                throw new ExceptionAnimale("Nome animale già esistente.");
            }
        }
        animaleDAO.modificaNome(idAnimaleAttuale, nome);
        animale.setNome(nome);
    }

    /**
     * Aggiunge un animale alla lista degli animali posseduti dall'utente.
     *
     * @param animale il nuovo {@link Animale}
     */
    public void aggiungiAnimale(Animale animale){
        utenteAttuale.creaAnimale(animale);
    }

    /**
     * Crea la lista completa degli item che appartengono al negozio, recuperandoli dal database.
     *
     * @return un {@link ArrayList} con tutti gli item del negozio
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public ArrayList<Item> popolaNegozio() throws SQLException {
        ArrayList<Item> listaCompleta = new ArrayList<>();

        //recupera i cibi e li aggiunge alla lista completa
        listaCompleta.addAll(ciboDAO.recuperaListaCibo());

        //recupera i vestiti e li aggiunge alla lista completa
        listaCompleta.addAll(vestitoDAO.recuperaListaVestiti());

        return listaCompleta;
    }

    /**
     * Crea la lista completa dei minigame, recuperandoli dal database.
     *
     * @return un {@link ArrayList} con tutti i minigame
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public ArrayList<Minigame> popolaMinigames() throws SQLException {
        return minigameDAO.recuperaMinigame();
    }

    /**
     * Recupera l'inventario dell'utente dal database, aggiornando la lista locale degli item
     * posseduti dall'utente.
     *
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
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

    /**
     * Recupera i vestiti attualmente indossati dall'animale e aggiorna la lista locale.
     *
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
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


    /**
     * Compra un item dal negozio se ha abbastanza punti e ricalcola i punti dell'animale.
     * Salva le modifiche nel database.
     *
     * @param item    l' {@link Item} da comprare
     * @param animale l' {@link Animale} che effettua l'acquisto
     * @throws RuntimeException se l'animale non ha abbastanza punti per comprare l'item
     * @throws SQLException     se si verifica un errore durante l'interazione con il database
     */
    public void compra(Item item, Animale animale) throws RuntimeException, SQLException{
        if(animale.getPunti() < item.getCosto()) {  //controllo se ha abbastanza punti
            throw new ExceptionPuntiNonSufficienti("L'animale non ha abbastanza punti!");
        }

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

    /**@return l' {@link Utente} attuale */
    public Utente getUtenteAttuale() {
        return utenteAttuale;
    }

    /**
     * Imposta lo stato dell'utente quando esce dall'account e rimuove il suo id dalla memoria locale.
     */
    public void esciUtente() {
        if(utenteAttuale!=null) {
            this.utenteAttuale.setAccessoEffettuato(false);
            this.utenteAttuale = null;
        }
        idUtenteAttuale = -1;
    }

    /**
     * Se l'item è un cibo, all'utilizzo ricarica la fame dell'animale e viene rimosso dall'inventario perché consumato.
     * Se l'item è un vestito, all'utilizzo viene indossato dall'animale e vengono applicati i valori di boost.
     * Infine viene aggiornato lo stato dell'animale.
     *
     * @param item    l' {@link Item} da usare
     * @param animale l' {@link Animale} che vuole usare l'item
     * @throws RuntimeException se si verifica un errore durante l'utilizzo
     * @throws SQLException     se si verifica un errore durante l'interazione con il database
     */
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

    /**
     * Rimuove un vestito dall'animale e aggiorna il suo stato.
     *
     * @param vestito il {@link Vestito} da rimuovere
     * @param animale l' {@link Animale} a cui si vuole rimuovere il vestito
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void rimuoviVestito(Vestito vestito, Animale animale) throws SQLException{
        if(utenteAttuale.rimuoviVestito(vestito, animale)) {
            vestitoDAO.rimuoviVestito(vestito.getIdIstanza());
            vestito.setIdAnimale(-1);
            salvaStatoAnimale(animale);
        }
    }

    /**
     * Imposta il home frame, serve per gestire i valori dell'animale che
     * cambiano a causa del timer.
     *
     * @param tamagotchiFrame il frame della finestra Tamagotchi
     */
    public void setHomeFrame(Tamagotchi tamagotchiFrame) {
        this.tamagotchiFrame = tamagotchiFrame;
    }

    /**
     * Inizia il timer per gestire la fame e l'energia che diminuiscono automaticamente,
     * ad ogni minuto i valori vengono consumati.
     *
     * @param animale l' {@link Animale} a cui diminuiscono i valori
     */
    public void iniziaTimer(Animale animale) {
        if (gameTimer != null && gameTimer.isRunning())  //gestione di possibili timer attivi
            gameTimer.stop();
        gameTimer = new Timer(60000, e -> {  //viene istanziato il timer di gioco
            animale.consumaEnergia();
            animale.consumaFame();
            tamagotchiFrame.aggiornaLabel();
        });
        gameTimer.start();
    }

    /**
     * Ferma il timer di gioco.
     */
    public void fermaTimer() {
        gameTimer.stop();
    }

    /**
     * Addormenta l'animale selezionato, aggiornando il suo stato nel database e nella memoria locale.
     * Istanzia il nuovo timer del sonno, l'energia dell'animale aumenta ogni secondo.
     *
     * @param animale l' {@link Animale} addormentato
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void addormenta(Animale animale) throws SQLException {
        animale.setDorme(true);
        salvaStatoAnimale(animale);
        sonnoTimer = new Timer(1000, e -> {
            animale.caricaEnergia();
            tamagotchiFrame.aggiornaLabel();
        });
        sonnoTimer.start();
    }

    /**
     * Sveglia l'animale selezionato, aggiornando il suo stato nel database e nella memoria locale.
     * Se il timer del sonno è attivo, viene fermato.
     *
     * @param animale the animale
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void sveglia(Animale animale) throws SQLException {
        animale.setDorme(false);
        if(sonnoTimer != null && sonnoTimer.isRunning())
            sonnoTimer.stop();
        salvaStatoAnimale(animale);
    }

    /**
     * Elimina l'animale selezionato dal database e dalla memoria locale.
     * Se sta attualmente indossando dei vestiti vengono rimossi.
     *
     * @param animale l' {@link Animale} da eliminare
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void eliminaAnimale(Animale animale) throws SQLException{

        //gestione di possibili timer attivi che devono essere fermati
        if (gameTimer != null && gameTimer.isRunning())
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

    /**
     * Aggiorna la password dell'utente nel database e nella memoria locale,
     * solo se l'utente inserisce correttamente la vecchia password.
     *
     * @param vecchiaPass la vecchia password
     * @param nuovaPass   la nuova password
     * @throws RuntimeException se si verifica un errore durante l'aggiornamento della password
     * @throws SQLException     se si verifica un errore durante l'interazione con il database
     */
    public void cambiaPassword(String vecchiaPass, String nuovaPass) throws RuntimeException, SQLException {
        if(nuovaPass.isBlank()) throw new ExceptionPassword("Nessuna password inserita.");
        if(nuovaPass.length() > 15) throw new ExceptionPassword("La password deve essere di massimo 15 caratteri.");

        if(utenteAttuale.getPassword().equals(vecchiaPass)){
            utenteDAO.aggiornaPassword(idUtenteAttuale, nuovaPass);
            utenteAttuale.setPassword(nuovaPass);
        } else throw new ExceptionPassword("La password è errata.");
    }

    /**
     * Gioca a sasso, carta, forbici se l'animale ha abbastanza energia,
     * calcola in automatico il risultato del gioco in base alla mano dell'utente e la mano avversaria.
     *
     * @param minigame       il {@link Minigame} selezionato
     * @param manoUtente     la mano dell'utente
     * @param manoAvversaria la mano avversaria
     * @param animale        l' {@link Animale} che sta giocando
     * @return una {@link String} contenente il risultato del gioco
     * @throws RuntimeException se si verifica un errore durante l'avvio del minigame
     */
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

    /**
     * Calcola in modo casuale la mano avversaria nel gioco sasso, carta, forbici.
     *
     * @return una {@link String} contenente la mano avversaria
     */
    public String casualeSassoCartaForbici() {
        String sasso = "sasso";
        String carta = "carta";
        String forbici = "forbici";
        String[] opzioni = {sasso,carta,forbici};

        Random random = new Random();
        int indiceCasuale = random.nextInt(opzioni.length);
        return opzioni[indiceCasuale];
    }

    /**
     * Calcola in modo casuale la mano avversaria nel gioco del lancio della moneta.
     *
     * @return una {@link String} contenente la mano avversaria
     */
    public String casualeLancioMoneta() {
        String testa = "testa";
        String croce = "croce";
        String[] opzioni = {testa,croce};

        Random random = new Random();
        int indiceCasuale = random.nextInt(opzioni.length);
        return opzioni[indiceCasuale];
    }

    /**
     * Gioca a al lancio della moneta se l'animale ha abbastanza energia,
     * calcola in automatico il risultato del gioco in base alla mano dell'utente e la mano avversaria.
     *
     * @param minigame        il {@link Minigame} selezionato
     * @param inputUtente     mano dell'utente
     * @param risultatoLancio risultato del lancio
     * @param animale         l' {@link Animale} che sta giocando
     * @return una {@link String} contenente il risultato del gioco
     * @throws RuntimeException se si verifica un errore durante l'avvio del minigame
     */
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

    /**
     * Calcola in modo casuale il risultato della slot machine.
     *
     * @return il risultato della slot machine
     */
    public int casualeSlotMachine() {
        Random random = new Random();
        return random.nextInt(4);
    }

    /**
     * Gioca a alla slot machine se l'animale ha abbastanza energia,
     * calcola in automatico il risultato del gioco in base ai numeri estratti.
     *
     * @param minigame il {@link Minigame} selezionato
     * @param animale  l' {@link Animale} che sta giocando
     * @param slot1    numero dello slot 1
     * @param slot2    numero dello slot 2
     * @param slot3    numero dello slot 3
     * @return una {@link String} contenente il risultato del gioco
     * @throws RuntimeException se si verifica un errore durante l'avvio del minigame
     */
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

    /**
     * Elimina l'item selezionato dal database e dalla lista locale degli item posseduti dall'utente.
     *
     * @param item l' {@link Item} da eliminare
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public void eliminaItem(Item item) throws SQLException{
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
