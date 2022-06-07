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

    public static final String ABSENT_SECRET_WORD = "Parola segreta non impostata";

    WordleSettingException(final String message) {
        super(message);
    }
}
