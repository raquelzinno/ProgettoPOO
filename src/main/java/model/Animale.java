package model;

import exceptions.ExceptionAnimale;

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

    public Animale(String nome, int fame, int energia, int punti, boolean dorme) {
        this.nome = nome;
        this.fameMax = this.fame = fame;  //inizialmente l'animale verrà creato con i suoi valori al max
        this.energiaMax = this.energia = energia;
        this.punti = punti;
        this.dorme = dorme;
        vestititIndossati = new ArrayList<Vestito>();
    }

    public void caricaEnergia() {
        if(energia < energiaMax)
        energia++;
    }

    public void consumaFame() {   //in beta
        if(fame > 0)
            fame--;
    }

    public void consumaEnergia() {   //in beta
        if(energia > 0)
            energia--;
    }

    public void gioca() { //idk for now, bisogna vedere meglio la gestione con la classe minigame
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws RuntimeException {
        if(nome.isBlank()) throw new ExceptionAnimale("Nessun nome inserito.");
        this.nome = nome;
    }

    public void setFameMax(int fameMax) {
        this.fameMax = fameMax;
    }

    public void setEnergiaMax(int energiaMax) {
        this.energiaMax = energiaMax;
    }

    public int getFameMax() {
        return fameMax;
    }

    public int getFame() {
        return fame;
    }

    public void setFame(int fame) {
        this.fame = fame;
    }

    public int getEnergiaMax() {
        return energiaMax;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public boolean isDorme() {
        return dorme;
    }

    public void setDorme(boolean dorme) {
        this.dorme = dorme;
    }

    public String getUtente() {
        return (utente.getLogin());
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public ArrayList<Vestito> getVestititIndossati() {
        return vestititIndossati;
    }

    public void setVestititIndossati(ArrayList<Vestito> vestititIndossati) {
        this.vestititIndossati = vestititIndossati;
    }
}
