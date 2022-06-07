package it.uniba.app.wordle.domain;

import java.util.Objects;

/**
 * {@literal <<NoECB>>} <br>
 * Eccezione lanciata in situazioni che richiedono
 * come precondizione l'esistenza o la non esistenza
 * della partita, nelle quali essa non è rispettata.
 *
 * Esempio: Precondizione per effettuare un tentativo
 * è l'aver iniziato una partita.
 */
public class WordleGameException extends RuntimeException {

    public static final  String EXISTS_GAME = "Partita in corso";
    public static final String NOT_EXISTS_GAME = "Partita inesistente";
    public static final String NO_GUESSES_LEFT = "Massimo numero di tentativi raggiunto";

    WordleGameException(final String message) {
        super(Objects.requireNonNull(message));
    }
}
