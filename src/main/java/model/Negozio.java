package model;
import java.util.ArrayList;

public class Negozio {
    private int numeroItem; //usando l'arraylist numeroItem diventerebbe obsoleto, basterebbe usare .size
    private ArrayList<Item> listaItem;  //gemini consiglia di inizializzarlo come List per qualche motivo?

    public Negozio() {  //problema della molteplicità da riguardare possibilmente
        listaItem = new ArrayList<Item>(); //potremmo dover mettere un controllo in caso l'item ritorni null
        numeroItem = listaItem.size();
    }

    public void aggiungiItem(Item item) {
        listaItem.add(item);
        numeroItem = listaItem.size();
    }

    public void rimuoviItem(Item item) {
        listaItem.remove(item);
    }

    public String visualizzaItem() {
        String risultato = "";
        for(Item temp: listaItem) {
            risultato += "Nome: " +  temp.getNome() + "     costo: " + temp.getCosto() + "\n"; //potremmo tranquillamente cambiare il format della stringa
        }
        return risultato;
    }

}

