package model;

public class Cibo extends Item{
    private TipoCibo tipo;
    private int puntiFame;

    public Cibo(String nome, int costo, Negozio negozio, TipoCibo tipo, int puntiFame) {
        super(nome, costo, negozio);
        this.tipo = tipo;
        this.puntiFame = puntiFame;
    }

    public TipoCibo getTipo() {
        return tipo;
    }

    public void setTipo(TipoCibo tipo) {
        this.tipo = tipo;
    }

    public int getPuntiFame() {
        return puntiFame;
    }

    public void setPuntiFame(int puntiFame) {
        this.puntiFame = puntiFame;
    }
}
