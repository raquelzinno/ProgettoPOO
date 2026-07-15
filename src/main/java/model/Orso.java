package model;

public class Orso extends Animale{
    /**
     * Crea una nuova istanza di Orso.
     * Setta i valori di default specifici del tipo Orso.
     *
     * @param nome nome dell'orso
     */
    public Orso(String nome) {
        super(nome, 20, 30, 0);
    }

    /** Layout per la JList */
    @Override
    public String toString() {
        return "L'orso: " + this.getNome();
    }

    /** La fame dell'orso diminuisce più velocemente di quella del pinguino */
    @Override
    public void consumaFame() {
        if (this.getFame()> 0)
            this.setFame(this.getFame() - 2);

        if (this.getFame() < 0) { //controllo per non far arrivare la fame in negativo
            this.setFame(0);
        }
    }
}
