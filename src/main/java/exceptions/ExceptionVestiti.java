package exceptions;

public class ExceptionVestiti extends RuntimeException{
    /**
     * Crea una nuova istanza di {@link ExceptionVestiti} con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la causa dell'errore riguardante i vestiti nell'inventario.
     */
    public ExceptionVestiti(String message) {
        super(message);
    }
}
