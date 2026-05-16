package model;

public class Cibo extends Item{
    private String tipo;
    private int puntiFame;

    public Cibo(String nome, int costo, String tipo, int puntiFame) {
        super(nome, costo);
        this.tipo = tipo;
        this.puntiFame = puntiFame;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPuntiFame() {
        return puntiFame;
    }
}
