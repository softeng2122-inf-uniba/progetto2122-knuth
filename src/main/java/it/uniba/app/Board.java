package it.uniba.app;

/** Questa classe si occupa di rappresentare la griglia di gioco del wordle game
 *
 */
public class Board
{
    private final Guess[] guessArray;
    private final int wordLength;
    private int firstEmptyIndex;

    //costruttore per wordle classico con parola da 5 lettere
    Board()
    {
        this(5 ,6);
    }  // molto probabilmente servirà una costante per questi argomenti

    //costruttore
    // riceve column lunghezza della matrice e row che permette di avere tante righe quanti sono i tentativi
    Board(int column ,int row )
    {
        this.guessArray = new Guess[row];
        this.wordLength = column;
        this.firstEmptyIndex = 0;
    }

    public Guess getGuess(int index)
    {
        return guessArray[index];
    }

    //prende il guess con i colori settati e lo aggiunge alla prima riga libera
    public void acceptNewGuess(Guess g)
    {
        //nota: non ci sono controlli
        guessArray[firstEmptyIndex] = g;
        firstEmptyIndex++;
    }

    public int getRowsNumber()
    {
        return guessArray.length;
    }

    public int getWordLength()
    {
        return wordLength;
    }

    public int getNumFilledRows()
    {
        return firstEmptyIndex;
    }
}
