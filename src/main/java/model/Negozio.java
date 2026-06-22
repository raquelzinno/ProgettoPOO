package model;
import java.util.ArrayList;

public class Negozio {
    private ArrayList<Item> listaItem;

    public Negozio(ArrayList<Item> listaItem) {
        this.listaItem = listaItem; //ogni volta che verrà istanziato il negozio avrà già degli item di default
        for (Item item : this.listaItem) { //setta a tutti gli item il negozio attuale
            item.setNegozio(this);
        }
        //aggiungiItemDefault(); //ogni volta che verrà istanziato il negozio avrà già degli item di default
    }

    public void aggiungiItem(Item item) {
        listaItem.add(item);
    }

    public void aggiungiItemDefault() { //permette di stabilire gli item di default
        //CIBO ---------------------------------------------------------------------------------------
        Cibo pizza = new Cibo("Pizza", 3, this, TipoCibo.salato, 4, "img/pizza.png");
        Cibo salmone = new Cibo("Salmone", 5, this, TipoCibo.salato, 8, "img/salmone.png");
        Cibo broccolo = new Cibo("Broccolo", 2, this, TipoCibo.salato, 3, "img/broccolo.png");
        Cibo pasta = new Cibo("Pasta al sugo", 4, this, TipoCibo.salato, 5, "img/pasta.png");

        Cibo torta = new Cibo("Torta al cioccolato", 3, this, TipoCibo.dolce, 4,"img/torta.png");
        Cibo mela = new Cibo("Mela", 2, this, TipoCibo.dolce, 3,"img/mela.png");
        Cibo baba = new Cibo("Babà", 5, this, TipoCibo.dolce, 8,"img/baba.png");

        Cibo acqua = new Cibo("Acqua", 1, this, TipoCibo.bevanda, 1,"img/acqua.png");
        Cibo cocacola = new Cibo("CocaCola", 2, this, TipoCibo.bevanda, 2,"img/cocacola.png");
        Cibo monster = new Cibo("Monster", 2, this, TipoCibo.bevanda, 2,"img/monster.png");

        //VESTITI ---------------------------------------------------------------------------------------
        Vestito magliaNapoli = new Vestito("Maglia del Napoli", 30, this, 25,0,"img/magliaNapoli.png");
        Vestito pantaloniSkinny = new Vestito("Pantaloni skinny", 30, this, 10, 20, "img/pantaloniSkinny.png");
        Vestito scarpeGinanstica = new Vestito("Scarpe da ginnastica", 30, this, 20, 0, "img/scarpeGinnastica.png");
        Vestito bavaglino = new Vestito("Bavaglino", 20, this, 0, 15, "img/bavaglino.png");
        Vestito laurea = new Vestito("Cappello da laurea", 40, this, 25, 25, "img/cappelloLaurea.png");
        Vestito corona = new Vestito("Corona", 100, this, 50, 50, "img/corona.png");

        //aggiungo gli item
        this.aggiungiItem(pizza);
        this.aggiungiItem(salmone);
        this.aggiungiItem(broccolo);
        this.aggiungiItem(pasta);
        this.aggiungiItem(torta);
        this.aggiungiItem(mela);
        this.aggiungiItem(baba);
        this.aggiungiItem(acqua);
        this.aggiungiItem(cocacola);
        this.aggiungiItem(monster);

        this.aggiungiItem(magliaNapoli);
        this.aggiungiItem(pantaloniSkinny);
        this.aggiungiItem(scarpeGinanstica);
        this.aggiungiItem(bavaglino);
        this.aggiungiItem(laurea);
        this.aggiungiItem(corona);
    }

    public ArrayList<Item> getListaItem() {
        return listaItem;
    }

}