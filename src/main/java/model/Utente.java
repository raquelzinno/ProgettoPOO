package model;
import java.util.ArrayList;

public class Utente {
    private final String login;
    private String password;
    private boolean accessoEffettuato;  //dovendo gestire gli account, penso sia necessaria questa variabile per stabilire che l'accesso è avvenuto
    private ArrayList<Animale> animaliPosseduti;
    private ArrayList<Item> itemPosseduti;

    /**
     * Instantiates a new Utente.
     *
     * @param login    the login
     * @param password the password
     */
    public Utente(String login, String password) {
        this.login = login;
        this.password = password;
        animaliPosseduti = new ArrayList<Animale>();
        itemPosseduti = new ArrayList<Item>();
        accessoEffettuato = false;
    }
    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    public void disconnettiAccount() {
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
    }

    public void creaAnimale(Animale animale) {
        if(accessoEffettuato) {
            if (animaliPosseduti.size() == 2) {
                System.out.println("Hai già il massimo di animali consentiti"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
            } else {
                animale.setUtente(this);
                animaliPosseduti.add(animale);
            }
        }
        else {
            System.out.println("non hai ancora effettuato l'accesso");
        }
    }    //in teoria per tutti i metodi successivi dovremmo aggiungere il controllo se l'accesso è stato effettuato, però mi scocciavo di aggiungerlo for now :)

    public void eliminaAnimale(Animale animale) {
        animaliPosseduti.remove(animale);
    }

    public void modificaNome(String nome,Animale animale) {
        animale.setNome(nome);
    }

    public void compraItem(Item item,Animale animale)
    {
        if(animale.getPunti() < item.getCosto())
        {
            System.out.println("L'animale non ha abbastanza punti!"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
        }
        else
        {
            animale.setPunti(animale.getPunti()- item.getCosto()); //ricalcolo dei punti dopo la spesa
            itemPosseduti.add(item);  //aggiungo l'item alla lista dell'utente
        }
    }

    public void daiCibo(Cibo cibo,Animale animale) {
        if(itemPosseduti.contains(cibo)) {  //vedo se il cibo è nell'inventario
            animale.setFame(animale.getFame() + cibo.getPuntiFame());   //ricalcolo il livello di fame
            if(animale.getFame() > animale.getFameMax()) //nel caso in cui il nuovo livello di fame eccede il massimo, viene ricalcolato per essere uguale al massimo
            {
                animale.setFame(animale.getFameMax());
            }
            itemPosseduti.remove(cibo);  //l'item viene rimosso dall'inventario
        }
        else {
            System.out.println("Non possiedi questo Item!"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
        }
    }

    public void vesti(Vestito vestito,Animale animale) {
        if(itemPosseduti.contains(vestito)) {  //prima controllo se possiedo i vestiti nei miei item
            if(vestito.isIndossato()) {  //controllo se già lo sto indossando
                System.out.println("Già stai indossando questo vestito!"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
            }
            else {
                if((animale.getVestititIndossati()).size() == 2) { //controllo se sto indossando il massimo di vestiti consentiti
                    System.out.println("Non puoi indossare più di due vestiti!"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
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
            System.out.println("Non possiedi questo Item!"); //il println è per una prova, dovremmo gestire questa situazione in un'altra maniera
        }
    }

    public void selezionaAnimale() {}  //idk what to do about this tbh

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
