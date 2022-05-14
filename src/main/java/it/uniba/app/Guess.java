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
        private Character letter;
        private Color color;

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

        public Color getColor()
        {
            return color;
        }

        public void setColor(Color color)
        {
            this.color = color;
        }

        public Character getLetter()
        {
            return letter;
        }

        public void setLetter(Character letter)
        {
            this.letter = letter;
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

    public void setColor(int index, Color color)
    {
        cellArray[index].setColor(color);
    }

    public Color getColor(int index)
    {
        return cellArray[index].getColor();
    }

    public void setLetter(int index, char letter)
    {
        cellArray[index].setLetter(letter);
    }

    public char getLetter(int index)
    {
        return cellArray[index].getLetter();
    }

}

