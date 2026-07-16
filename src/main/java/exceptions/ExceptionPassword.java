package exceptions;

public class ExceptionPassword extends RuntimeException{
    /**
     * Crea una nuova istanza di {@link ExceptionPassword} con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la causa dell'errore di autenticazione o validazione.
     */
    public ExceptionPassword(String message) {
        super(message);
    }
}
