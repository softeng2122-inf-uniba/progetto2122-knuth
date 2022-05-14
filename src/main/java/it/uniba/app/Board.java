package it.uniba.app;

/** Questa classe si occupa di rappresentare la griglia di gioco del wordle game
 *
 */
public class Board
{
    private final Guess[] guessArray;
    private final int wordLength;

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
    }
}
