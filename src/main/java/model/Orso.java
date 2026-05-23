package model;

public class Orso extends Animale{
    public Orso(String nome, int fame, int energia, int punti, boolean dorme) {
        super(nome, fame, energia, punti, dorme);
    }

    @Override
    public String toString() {
        return "L'orso: " + this.getNome();
    }
}
