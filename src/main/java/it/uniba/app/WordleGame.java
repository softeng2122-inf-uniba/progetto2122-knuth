package it.uniba.app;

/**
 * {@literal <<Entity>>} <br>
 * Classe che rappresenta una partita di Wordle.
 * Fornisce metodi per recuperare le impostazioni
 * della partita, nonché la matrice dei tentativi.
 */
public class WordleGame {
    private final String secretWord;
    private final Board gameBoard;

    //costruttore
    WordleGame(String secretWord) {
        this(secretWord, 5, 6);
    }

    //costruttore con scelta parola e dimensioni del gioco
    WordleGame(String secretWord, int column, int row) {
        this.secretWord = secretWord;
        this.gameBoard = new Board(column, row);
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
