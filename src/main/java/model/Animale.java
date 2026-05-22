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

    public Animale(String nome, int fame, int energia, int punti, boolean dorme) {
        this.nome = nome;
        this.fameMax = this.fame = fame;  //inizialmente l'animale verrà creato con i suoi valori al max
        this.energiaMax = this.energia = energia;
        this.punti = punti;
        this.dorme = dorme;
        vestititIndossati = new ArrayList<Vestito>();
    }

    public void addormenta() {
        if(dorme) {      //se dorme è true, sta già dormendo e non lo puoi rimettere a dormire
            System.out.println("Sta già dormendo! Devi prima svegliarlo."); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
        }
        else {
            while(energia<energiaMax) {
                energia++;   //se è sveglio allora viene messo a dormire e l'energia attuale aumenta fino al max
            }
            dorme = false;   //appena maxata l'energia, l'animale si sveglia
        }
    }

    public void consumaFame(int puntiDaTogliere) {   //in beta
        fame = fame - puntiDaTogliere;
    }

    //sarebbe bello riuscire a consumare fame/energia nel tempo, oppure troviamo un altro sistema più preciso per il consumo dei valori

    public void consumaEnergia(int puntiDaTogliere) {   //in beta
        energia = energia - puntiDaTogliere;
    }


    public void gioca() { //idk for now, bisogna vedere meglio la gestione con la classe minigame
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
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
