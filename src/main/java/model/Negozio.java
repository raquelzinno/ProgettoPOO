package model;
import java.util.ArrayList;

public class Negozio {
    private ArrayList<Item> listaItem;

    public Negozio(ArrayList<Item> listaItem) {
        this.listaItem = listaItem; //ogni volta che verrà istanziato il negozio avrà già degli item di default
        for (Item item : this.listaItem) { //setta a tutti gli item il negozio attuale
            item.setNegozio(this);
        }
    }

    public ArrayList<Item> getListaItem() {
        return listaItem;
    }
}