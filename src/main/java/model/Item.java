package model;

public abstract class Item {
    private String nome;
    private int costo;
    private Negozio negozio;
    private String iconPath;
    private int idIstanza;

    //costruttore per gli item di default presenti nel negozio
    public Item(String nome, int costo, Negozio negozio, String iconPath) {
        this.nome = nome;
        this.costo = costo;
        this.negozio = negozio;
        this.iconPath = iconPath;
        idIstanza = -1;
    }

    //costruttore per gli item presenti nell'inventario
    public Item(String nome, int costo, Negozio negozio, String iconPath, int idIstanza) {
        this.nome = nome;
        this.costo = costo;
        this.negozio = negozio;
        this.iconPath = iconPath;
        this.idIstanza = idIstanza;
    }

    public abstract Item creaCopia(int idIstanza); //il creaCopia ha un comportamento concreto nelle classi che estendono Item

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

    public String getIconPath() {
        return iconPath;
    }

    public int getIdIstanza() { return idIstanza; }

    public void setIdIstanza(int idIstanza) {
        this.idIstanza = idIstanza;
    }
}
