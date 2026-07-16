package exceptions;

public class ExceptionAnimale extends RuntimeException {
    /**
     * Crea una nuova istanza di {@link ExceptionAnimale} con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la causa dell'errore riguardante l'animale.
     */
    public ExceptionAnimale(String message) {
        super(message);
    }
}
