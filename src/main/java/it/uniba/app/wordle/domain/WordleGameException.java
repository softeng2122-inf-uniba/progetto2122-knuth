package it.uniba.app.wordle.domain;

import java.util.Objects;

/**
 * {@literal <<NoECB>>} <br>
 * Eccezione lanciata in situazioni che richiedono
 * precondizioni legate allo stato della partita corrente
 * o alla sua esistenza.
 *
 * <p>Esempio: Precondizione per effettuare un tentativo
 * e' l'aver iniziato una partita.</p>
 */
public class WordleGameException extends RuntimeException {

    /** Messaggio per denotare la presenza di una partita gia' in corso. */
    public static final  String EXISTS_GAME = "Partita in corso";

    /** Messaggio per denotare l'assenza di una partita in corso. */
    public static final String NOT_EXISTS_GAME = "Partita inesistente";

    /** Messaggio per denotare l'esaurimento dei tentativi disponibili. */
    public static final String NO_GUESSES_LEFT =
                               "Massimo numero di tentativi raggiunto";

    WordleGameException(final String message) {
        super(Objects.requireNonNull(message));
    }
}
