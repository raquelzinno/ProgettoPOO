package model;
import exceptions.ExceptionFame;
import exceptions.ExceptionPuntiNonSufficienti;
import exceptions.ExceptionVestiti;

import java.util.ArrayList;

public class Utente {
    private final String login;
    private String password;
    private boolean accessoEffettuato;
    private ArrayList<Animale> animaliPosseduti;
    private ArrayList<Item> itemPosseduti;

    public Utente(String login, String password) {
        this.login = login;
        this.password = password;
        animaliPosseduti = new ArrayList<Animale>();
        itemPosseduti = new ArrayList<Item>();
        accessoEffettuato = false;
    }

    public String getLogin() {
        return login;
    }

    public void creaAnimale(Animale animale) {
        animaliPosseduti.add(animale);
    }

    public void eliminaAnimale(Animale animale) {
        animaliPosseduti.remove(animale);
    }

    public void compraItem(Item item, Animale animale, int idIstanza) throws RuntimeException {
        if(animale.getPunti() < item.getCosto()) {  //posso comprare l'item solo se ho abbastanza punti
            throw new ExceptionPuntiNonSufficienti("L'animale non ha abbastanza punti!");
        }
        else
        {
            animale.setPunti(animale.getPunti()- item.getCosto()); //ricalcolo dei punti dopo la spesa
            Item copiaAcquistata = item.creaCopia(idIstanza);  //quando faccio un acquisto viene creata una copia dell'oggetto del negozio
            itemPosseduti.add(copiaAcquistata);  //aggiungo la copia agli item posseduti
        }
    }

    public void daiCibo(Cibo cibo, Animale animale) throws RuntimeException{
        if(animale.getFame() == animale.getFameMax()) throw new ExceptionFame(animale.getNome() + " non ha fame.");

        if(itemPosseduti.contains(cibo)) {  //vedo se il cibo è nell'inventario
            animale.setFame(animale.getFame() + cibo.getPuntiFame());   //ricalcolo il livello di fame
            if(animale.getFame() > animale.getFameMax()) //nel caso in cui il nuovo livello di fame eccede il massimo, viene ricalcolato per essere uguale al massimo
            {
                animale.setFame(animale.getFameMax());
            }
            itemPosseduti.remove(cibo);  //l'item viene rimosso dall'inventario
        }
    }

    public boolean vesti(Vestito vestito,Animale animale) throws RuntimeException {
        if(itemPosseduti.contains(vestito)) {  //prima controllo se possiedo i vestiti nei miei item
            if(vestito.isIndossato()) {  //controllo se già lo sto indossando
                throw new ExceptionVestiti("Stai già indossando questo vestito!");
            }
            else {
                if((animale.getVestitiIndossati()).size() == 2) { //controllo se sto indossando il massimo di vestiti consentiti
                    throw new ExceptionVestiti("Non puoi indossare più di due vestiti!");
                }
                else {
                    (animale.getVestitiIndossati()).add(vestito);  //il vestito viene aggiunto all'animale
                    animale.setEnergiaMax(animale.getEnergiaMax() + vestito.getBoostEnergia());  //vengono settati i nuovi livelli max in base ai valori boost
                    animale.setFameMax(animale.getFameMax() + vestito.getBoostFame());
                    vestito.setIndossato(true);
                    return true;
                }
            }
        }
        else {
            throw new ExceptionVestiti("Non possiedi questo Item!");
            //System.out.println("Non possiedi questo Item!"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
        }
    }

    public void eliminaItem(Item item) {
        itemPosseduti.remove(item);
    }

    public boolean rimuoviVestito(Vestito vestito, Animale animale){
        if(itemPosseduti.contains(vestito)) {  //prima controllo se possiedo i vestiti nei miei item
            if (vestito.isIndossato()) {
                (animale.getVestitiIndossati()).remove(vestito);  //il vestito viene rimosso all'animale
                animale.setEnergiaMax(animale.getEnergiaMax() - vestito.getBoostEnergia());  //vengono settati i nuovi livelli max in base ai valori boost
                animale.setFameMax(animale.getFameMax() - vestito.getBoostFame());
                vestito.setIndossato(false);
                if (animale.getFame() > animale.getFameMax()) //se il livello di fame supera il nuovo max, viene settato al max
                    animale.setFame(animale.getFameMax());
                if (animale.getEnergia() > animale.getEnergiaMax())
                    animale.setEnergia(animale.getEnergiaMax()); //se il livello di energia supera il nuovo max, viene settato al max
                return true;
            }
        }
        return false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccessoEffettuato() {
        return accessoEffettuato;
    }

    public void setAccessoEffettuato(boolean accessoEffettuato) {
        this.accessoEffettuato = accessoEffettuato;
    }

    public ArrayList<Animale> getAnimaliPosseduti() {
        return animaliPosseduti;
    }

    public void setAnimaliPosseduti(ArrayList<Animale> animaliPosseduti) {
        this.animaliPosseduti = animaliPosseduti;
    }

    public ArrayList<Item> getItemPosseduti() {
        return itemPosseduti;
    }

    public void setItemPosseduti(ArrayList<Item> itemPosseduti) {
        this.itemPosseduti = itemPosseduti;
    }
}
