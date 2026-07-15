package model;
import java.util.ArrayList;

public class Negozio {
    private ArrayList<Item> listaItem;

    /**
     * Crea una nuova istanza di Negozio.
     * Ogni volta che verrà istanziato il negozio avrà già degli item di default,
     * a cui viene settato il negozio attuale come negozio di appartenenza.
     *
     * @param listaItem lista degli item acquistabili nel negozio
     */
    public Negozio(ArrayList<Item> listaItem) {
        this.listaItem = listaItem;
        for (Item item : this.listaItem) {
            item.setNegozio(this);
        }
    }

    /** @return lista item del negozio */
    public ArrayList<Item> getListaItem() {
        return listaItem;
    }
}