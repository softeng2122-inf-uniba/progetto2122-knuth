package it.uniba.app;

/**
 * {@literal <<NoECB>>} <br>
 * Eccezione lanciata in situazioni che richiedono come precondizione
 * l'esistenza o la non esistenza della partita, nelle quali essa non
 * è rispettata.
 *
 * Esempio: Precondizione per effettuare un tentativo è l'aver
 * iniziato una partita.
 */
public class WordleGameException extends RuntimeException {
    WordleGameException(String message) {
        super(message);
    }
}
