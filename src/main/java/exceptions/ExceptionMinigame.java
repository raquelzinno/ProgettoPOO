package exceptions;

public class ExceptionMinigame extends RuntimeException{
    /**
     * Crea una nuova istanza di {@link ExceptionMinigame} con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la causa dell'errore durante l'avvio di un minigame.
     */
    public ExceptionMinigame(String message) {
        super(message);
    }
}
