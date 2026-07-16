package exceptions;

public class ExceptionTroppiAnimali extends RuntimeException {
    /**
     * Crea una nuova istanza di {@link ExceptionTroppiAnimali con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la condizione di animali massimi raggiunti.
     */
    public ExceptionTroppiAnimali(String message) {
        super(message);
    }
}
