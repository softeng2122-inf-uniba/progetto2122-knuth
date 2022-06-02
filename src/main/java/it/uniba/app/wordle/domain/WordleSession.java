package it.uniba.app.wordle.domain;


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
    private int nMaxGuesses;
    private int wordLength;

    private final static int DEFAULT_MAX_GUESSES = 6;
    private final static int DEFAULT_WORD_LENGTH = 5;

    public WordleSession() {
        currentGame = null;
        secretWord = null;
        nMaxGuesses = DEFAULT_MAX_GUESSES;
        wordLength = DEFAULT_WORD_LENGTH;
    }

    public WordleGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(WordleGame currentGame) {
        this.currentGame = currentGame;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public int getnMaxGuesses() {
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