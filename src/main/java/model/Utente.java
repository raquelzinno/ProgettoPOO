package model;
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

    /*public void disconnettiAccount() {
        if(accessoEffettuato) {
            accessoEffettuato = false;
        }
        else {
            System.out.println("non hai ancora effettuato l'accesso"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
        }
    }

    public void faiLogin (String login, String password) {
        if((this.login).equals(login) && (this.password).equals(password)) {
            accessoEffettuato = true;
        }
        else
        {
            System.out.println("Nome utente o password sbagliata"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
        }
    }

    public void cambiaPassword(String vecchiaPassword,String nuovaPassword) {
        if(accessoEffettuato) {
            if((this.password).equals(vecchiaPassword)) {
                this.password = nuovaPassword;
            }
            else {
                System.out.println("Password sbagliata"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
            }
        }
        else {
            System.out.println("non hai ancora effettuato l'accesso");
        }
    }*/

    public void creaAnimale(Animale animale) {
        animaliPosseduti.add(animale);
        /*if(accessoEffettuato) {
            if (animaliPosseduti.size() == 2) {
                System.out.println("Hai già il massimo di animali consentiti"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
            } else {
                animale.setUtente(this);
                animaliPosseduti.add(animale);
            }
        }
        else {
            System.out.println("non hai ancora effettuato l'accesso");
        }*/

    }

    public void eliminaAnimale(Animale animale) {
        animaliPosseduti.remove(animale);
    }

    public void modificaNome(String nome,Animale animale) {
        animale.setNome(nome);
    }

    public void compraItem(Item item, Animale animale) {
        if(animale.getPunti() < item.getCosto()) {  //posso comprare l'item solo se ho abbastanza punti
            System.out.println("L'animale non ha abbastanza punti!");
        }
        else
        {
            animale.setPunti(animale.getPunti()- item.getCosto()); //ricalcolo dei punti dopo la spesa
            Item copiaAcquistata = item.creaCopia();  //quando faccio un acquisto viene creata una copia dell'oggetto del negozio
            itemPosseduti.add(copiaAcquistata);  //aggiungo la copia agli item posseduti
        }
    }

    public void daiCibo(Cibo cibo, Animale animale) {
        if(itemPosseduti.contains(cibo)) {  //vedo se il cibo è nell'inventario
            animale.setFame(animale.getFame() + cibo.getPuntiFame());   //ricalcolo il livello di fame
            if(animale.getFame() > animale.getFameMax()) //nel caso in cui il nuovo livello di fame eccede il massimo, viene ricalcolato per essere uguale al massimo
            {
                animale.setFame(animale.getFameMax());
            }
            itemPosseduti.remove(cibo);  //l'item viene rimosso dall'inventario
        }
    }

    public void vesti(Vestito vestito,Animale animale) throws RuntimeException {
        if(itemPosseduti.contains(vestito)) {  //prima controllo se possiedo i vestiti nei miei item
            if(vestito.isIndossato()) {  //controllo se già lo sto indossando
                throw new ExceptionVestiti("Stai già indossando questo vestito!");
            }
            else {
                if((animale.getVestititIndossati()).size() == 2) { //controllo se sto indossando il massimo di vestiti consentiti
                    throw new ExceptionVestiti("Non puoi indossare più di due vestiti!");
                }
                else {
                    (animale.getVestititIndossati()).add(vestito);  //il vestito viene aggiunto all'animale
                    animale.setEnergiaMax(animale.getEnergiaMax() + vestito.getBoostEnergia());  //vengono settati i nuovi livelli max in base ai valori boost
                    animale.setFameMax(animale.getFameMax() + vestito.getBoostFame());
                    vestito.setIndossato(true);
                }
            }
        }
        else {
            throw new ExceptionVestiti("Non possiedi questo Item!");
            //System.out.println("Non possiedi questo Item!"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
        }
    }

    public void rimuoviVestito(Vestito vestito, Animale animale){
        if(itemPosseduti.contains(vestito)) {  //prima controllo se possiedo i vestiti nei miei item
            if (vestito.isIndossato()) {
                (animale.getVestititIndossati()).remove(vestito);  //il vestito viene rimosso all'animale
                animale.setEnergiaMax(animale.getEnergiaMax() - vestito.getBoostEnergia());  //vengono settati i nuovi livelli max in base ai valori boost
                animale.setFameMax(animale.getFameMax() - vestito.getBoostFame());
                vestito.setIndossato(false);
                if (animale.getFame() > animale.getFameMax()) //se il livello di fame supera il nuovo max, viene settato al max
                    animale.setFame(animale.getFameMax());
                if (animale.getEnergia() > animale.getEnergiaMax())
                    animale.setEnergia(animale.getEnergiaMax()); //se il livello di energia supera il nuovo max, viene settato al max
            }
        }
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
