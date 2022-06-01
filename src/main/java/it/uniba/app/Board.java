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

    //costruttore
    // riceve column lunghezza della matrice e row che permette
    // di avere tante righe quanti sono i tentativi
    Board(final int column, final int row) {
        this.guessArray = new Guess[row];
        this.wordLength = column;
        this.firstEmptyIndex = 0;
    }

    public Guess getGuess(final int index) {
        return guessArray[index];
    }

    //prende il guess con i colori settati e lo aggiunge alla prima riga libera
    public void acceptNewGuess(final Guess g) {
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
