package model;

public class Vestito extends Item{
    private boolean indossato;
    private int boostEnergia;
    private  int boostFame;

    public Vestito(String nome, int costo, boolean indossato, int boostEnergia, int boostFame) {
        super(nome, costo);
        this.indossato = indossato;
        this.boostEnergia = boostEnergia;
        this.boostFame = boostFame;
    }

    public boolean isIndossato() {
        return indossato;
    }

    public int getBoostEnergia() {
        return boostEnergia;
    }

    public int getBoostFame() {
        return boostFame;
    }
}
