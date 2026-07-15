package model;

public abstract class Item {
    private String nome;
    private int costo;
    private Negozio negozio;
    private String iconPath;
    private int idIstanza;

    /**
     * Costruttore per gli item di default presenti nel negozio.
     *
     * @param nome     nome dell'item
     * @param costo    costo dell'item
     * @param negozio  negozio in cui comprare l'item
     * @param iconPath path dell'icona dell'item
     */

    public Item(String nome, int costo, Negozio negozio, String iconPath) {
        this.nome = nome;
        this.costo = costo;
        this.negozio = negozio;
        this.iconPath = iconPath;
        idIstanza = -1;
    }

    /**
     * Costruttore per gli item presenti nell'inventario.
     *
     * @param nome     nome dell'item
     * @param costo    costo dell'item
     * @param negozio  negozio in cui comprare l'item
     * @param iconPath path dell'icona dell'item
     * @param idIstanza l'id dell'istanza dell'item, necessario per la gestione nel database
     */

    public Item(String nome, int costo, Negozio negozio, String iconPath, int idIstanza) {
        this.nome = nome;
        this.costo = costo;
        this.negozio = negozio;
        this.iconPath = iconPath;
        this.idIstanza = idIstanza;
    }

    /**
     * Crea una copia dell'item del negozio da inserire nell'inventario.
     *
     * @param idIstanza l'id dell'istanza dell'item
     * @return l'item
     */
    public abstract Item creaCopia(int idIstanza);

    /** @return nome dell'item */
    public String getNome() {
        return nome;
    }

    /** @param nome nome da impostare */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return costo dell'item */
    public int getCosto() {
        return costo;
    }

    /** @param costo costo da impostare */
    public void setCosto(int costo) {
        this.costo = costo;
    }

    /** @return negozio di appartenenza */
    public Negozio getNegozio() {
        return negozio;
    }

    /** @param negozio negozio da impostare */
    public void setNegozio(Negozio negozio) {
        this.negozio = negozio;
    }

    /** @return icon path dell'item */
    public String getIconPath() {
        return iconPath;
    }

    /** @return id istanza dell'item */
    public int getIdIstanza() { return idIstanza; }

    /** @param idIstanza id istanza da impostare */
    public void setIdIstanza(int idIstanza) {
        this.idIstanza = idIstanza;
    }
}
