package it.uniba.app.wordle.domain;

/**
 * {@literal <<NoECB>>} <br>
 *  Eccezione lanciata in situazioni che richiedono particolari
 *  precondizioni sulle impostazioni di gioco non rispettate.
 *
 *  <p>Esempio: Precondizione per iniziare una partita e' l'aver
 *  impostato una parola segreta.</p>
 */
public class WordleSettingException extends RuntimeException {

    /** Messaggio per denotare la mancanza della parola segreta. */
    public static final String ABSENT_SECRET_WORD
            = "Parola segreta non impostata";

    WordleSettingException(final String message) {
        super(message);
    }
}
