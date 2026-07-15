package model;

public class Vestito extends Item{
    private boolean indossato;
    private int boostEnergia;
    private  int boostFame;
    private int idAnimale;

    /**
     * Costruttore per i vestiti di default del negozio.
     * Setta indossato falso di default, l'id dell'animale non appartiene a nessun animale.
     *
     * @param nome         nome del vestito
     * @param costo        costo del vestito
     * @param negozio      negozio a cui appartiene il vestito
     * @param boostEnergia boost energia del vestito
     * @param boostFame    boost fame del vestito
     * @param iconPath     path dell'icona del vestito
     */

    public Vestito(String nome, int costo, Negozio negozio, int boostEnergia, int boostFame, String iconPath) {
        super(nome, costo, negozio, iconPath);
        indossato = false;
        idAnimale = -1;
        this.boostEnergia = boostEnergia;
        this.boostFame = boostFame;
    }

    /**
     * Costruttore per i vestiti dell'inventario non indossati.
     * Setta indossato falso di default.
     *
     * @param nome         nome del vestito
     * @param costo        costo del vestito
     * @param negozio      negozio a cui appartiene il vestito
     * @param idIstanza    l'id dell'istanza del vestito, necessario per la gestione nel database
     * @param boostEnergia boost energia del vestito
     * @param boostFame    boost fame del vestito
     * @param iconPath     path dell'icona del vestito
     */

    public Vestito(String nome, int costo, Negozio negozio, int idIstanza,int boostEnergia, int boostFame, String iconPath) {
        super(nome, costo, negozio, iconPath, idIstanza);
        indossato = false;
        idAnimale = -1;
        this.boostEnergia = boostEnergia;
        this.boostFame = boostFame;
    }

    /**
     * Costruttore per i vestiti dell'inventario indossati.
     * Setta indossato vero di default.
     *
     * @param nome         nome del vestito
     * @param costo        costo del vestito
     * @param negozio      negozio a cui appartiene il vestito
     * @param idIstanza    l'id dell'istanza del vestito, necessario per la gestione nel database
     * @param boostEnergia boost energia del vestito
     * @param boostFame    boost fame del vestito
     * @param iconPath     path dell'icona del vestito
     * @param idAnimale    l'id dell'animale che indossa il vestito, necessario per la gestione nel database
     */

    public Vestito(String nome, int costo, Negozio negozio, int idIstanza,int boostEnergia, int boostFame, String iconPath, int idAnimale) {
        super(nome, costo, negozio, iconPath, idIstanza);
        indossato = true;
        this.idAnimale = idAnimale;
        this.boostEnergia = boostEnergia;
        this.boostFame = boostFame;
    }

    /** Crea una copia dell'oggetto vestito del negozio per inserirlo nell'inventario dell'animale */
    @Override
    public Item creaCopia(int idIstanza) {  //crea una copia dell'oggetto Vestito
        return new Vestito(this.getNome(), this.getCosto(), this.getNegozio(), idIstanza, this.getBoostEnergia(), this.getBoostFame(), this.getIconPath());
    }

    /** Stampa nome e valori del vestito, layout per la JList */
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

    // --- GETTER E SETTER ---
    /** @return {@code true} se il vestito è indossato, {@code false} altrimenti */
    public boolean isIndossato() {
        return indossato;
    }

    /** @param indossato stato del vestito da impostare */
    public void setIndossato(boolean indossato) {
        this.indossato = indossato;
    }

    /** @return boost energia del vestito */
    public int getBoostEnergia() {
        return boostEnergia;
    }

    /** @param boostEnergia boost energia da impostare */
    public void setBoostEnergia(int boostEnergia) {
        this.boostEnergia = boostEnergia;
    }

    /** @return boost fame del vestito */
    public int getBoostFame() {
        return boostFame;
    }

    /** @param boostFame boost fame da impostare */
    public void setBoostFame(int boostFame) {
        this.boostFame = boostFame;
    }

    /** @return id dell'animale */
    public int getIdAnimale() {
        return idAnimale;
    }

    /** @param idAnimale id dell'animale da impostare */
    public void setIdAnimale(int idAnimale) {
        this.idAnimale = idAnimale;
    }
}
