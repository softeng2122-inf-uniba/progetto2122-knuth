package it.uniba.app.wordle.domain;

/**
 * {@literal <<NoECB>>} <br>
 * L'enumerazione Color contiene le costanti
 * che rappresentano i colori utilizzati nel gioco.
 */
public enum Color {
    /** Valore per celle vuote o appena create. */
    NO_COLOR,

    /** Valore per celle contenenti la lettera corretta
     * (confrontando con la parola segreta). */
    GREEN,

    /** Valore per celle contenenti una lettera presente
     * nella parola segreta ma in un'altra posizione. */
    YELLOW,

    /** Valore per celle contenenti una lettera non presente
     * nella parola segreta. */
    GREY
}
