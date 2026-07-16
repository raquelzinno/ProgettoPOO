package exceptions;

public class ExceptionFame extends RuntimeException {
    /**
     * Crea una nuova istanza di {@link ExceptionFame} con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la causa dell'errore riguardante la fame dell'animale.
     */
    public ExceptionFame(String message) {
        super(message);
    }
}
