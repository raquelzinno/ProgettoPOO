package model;

import java.util.ArrayList;

public class Minigame {
    private String nome;
    private  int ricompensa;
    private int energiaConsumata;

    public Minigame(String nome, int ricompensa, int energiaConsumata) {
        this.nome = nome;
        this.ricompensa = ricompensa;
        this.energiaConsumata = energiaConsumata;
    }

    public static ArrayList<Minigame> getMinigamesDiDefault() { //permette di stabilire quali saranno i minigames di default
        ArrayList<Minigame> listaMinigame = new ArrayList<>();

        Minigame sassoCartaForbici = new Minigame("Sasso, Carta, Forbici!", 5, 2);
        Minigame lancioMoneta = new Minigame("Lancio della moneta", 5,5);

        listaMinigame.add(sassoCartaForbici);
        listaMinigame.add(lancioMoneta);
        return listaMinigame;
    }

    public String getNomeGioco() {
        return nome;
    }

    public int getRicompensa() {
        return ricompensa;
    }

    public int getEnergiaConsumata() {
        return energiaConsumata;
    }
}
