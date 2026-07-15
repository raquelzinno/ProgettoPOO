package model;

public class Cibo extends Item{
    private TipoCibo tipo;
    private int puntiFame;

    /**
     * Costruttore per i cibi di default del negozio.
     *
     * @param nome      nome del cibo
     * @param costo     costo del cibo
     * @param negozio   negozio di appartenenza
     * @param tipo      tipo (dolce, salato, bevanda)
     * @param puntiFame punti fame che fa guadagnare all'animale
     * @param iconPath  path dell'icona del cibo
     */
    public Cibo(String nome, int costo, Negozio negozio, TipoCibo tipo, int puntiFame, String iconPath) {
        super(nome, costo, negozio, iconPath);
        this.tipo = tipo;
        this.puntiFame = puntiFame;
    }

    /**
     * Costruttore per i cibi inseriti nell'inventario.
     *
     * @param nome      nome del cibo
     * @param costo     costo del cibo
     * @param negozio   negozio di appartenenza
     * @param idIstanza l'id dell'istanza del cibo, necessario per la gestione nel database
     * @param tipo      tipo (dolce, salato, bevanda)
     * @param puntiFame punti fame che fa guadagnare all'animale
     * @param iconPath  path dell'icona del cibo
     */
    public Cibo(String nome, int costo, Negozio negozio, int idIstanza, TipoCibo tipo, int puntiFame, String iconPath) {
        super(nome, costo, negozio, iconPath, idIstanza);
        this.tipo = tipo;
        this.puntiFame = puntiFame;
    }

    /** Crea una copia dell'oggetto cibo del negozio per inserirlo nell'inventario dell'animale */
    @Override
    public Item creaCopia(int idIstanza) {
        return new Cibo(this.getNome(), this.getCosto(), this.getNegozio(), idIstanza, this.getTipo(), this.getPuntiFame(), this.getIconPath());
    }

    /** Stampa nome e valori del vestito, layout per la JList */
    @Override
    public String toString() {
        return "<html>" +
                "<b>" + this.getNome() + "</b>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prezzo: " +
                this.getCosto() + " punti<br>" +
                "<i>Tipo: " + this.tipo + "</i><br>" +
                "<i>Punti fame: </i>" + this.puntiFame +"<br>" + "&nbsp;" +
                "</html>";
    }

    // --- GETTER E SETTER ---
    /** @return tipo del cibo */
    public TipoCibo getTipo() { return tipo; }

    /** @param tipo tipo da impostare */
    public void setTipo(TipoCibo tipo) {
        this.tipo = tipo;
    }

    /** @return punti fame del cibo */
    public int getPuntiFame() {
        return puntiFame;
    }

    /** @param puntiFame punti fame da impostare */
    public void setPuntiFame(int puntiFame) {
        this.puntiFame = puntiFame;
    }
}
