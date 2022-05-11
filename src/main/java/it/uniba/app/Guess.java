package it.uniba.app;

import java.util.ArrayList;
import java.util.List;

/** Rappresenta un tentativo del giocatore che cerca di indovinare la parola segreta
 *
 */
public class Guess
{
    List<LetterBox> cellArray;

    /** Rappresenta una cella contenente una lettera del tentativo e il colore associato
     *
     */
    public class LetterBox
    {
        Character letter;
        String color;
    }

}

