package model;

public class Pinguino extends Animale{
    public Pinguino(String nome) {
        super(nome, 20, 15, 0);  //valori di default dell'animale di tipo Pinguino
    }

    @Override
    public String toString() {  //layout per la jlist
        return "Il pinguino: " + this.getNome();
    }
}
