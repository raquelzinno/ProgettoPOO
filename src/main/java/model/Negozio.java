package model;
import java.util.ArrayList;

public class Negozio {
    private ArrayList<Item> listaItem;

    public Negozio() {
        listaItem = new ArrayList<Item>();
        aggiungiItemDefault(); //ogni volta che verrà istanziato il negozio avrà già degli item di default
    }

    public void aggiungiItem(Item item) {
        listaItem.add(item);
    }

    public void aggiungiItemDefault() { //permette di stabilire gli item di default
        Cibo pizza = new Cibo("Pizza", 2, this, TipoCibo.salato, 4);
        Vestito magliaEmo = new Vestito("Maglia emo", 4, this, 10,9);
        this.aggiungiItem(pizza);
        this.aggiungiItem(magliaEmo);
    }

    public ArrayList<Item> getListaItem() {
        return listaItem;
    }

    public void rimuoviItem(Item item) {
        listaItem.remove(item);
    }

    public String visualizzaItem() {
        String risultato = "";
        for(Item temp: listaItem) {
            risultato += "Nome: " +  temp.getNome() + "     costo: " + temp.getCosto() + "\n";
        }
        return risultato;
    }

}

