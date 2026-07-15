package model;

public class Pinguino extends Animale{
    /**
     * Crea una nuova istanza di Pinguino.
     * Setta i valori di default specifici del tipo Pinguino.
     *
     * @param nome nome del pinguino
     */
    public Pinguino(String nome) {
        super(nome, 20, 20, 0);
    }

    /** Layout per la JList */
    @Override
    public String toString() {
        return "Il pinguino: " + this.getNome();
    }
}
