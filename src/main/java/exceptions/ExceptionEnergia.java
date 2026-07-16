package exceptions;

public class ExceptionEnergia extends RuntimeException {
    /**
     * Crea una nuova istanza di {@link ExceptionEnergia} con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la causa dell'errore riguardante l'energia dell'animale.
     */
    public ExceptionEnergia(String message) {
        super(message);
    }
}
