package model;

public class Minigame {
    private String nome;
    private  int ricompensa;
    private int energiaConsumata;

    public Minigame(String nome, int ricompensa, int energiaConsumata) {
        this.nome = nome;
        this.ricompensa = ricompensa;
        this.energiaConsumata = energiaConsumata;
    }

    public String getNome() {
        return nome;
    }

    public int getRicompensa() {
        return ricompensa;
    }

    public int getEnergiaConsumata() {
        return energiaConsumata;
    }
}
