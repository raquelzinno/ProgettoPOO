package model;

import java.util.ArrayList;

/**
 * The type Minigame.
 */
public class Minigame {
    private String nome;
    private  int ricompensa;
    private int energiaConsumata;

    /**
     * Crea una nuova istanza di Minigame.
     *
     * @param nome             nome del minigame
     * @param ricompensa       ricompensa del minigame
     * @param energiaConsumata energia consumata quando si gioca al minigame
     */
    public Minigame(String nome, int ricompensa, int energiaConsumata) {
        this.nome = nome;
        this.ricompensa = ricompensa;
        this.energiaConsumata = energiaConsumata;
    }

    /**
     * Crea una lista in cui inserire tutti i minigames.
     * Permette di stabilire quali saranno i minigames di default
     *
     * @return lista dei minigames di default
     */
    public static ArrayList<Minigame> getMinigamesDiDefault() {
        ArrayList<Minigame> listaMinigame = new ArrayList<>();

        Minigame sassoCartaForbici = new Minigame("Sasso, Carta, Forbici!", 5, 5);
        Minigame lancioMoneta = new Minigame("Lancio della moneta", 4,6);
        Minigame slotMachine = new Minigame("Slot machine", 100,20);

        listaMinigame.add(sassoCartaForbici);
        listaMinigame.add(lancioMoneta);
        listaMinigame.add(slotMachine);

        return listaMinigame;
    }

    // --- GETTER E SETTER ---

    /** @return nome del gioco */
    public String getNomeGioco() {
        return nome;
    }

    /** @return ricompensa del gioco */
    public int getRicompensa() {
        return ricompensa;
    }

    /** @return energia consumata quando si gioca al minigame */
    public int getEnergiaConsumata() {
        return energiaConsumata;
    }
}
