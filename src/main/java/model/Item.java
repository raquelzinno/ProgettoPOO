package model;

public class Item {
    private String nome;
    private int costo;
    private Negozio negozio;

    public Item(String nome, int costo, Negozio negozio) {
        this.nome = nome;
        this.costo = costo;
        this.negozio = negozio;

        negozio.aggiungiItem(this); //aggiunge direttamente l'item all'array list del negozio
                                    // potremmo dover mettere un controllo in caso il negozio sia NULL
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public Negozio getNegozio() {
        return negozio;
    }

    public void setNegozio(Negozio negozio) {
        this.negozio = negozio;
    }
}
