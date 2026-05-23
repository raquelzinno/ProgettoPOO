package model;

public class Vestito extends Item{
    private boolean indossato;
    private int boostEnergia;
    private  int boostFame;

    public Vestito(String nome, int costo, Negozio negozio, boolean indossato, int boostEnergia, int boostFame) {
        super(nome, costo, negozio);
        this.indossato = indossato;
        this.boostEnergia = boostEnergia;
        this.boostFame = boostFame;
    }

    @Override
    public String toString() {
        return "<html>" +
                "<b>" + this.getNome() + "</b>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prezzo: " +
                this.getCosto() + " punti<br>" +
                "<i>Boost Energia:</i> " + this.boostEnergia + "<br>" +
                "<i>Boost Fame:</i> " + this.boostFame + "<br>" + "&nbsp;" +
                "</html>";
    }

    public boolean isIndossato() {
        return indossato;
    }

    public void setIndossato(boolean indossato) {
        this.indossato = indossato;
    }

    public int getBoostEnergia() {
        return boostEnergia;
    }

    public void setBoostEnergia(int boostEnergia) {
        this.boostEnergia = boostEnergia;
    }

    public int getBoostFame() {
        return boostFame;
    }

    public void setBoostFame(int boostFame) {
        this.boostFame = boostFame;
    }
}
