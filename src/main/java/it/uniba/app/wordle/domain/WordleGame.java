package it.uniba.app.wordle.domain;

import java.util.Objects;

/**
 * {@literal <<Entity>>} <br>
 * Classe che rappresenta una partita di Wordle.
 * Fornisce metodi per recuperare le impostazioni
 * della partita, nonché la matrice dei tentativi.
 */
class WordleGame {
    private final String secretWord;
    private final Board gameBoard;

    //costruttore con scelta parola e dimensioni del gioco
    WordleGame(final String secretWord,
               final  int numMaxGuesses, final int wordLength) {
        Objects.requireNonNull(secretWord);

        this.secretWord = secretWord;
        this.gameBoard = new Board(wordLength, numMaxGuesses);
    }

    public String getSecretWord() {
        return secretWord;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public int getMaxGuesses() {
        return gameBoard.getRowsNumber();
    }

    public int getWordLength() {
        return gameBoard.getWordLength();
    }

    public int getNumRemainingGuesses() {
        return getMaxGuesses() - gameBoard.getNumFilledRows();
    }
}
