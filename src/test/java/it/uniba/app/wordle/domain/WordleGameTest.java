package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Un WordleGame")
class WordleGameTest {

    private static final int DEFAULT_MAX_GUESSES = 6;
    private static final int DEFAULT_WORD_LENGTH = 5;

    WordleGame wGame;

    @Test
    @DisplayName("è istanziato con new WordleGame"
            + "(secretWord, numMaxGuesses, wordLength)")
    void isInstantiatedWithNew() {
        new WordleGame("PACCO",
                        DEFAULT_MAX_GUESSES, DEFAULT_WORD_LENGTH);
    }

    @Nested
    @DisplayName("quando si instanzia con new"
            + " e una stringa con 2 interi come parametri")
    class createdWithParametersTest {

        @BeforeEach
        void createNewWordleGame() {
            wGame = new WordleGame("GIOCO",
                                    DEFAULT_MAX_GUESSES, DEFAULT_WORD_LENGTH);
        }

        @Test
        @DisplayName("restituisce la stringa \"GIOCO\"")
        void testGetSecretWord() {
            assertEquals("GIOCO", wGame.getSecretWord());
        }

        @Test
        @DisplayName("contiene una Board di dimensioni 6 righe per 5 colonne")
        void testGetGameBoard() {

            assertAll(() -> assertEquals(wGame.getGameBoard(), wGame.getGameBoard()),
                      () -> assertEquals(DEFAULT_MAX_GUESSES,
                                         wGame.getGameBoard().getRowsNumber()),
                      () -> assertEquals(DEFAULT_WORD_LENGTH,
                                         wGame.getGameBoard().getWordLength()));
        }

        @Test
        @DisplayName("restituisce numero massimo di tentativi 6")
        void testgetMaxGuesses() {
            assertEquals(DEFAULT_MAX_GUESSES, wGame.getMaxGuesses());
        }

        @Test
        @DisplayName("restituisce la lunghezza della parola segreta")
        void testgetWordLength() {
            assertEquals(DEFAULT_WORD_LENGTH, wGame.getWordLength());
        }

        @Test
        @DisplayName("ritorna numero di tentativi rimasti dopo aveni fatti 2")
        void testgetNumRemainingGuesses() {

            Guess testing1 = new Guess("cuoco");
            wGame.getGameBoard().acceptNewGuess(testing1);

            Guess testing2 = new Guess("birra");
            wGame.getGameBoard().acceptNewGuess(testing2);

            assertEquals(wGame.getMaxGuesses() - 2,
                          wGame.getNumRemainingGuesses());
        }
    }
}


