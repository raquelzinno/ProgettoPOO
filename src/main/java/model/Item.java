package model;

public abstract class Item {
    private String nome;
    private int costo;
    private Negozio negozio;

    public Item(String nome, int costo, Negozio negozio) {
        this.nome = nome;
        this.costo = costo;
        this.negozio = negozio;
    }

    public abstract Item creaCopia(); //il creaCopia ha un comportamento concreto nelle classi che estendono Item

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
