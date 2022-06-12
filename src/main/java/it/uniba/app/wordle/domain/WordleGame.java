package it.uniba.app.wordle.domain;

import java.util.Objects;

/**
 * {@literal <<Entity>>} <br>
 * Classe che rappresenta una partita di Wordle.
 */
class WordleGame {
    /** Parola segreta della partita. */
    private final String secretWord;
    /** Board associata alla partita. */
    private final Board gameBoard;

    /**
     * Crea una partita con le impostazioni specificate.
     *
     * @param gameSecretWord parola segreta
     * @param numMaxGuesses tentativi massimi
     * @param wordLength numero di lettere
     * @throws NullPointerException se {@code gameSecretWord} e' null
     */
    WordleGame(final String gameSecretWord,
               final int numMaxGuesses,
               final int wordLength) {
        Objects.requireNonNull(gameSecretWord);

        this.secretWord = gameSecretWord;
        this.gameBoard = new Board(numMaxGuesses, wordLength);
    }

    String getSecretWord() {
        return secretWord;
    }

    Board getGameBoard() {
        return gameBoard;
    }

    int getMaxGuesses() {
        return gameBoard.getRowsNumber();
    }

    int getWordLength() {
        return gameBoard.getWordLength();
    }

    int getNumRemainingGuesses() {
        return getMaxGuesses() - gameBoard.getNumFilledRows();
    }
}
