package it.uniba.app;

/**
 * {@literal <<Entity>>} <br>
 * Classe che rappresenta un tentativo del giocatore.
 * Ogni lettera del tentativo viene memorizzata in un oggetto di {@link LetterBox}.
 */
public class Guess
{
    private final LetterBox[] cellArray;

    /**
     * {@literal <<Entity>>} <br>
     * Classe che rappresenta una singola cella di {@link Board} e incapsula lettera e colore.
     */
    public static class LetterBox
    {
        private final Character letter;
        private Color color;

        LetterBox(char letter)
        {
            this(letter, Color.NO_COLOR);
        }

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

    public Color getColor(int index)
    {
        return cellArray[index].color;
    }

    public void setColor(int index, Color color)
    {
        cellArray[index].color = color;
    }

    public Character getLetter(int index)
    {
        return cellArray[index].letter;
    }

    public String getWord()
    {
        StringBuilder s = new StringBuilder("");
        for (LetterBox lb : cellArray)
            s.append(lb.getLetter());
        return s.toString();
    }

}

