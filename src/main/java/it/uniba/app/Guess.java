package it.uniba.app;

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
        private final Character letter;
        private Color color;

        //costruttore senza argomenti
        LetterBox(char letter)
        {
            this(letter, Color.NO_COLOR);
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

    public LetterBox getLetterBox(int index)
    {
        return cellArray[index];
    }

}

