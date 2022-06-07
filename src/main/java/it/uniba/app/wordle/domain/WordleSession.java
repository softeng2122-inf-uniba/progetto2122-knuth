package it.uniba.app.wordle.domain;

import java.util.Objects;

/**
 * {@literal <<Control>>} <br>
 * Classe che implementa la logica del gioco: opera su oggetti delle
 * classi di tipo Entity, coerentemente con le regole di Wordle.
 * <p></p>
 * Fornisce una API (Application Programming Interface) indipendente
 * dallo strato di User Interface, il quale richiamerà i suoi servizi,
 * effettuando controlli sulla legalità delle chiamate.
 */
 class WordleSession {

    private WordleGame currentGame;

    // impostazioni di gioco
    private String secretWord;

    // non final, si potrebbero far modificare dal giocatore in release future
    private int nMaxGuesses;
    private int wordLength;

    private static final int DEFAULT_MAX_GUESSES = 6;
    private static final int DEFAULT_WORD_LENGTH = 5;

    WordleSession() {
        currentGame = null;
        secretWord = null;
        nMaxGuesses = DEFAULT_MAX_GUESSES;
        wordLength = DEFAULT_WORD_LENGTH;
    }

    public WordleGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(final WordleGame game) {
        this.currentGame = game;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(final String gameSecretWord) {
        Objects.requireNonNull(gameSecretWord);

        this.secretWord = gameSecretWord;
    }

    public int getNMaxGuesses() {
        return nMaxGuesses;
    }

    public int getWordLength() {
        return wordLength;
    }

    public boolean hasSecretWord() {
        return secretWord != null;
    }

    public boolean isGameRunning() {
        return currentGame != null;
    }
}
