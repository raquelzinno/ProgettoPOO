package model;

public class Pinguino extends Animale{
    public Pinguino(String nome, int fame, int energia, int punti, boolean dorme) {
        super(nome, fame, energia, punti, dorme);
    }

    @Override
    public String toString() {
        return "Il pinguino: " + this.getNome();
    }
}
