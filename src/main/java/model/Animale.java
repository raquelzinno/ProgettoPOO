package model;

public class Animale {
    private String nome;
    private int fame;
    private int energia;
    private int punti;
    private boolean dorme;

    public Animale(String nome, int fame, int energia, int punti, boolean dorme) {
        this.nome = nome;
        this.fame = fame;
        this.energia = energia;
        this.punti = punti;
        this.dorme = dorme;
    }

    public String getNome() {
        return nome;
    }

    public int getFame() {
        return fame;
    }

    public int getEnergia() {
        return energia;
    }

    public int getPunti() {
        return punti;
    }

    public boolean isDorme() {
        return dorme;
    }
}
