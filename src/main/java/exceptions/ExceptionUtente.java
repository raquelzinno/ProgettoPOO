package exceptions;

public class ExceptionUtente extends RuntimeException{
    /**
     * Crea una nuova istanza di {@link ExceptionUtente} con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la causa dell'errore riguardante l'utente.
     */
    public ExceptionUtente(String message) {
        super(message);
    }
}
