package model;

public class Cibo extends Item{
    private TipoCibo tipo;
    private int puntiFame;

    public Cibo(String nome, int costo, Negozio negozio, TipoCibo tipo, int puntiFame, String iconPath) {
        super(nome, costo, negozio, iconPath);
        this.tipo = tipo;
        this.puntiFame = puntiFame;
    }

    public Cibo(String nome, int costo, Negozio negozio, int idIstanza, TipoCibo tipo, int puntiFame, String iconPath) {
        super(nome, costo, negozio, iconPath, idIstanza);
        this.tipo = tipo;
        this.puntiFame = puntiFame;
    }

    @Override
    public String toString() {  //layout per la jlist
        return "<html>" +
                "<b>" + this.getNome() + "</b>" +
                //"<p style=\text-align: right;\">Questo testo sarà allineato a destra.</p>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prezzo: " +
                this.getCosto() + " punti<br>" +
                "<i>Tipo: " + this.tipo + "</i><br>" +
                "<i>Punti fame: </i>" + this.puntiFame +"<br>" + "&nbsp;" +
                "</html>";
    }

    @Override
    public Item creaCopia(int idIstanza) { //crea una copia dell'oggetto Cibo
        return new Cibo(this.getNome(), this.getCosto(), this.getNegozio(), idIstanza, this.getTipo(), this.getPuntiFame(), this.getIconPath());
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
