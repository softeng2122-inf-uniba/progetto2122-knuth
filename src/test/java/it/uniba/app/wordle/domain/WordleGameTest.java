package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Un WordleGame")
class WordleGameTest {

    private static final int DEFAULT_MAX_GUESSES = 6;
    private static final int DEFAULT_WORD_LENGTH = 5;

    private WordleGame wGame;

    @Nested
    @DisplayName("quando è istanziato con new WordleGame(\"GIOCO\", 6, 5)")
    class CorrectlyCreatedTest {

        @BeforeEach
        void createNewWordleGame() {
            wGame = new WordleGame("GIOCO",
                                    DEFAULT_MAX_GUESSES, DEFAULT_WORD_LENGTH);
        }

        @Test
        @DisplayName("ha come parola segreta \"GIOCO\"")
        void testGetSecretWord() {
            assertEquals("GIOCO", wGame.getSecretWord());
        }

        @Test
        @DisplayName("prevede un numero massimo di 6 tentativi")
        void testGetMaxGuesses() {
            assertEquals(DEFAULT_MAX_GUESSES, wGame.getMaxGuesses());
        }

        @Test
        @DisplayName("prevede parole lunghe 5 caratteri")
        void testGetWordLength() {
            assertEquals(DEFAULT_WORD_LENGTH, wGame.getWordLength());
        }

        @Test
        @DisplayName("crea una board di dimensioni adeguate")
        void testGetGameBoard() {
            assertAll(() -> assertEquals(wGame.getMaxGuesses(),
                              wGame.getGameBoard().getRowsNumber()),
                      () -> assertEquals(wGame.getWordLength(),
                              wGame.getGameBoard().getWordLength()));
        }

        @Nested
        @DisplayName("dopo aver effettuato due tentativi validi")
        class AfterTwoGuessesTest {
            @BeforeEach
            void pushTwoGuesses() {
                wGame.getGameBoard().acceptNewGuess(new Guess("CUOCO"));
                wGame.getGameBoard().acceptNewGuess(new Guess("BIRRA"));
            }

            @Test
            @DisplayName("rimangono 4 tentativi disponibili")
            void testGetNumRemainingGuesses() {
                assertEquals(wGame.getMaxGuesses() - 2,
                                     wGame.getNumRemainingGuesses());
            }
        }
    }
}


