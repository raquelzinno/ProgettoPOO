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

    /**
     * Crea una nuova istanza di utente.
     * Le liste degli item e degli animali posseduti sono vuote inizialmente.
     * L'accesso effettuato è falso di default
     *
     * @param login    nome utente univoco
     * @param password password dell'utente
     */
    public Utente(String login, String password) {
        this.login = login;
        this.password = password;
        animaliPosseduti = new ArrayList<Animale>();
        itemPosseduti = new ArrayList<Item>();
        accessoEffettuato = false;
    }

    /**
     * Inserisce l'animale creato nella lista degli animali posseduti dall'utente.
     *
     * @param animale l'animale creato dall'utente
     */
    public void creaAnimale(Animale animale) {
        animaliPosseduti.add(animale);
    }

    /**
     * Elimina l'animale selezionato dalla lista degli animali posseduti dall'utente.
     *
     * @param animale l'animale selezionato dall'utente
     */
    public void eliminaAnimale(Animale animale) {
        animaliPosseduti.remove(animale);
    }

    /**
     * Compra un item e lo inserisce nella lista degli item posseduti dall'utente,
     * poi ricalcola i punti dell'animale.
     *
     * @param item      l'item comprato
     * @param animale   l'animale a cui appartiene l'item comprato
     * @param idIstanza l'id dell'istanza dell'item comprato, necessario per la gestione nel database
     * @throws RuntimeException se l'animale non ha abbastanza punti per comprare l'item
     */
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

    /**
     * Rimuove il cibo selezionato dalla lista degli item posseduti dall'utente
     * e ricalcola il parametro della fame dell'animale.
     *
     * @param cibo    il cibo selezionato
     * @param animale l'animale a cui si vuole dare il cibo
     * @throws RuntimeException se la fame dell'animale è al massimo, non si può dare da mangiare
     */
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

    /**
     * Aggiunge il vestito selezionato alla lista di vestiti indossati dall'animale
     * e modifica i valori dell'animale nel caso in cui il vestito possegga dei boost.
     *
     * @param vestito il vestito selezionato da far indossare all'animale
     * @param animale l'animale da vestire
     * @return {@code true} se il vestito è stato indossato correttamente, {@code false} altrimenti
     * @throws RuntimeException se l'animale non possiede il vestito, se lo sta già indossando o se ne sta già indossando due
     */
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

                    //vengono settati i nuovi livelli max in base ai valori boost
                    animale.setEnergiaMax(animale.getEnergiaMax() + vestito.getBoostEnergia());
                    animale.setFameMax(animale.getFameMax() + vestito.getBoostFame());

                    vestito.setIndossato(true);
                    return true;
                }
            }
        }
        else {
            throw new ExceptionVestiti("Non possiedi questo Item!");
        }
    }

    /**
     * Elimina l'item selezionato dall'inventario dell'utente.
     *
     * @param item l'item da eliminare
     */
    public void eliminaItem(Item item) {
        itemPosseduti.remove(item);
    }

    /**
     * Rimuove il vestito selezionato dalla lista dei vestiti indossati dall'animale.
     * Rimuove i boost applicati e gestisce i nuovi valori max dei parametri dell'animale.
     *
     * @param vestito il vestito da rimuovere
     * @param animale l'animale a cui rimuovere il vestito
     * @return {@code true} se il vestito è stato rimosso correttamente, {@code false} altrimenti
     */
    public boolean rimuoviVestito(Vestito vestito, Animale animale){
        if(itemPosseduti.contains(vestito)) {  //prima controllo se possiedo i vestiti nei miei item

            if (vestito.isIndossato()) {
                (animale.getVestitiIndossati()).remove(vestito);  //il vestito viene rimosso all'animale

                //vengono settati i nuovi livelli max in base ai valori boost
                animale.setEnergiaMax(animale.getEnergiaMax() - vestito.getBoostEnergia());
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

    // --- GETTER E SETTER ---

    /** @return il login dell'utente */
    public String getLogin() {
        return login;
    }

    /** @return la password dell'utente */
    public String getPassword() {
        return password;
    }

    /** @param password la password da impostare */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @return {@code true} se l'accesso è stato effettuato, {@code false} altrimenti */
    public boolean isAccessoEffettuato() {
        return accessoEffettuato;
    }

    /** @param accessoEffettuato stato da impostare dell'accesso */
    public void setAccessoEffettuato(boolean accessoEffettuato) {
        this.accessoEffettuato = accessoEffettuato;
    }

    /** @return lista degli animali posseduti */
    public ArrayList<Animale> getAnimaliPosseduti() {
        return animaliPosseduti;
    }

    /** @param animaliPosseduti lista degli animali posseduti da impostare */
    public void setAnimaliPosseduti(ArrayList<Animale> animaliPosseduti) {
        this.animaliPosseduti = animaliPosseduti;
    }

    /** @return lista degli items posseduti */
    public ArrayList<Item> getItemPosseduti() {
        return itemPosseduti;
    }

    /** @param itemPosseduti lista degli items posseduti da impostare */
    public void setItemPosseduti(ArrayList<Item> itemPosseduti) {
        this.itemPosseduti = itemPosseduti;
    }
}
