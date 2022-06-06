package it.uniba.app.wordle.domain;

import java.util.Objects;

/**
 * {@literal <<NoECB>>} <br>
 *  Eccezione lanciata in situazioni che richiedono particolari
 *  precondizioni sulle impostazioni di gioco, nelle quali esse
 *  non sono rispettate.
 *  <p></p>
 *
 *  Esempio: Precondizione per iniziare una partita è l'aver
 *  impostato una parola segreta.
 */
public class WordleSettingException extends RuntimeException {

    private Motivation motivation;

    public enum Motivation {
        ABSENT_SECRET_WORD() {
            public String getMessage() {
                return "Parola segreta non impostata";
            }
        };

        public abstract String getMessage();
    }

    WordleSettingException(final String message) {
        super(Objects.requireNonNull(message));
        motivation = null;
    }

    WordleSettingException(final Motivation motivation) {
        this(Objects.requireNonNull(motivation.getMessage()));
        this.motivation = motivation;
    }
}
