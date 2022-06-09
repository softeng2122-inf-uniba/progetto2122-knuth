package it.uniba.app.wordle.domain;

import java.util.Objects;

/**
 * {@literal <<Entity>>}<br>
 * Classe che rappresenta la matrice dei tentativi di una partita, la quale
 * contiene i tentativi validi inseriti dall'utente segnando i colori
 * di ogni lettera.
 */
class Board {
    /** Array che contiene i tentativi secondo ordine d'inserimento. */
    private final Guess[] guessArray;
    /** Numero di lettere delle parole che costituiscono i tentativi. */
    private final int wordLength;
    /** Indice del primo elemento vuoto di {@code guessArray}. */
    private int firstEmptyIndex;

    // row -> numero di tentativi, column -> numero di lettere
    Board(final int row, final int column) {

        if (row <= 0 || column <= 0) {
            throw new IllegalArgumentException();
        }

        this.guessArray = new Guess[row];
        this.wordLength = column;
        this.firstEmptyIndex = 0;
    }

    Guess getGuess(final int index) {
        return guessArray[index];
    }

    /**
     * Aggiunge un tentativo alla prima riga libera della board.
     *
     * @param g tentativo da inserire
     * @throws NullPointerException se g è {@code null}
     */
    void acceptNewGuess(final Guess g) {
        Objects.requireNonNull(g);

        guessArray[firstEmptyIndex] = g;
        firstEmptyIndex++;
    }

    int getRowsNumber() {

        return guessArray.length;
    }

    int getWordLength() {

        return wordLength;
    }

    int getNumFilledRows() {

        return firstEmptyIndex;
    }
}
