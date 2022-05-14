package it.uniba.app;

import java.util.ArrayList;
import java.util.List;

/** Rappresenta un tentativo del giocatore che cerca di indovinare la parola segreta
 *
 */
public class Guess
{
    private final LetterBox[] cellArray;

    /** Rappresenta una cella contenente una lettera del tentativo e il colore associato
     *
     */
    public class LetterBox
    {
        Character letter;
        Color color;

        //costruttore senza argomenti
        LetterBox() {
            this.letter = ' ';
            this.color = Color.NO_COLOR;
        }

        //costruttore con argomenti
        LetterBox(char letter, Color color)
        {
            this.letter = letter;
            this.color = color;
        }
    }

    //costruttore di Guess con argomento il tentativo effettuato
    Guess(String guessingWord)
    {
        cellArray = new LetterBox[guessingWord.length()];
        for (int i = 0; i < guessingWord.length() ; i++)
        {
            cellArray[i] = new LetterBox(guessingWord.charAt(i), Color.NO_COLOR);
        }
    }

}

