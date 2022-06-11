package it.uniba.app.wordle.domain;

import java.util.Objects;

/**
 * {@literal <<Entity>>} <br>
 * Classe che rappresenta una sessione di gioco, in cui in ogni momento può
 * essere in corso una sola partita o nessuna.
 *
 * <p>Contiene anche le impostazioni di gioco, utilizzate quando si crea una
 * nuova partita.</p>
 */
class WordleSession {

    /** Partita corrente (eventualmente {@code null}). */
    private WordleGame currentGame;
    /** Parola segreta impostata (inizialmente {@code null}). */
    private String secretWord;

    // in release future saranno modificabili dal giocatore
    // quindi sarà eliminato il modificatore final
    /** Numero di tentativi per le partite. */
    private final int nMaxGuesses;
    /** Numero di lettere dei tentativi per le partite. */
    private final int wordLength;

    /** Impostazione di default per il numero di tentativi. */
    private static final int DEFAULT_MAX_GUESSES = 6;
    /** Impostazione di default per il numero di lettere. */
    private static final int DEFAULT_WORD_LENGTH = 5;

    /**
     * Crea una sessione di gioco inizializzando le impostazioni
     * per la creazione delle partite con i valori di default.
     */
    WordleSession() {
        currentGame = null;
        secretWord = null;
        nMaxGuesses = DEFAULT_MAX_GUESSES;
        wordLength = DEFAULT_WORD_LENGTH;
    }

    WordleGame getCurrentGame() {
        return currentGame;
    }

    /**
     * Imposta la partita da giocare o la elimina.
     *
     * @param game partita da giocare, {@code null} se si vuole
     *             eliminare la partita in corso
     */
    void setCurrentGame(final WordleGame game) {
        this.currentGame = game;
    }

    String getSecretWord() {
        return secretWord;
    }

    void setSecretWord(final String gameSecretWord) {
        Objects.requireNonNull(gameSecretWord);

        this.secretWord = gameSecretWord;
    }

    int getNMaxGuesses() {
        return nMaxGuesses;
    }

    int getWordLength() {
        return wordLength;
    }

    boolean hasSecretWord() {
        return secretWord != null;
    }

    boolean isGameRunning() {
        return currentGame != null;
    }

}
