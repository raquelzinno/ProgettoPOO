package exceptions;

public class ExceptionPuntiNonSufficienti extends RuntimeException {
    /**
     * Crea una nuova istanza di {@link ExceptionPuntiNonSufficienti} con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la causa dell'errore durante un acquisto.
     */
    public ExceptionPuntiNonSufficienti(String message) {
        super(message);
    }
}
