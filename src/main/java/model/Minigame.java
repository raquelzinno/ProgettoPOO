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

        Minigame sassoCartaForbici = new Minigame("Sasso, Carta, Forbici!", 5, 5);
        Minigame lancioMoneta = new Minigame("Lancio della moneta", 4,6);
        Minigame slotMachine = new Minigame("Slot machine", 100,20);
        listaMinigame.add(sassoCartaForbici);
        listaMinigame.add(lancioMoneta);
        listaMinigame.add(slotMachine);
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
