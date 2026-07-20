package model;

import java.util.ArrayList;

public class Animale {
    private String nome;
    private int fameMax;
    private int fame;
    private int energiaMax;
    private int energia;
    private int punti;
    private boolean dorme;
    private Utente utente;
    private ArrayList<Vestito> vestititIndossati;

    /**
     * Crea una nuova istanza di Animale.
     * Inizialmente l'animale verrà creato con i suoi valori al max,
     * di default è sempre sveglio e non indossa vestiti.
     *
     * @param nome    nome dell'animale
     * @param fame    fame dell'animale
     * @param energia energia dell'animale
     * @param punti   punti dell'animale
     */
    public Animale(String nome, int fame, int energia, int punti) {
        this.nome = nome;
        this.fameMax = this.fame = fame;
        this.energiaMax = this.energia = energia;
        this.punti = punti;
        this.dorme = false;
        vestititIndossati = new ArrayList<Vestito>();
    }

    /**
     * Quando dorme, se l'energia è minore dell'energia massima, l'energia viene aumentata.
     */
    public void caricaEnergia() {
        if(energia < energiaMax)
            energia++;
    }

    /**
     * Consuma fame, se la fame non è arrivata a 0 può essere consumata.
     */
    public void consumaFame() {
        if(fame > 0)
            fame--;
    }

    /**
     * Consuma energia, se l'energia non è arrivata a 0 può essere consumata.
     */
    public void consumaEnergia() {
        if(energia > 0)
            energia--;
    }

    /**
     * L'utente può giocare solo se l'animale ha abbastanza energia rispetto a quella richiesta dal minigame.
     * In caso di vittoria l'animale riceve una ricompensa a la sua energia diminuisce, altrimenti
     * solo l'energia verrà consumata.
     *
     * @param minigame il minigame selezionato
     * @param vittoria se l'utente ha vinto o meno
     */
    public void gioca(Minigame minigame, boolean vittoria) {
        if(energia >= minigame.getEnergiaConsumata()) {
            energia = energia - minigame.getEnergiaConsumata(); //in ogni caso energia verrà consumata
            if (vittoria) {
                punti = punti + minigame.getRicompensa(); //in caso di vittoria ricalcolo la ricompensa
            }
        }
    }

    // -- GETTER E SETTER ---

    /** @return nome dell'animale */
    public String getNome() {
        return nome;
    }

    /** @param nome nome da impostare */
    public void setNome(String nome) { this.nome = nome; }

    /** @param fameMax fame max da impostare */
    public void setFameMax(int fameMax) {
        this.fameMax = fameMax;
    }

    /** @param energiaMax energia max da impostare */
    public void setEnergiaMax(int energiaMax) {
        this.energiaMax = energiaMax;
    }

    /** @return fame max */
    public int getFameMax() {
        return fameMax;
    }

    /** @return fame dell'animale */
    public int getFame() {
        return fame;
    }

    /** @param fame fame dell'animale */
    public void setFame(int fame) {
        this.fame = fame;
    }

    /** @return energia max */
    public int getEnergiaMax() {
        return energiaMax;
    }

    /** @return energia dell'animale */
    public int getEnergia() {
        return energia;
    }

    /** @param energia energia da impostare */
    public void setEnergia(int energia) {
        this.energia = energia;
    }

    /** @return punti dell'animale */
    public int getPunti() {
        return punti;
    }

    /** @param punti tda impostare */
    public void setPunti(int punti) {
        this.punti = punti;
    }

    /** @return vero se l'animale dorme, falso altrimenti */
    public boolean isDorme() {
        return dorme;
    }

    /** @param dorme imposta lo stato dell'animale */
    public void setDorme(boolean dorme) {
        this.dorme = dorme;
    }

    /** @return l'utente a cui appartiene l'animale */
    public String getUtente() {
        return (utente.getLogin());
    }

    /** @param utente utente da impostare */
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    /** @return lista dei vestiti indossati dall'animale */
    public ArrayList<Vestito> getVestitiIndossati() {
        return vestititIndossati;
    }

    /** @param vestititIndossati lista dei vestiti indossati da impostare */
    public void setVestititIndossati(ArrayList<Vestito> vestititIndossati) { this.vestititIndossati = vestititIndossati; }
}
