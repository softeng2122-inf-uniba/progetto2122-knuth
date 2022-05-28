package it.uniba.app;

/**
 * {@literal <<Entity>>} <br>
 * Classe che rappresenta la matrice dei tentativi di dimensioni 5 righe per 6
 * colonne che contiene i tentativi validi inseriti dall'utente. <br>
 * Questa classe contiene metodi che forniscono informazioni sulla griglia e
 * per l'inserimento di nuovi tentativi.
 */
public class Board {
    private final Guess[] guessArray;
    private final int wordLength;
    private int firstEmptyIndex;
    private static final int setColumn = 5;
    private static final int setRow = 6;

    //costruttore per wordle classico con parola da 5 lettere
    Board() {
        this(setColumn, setRow);
    }  // molto probabilmente servirà una costante per questi argomenti

    //costruttore
    // riceve column lunghezza della matrice e row che permette
    // di avere tante righe quanti sono i tentativi
    Board(int column, int row) {
        this.guessArray = new Guess[row];
        this.wordLength = column;
        this.firstEmptyIndex = 0;
    }

    public Guess getGuess(int index)
    {
        return guessArray[index];
    }

    //prende il guess con i colori settati e lo aggiunge alla prima riga libera
    public void acceptNewGuess(Guess g) {
        guessArray[firstEmptyIndex] = g;
        firstEmptyIndex++;
    }

    public int getRowsNumber() {
        return guessArray.length;
    }

    public int getWordLength() {
        return wordLength;
    }

    public int getNumFilledRows() {
        return firstEmptyIndex;
    }
}
