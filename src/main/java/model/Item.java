package model;

public class Item {
    private String nome;
    private int costo;

    public Item(String nome, int costo) {
        this.nome = nome;
        this.costo = costo;
    }

    public String getNome() {
        return nome;
    }

    public int getCosto() {
        return costo;
    }
}
