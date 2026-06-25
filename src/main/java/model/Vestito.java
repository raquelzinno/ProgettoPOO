package model;

public class Vestito extends Item{
    private boolean indossato;
    private int boostEnergia;
    private  int boostFame;
    private int idAnimale;

    //costruttore per i vestiti di default del negozio
    public Vestito(String nome, int costo, Negozio negozio, int boostEnergia, int boostFame, String iconPath) {
        super(nome, costo, negozio, iconPath);
        indossato = false;
        idAnimale = -1;
        this.boostEnergia = boostEnergia;
        this.boostFame = boostFame;
    }

    //costruttore per i vestiti dell'inventario non indossati
    public Vestito(String nome, int costo, Negozio negozio, int idIstanza,int boostEnergia, int boostFame, String iconPath) {
        super(nome, costo, negozio, iconPath, idIstanza);
        indossato = false;
        idAnimale = -1;
        this.boostEnergia = boostEnergia;
        this.boostFame = boostFame;
    }

    //costruttore per i vestiti dell'inventario indossati
    public Vestito(String nome, int costo, Negozio negozio, int idIstanza,int boostEnergia, int boostFame, String iconPath, int idAnimale) {
        super(nome, costo, negozio, iconPath, idIstanza);
        indossato = true;
        this.idAnimale = idAnimale;
        this.boostEnergia = boostEnergia;
        this.boostFame = boostFame;
    }


    @Override
    public String toString() {  //layout per la jlist
        return "<html>" +
                "<b>" + this.getNome() + "</b>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prezzo: " +
                this.getCosto() + " punti<br>" +
                "<i>Boost Energia:</i> " + this.boostEnergia + "<br>" +
                "<i>Boost Fame:</i> " + this.boostFame + "<br>" + "&nbsp;" +
                "</html>";
    }

    @Override
    public Item creaCopia(int idIstanza) {  //crea una copia dell'oggetto Vestito
        return new Vestito(this.getNome(), this.getCosto(), this.getNegozio(), idIstanza, this.getBoostEnergia(), this.getBoostFame(), this.getIconPath());
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

    public int getIdAnimale() {
        return idAnimale;
    }

    public void setIdAnimale(int idAnimale) {
        this.idAnimale = idAnimale;
    }
}
