package model;

public class Orso extends Animale{
    public Orso(String nome) {
        super(nome, 20, 30, 0);  //valori di default dell'animale di tipo Orso
    }

    @Override
    public String toString() {
        return "L'orso: " + this.getNome();  //layout per la jlist
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
