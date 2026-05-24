package model;

public class Orso extends Animale{
    public Orso(String nome, int fame, int energia, int punti, boolean dorme) {
        super(nome, fame, energia, punti, dorme);
    }

    @Override
    public String toString() {
        return "L'orso: " + this.getNome();
    }

    @Override
    public void consumaFame() { //l'orso consuma più fame
        if (this.getFame()> 0)
            this.setFame(this.getFame() - 2);

        if (this.getFame() < 0) { //controllo per non far arrivare la fame in negativo
            this.setFame(0);
        }
    }
}
