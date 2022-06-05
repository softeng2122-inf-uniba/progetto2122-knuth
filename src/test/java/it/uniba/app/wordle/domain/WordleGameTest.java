package it.uniba.app.wordle.domain;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName("Un WordleGame")
class WordleGameTest {

    private static final int DEFAULT_MAX_GUESSES = 6;
    private static final int DEFAULT_WORD_LENGTH = 5;

    WordleGame wGame;
     // WordleGame(final String secretWord,
        //       final  int numMaxGuesses, final int wordLength)

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
    class createdWith3parameters {

        @BeforeEach
        void createNewWordleGame() {
            wGame = new WordleGame("GIOCO",
                                    DEFAULT_MAX_GUESSES, DEFAULT_WORD_LENGTH);
        }


        /*

        public int getMaxGuesses() {
            return gameBoard.getRowsNumber();
        }

        public int getWordLength() {
            return gameBoard.getWordLength();
        }

        public int getNumRemainingGuesses() {
            return getMaxGuesses() - gameBoard.getNumFilledRows();
        }
        */

         /*
        public String getSecretWord() {
            return secretWord;
        }
        */

        @Test
        @DisplayName("restituisce la stringa \"GIOCO\"")
        void testGetSecretWord() {
            assertEquals("GIOCO", wGame.getSecretWord());
        }

        /*
        public Board getGameBoard() {
            return gameBoard;
        }
        */
        @Test
        @DisplayName("contiene una Board di dimensioni 6 righe per 5 colonne")
        void testGetLetter() {
           //Board sample = new Board(DEFAULT_MAX_GUESSES, DEFAULT_WORD_LENGTH);

            assertAll(() -> assertEquals(wGame.getGameBoard(), wGame.getGameBoard()),
                      () -> assertEquals(DEFAULT_MAX_GUESSES,
                                         wGame.getGameBoard().getRowsNumber()),
                      () -> assertEquals(DEFAULT_WORD_LENGTH,
                                         wGame.getGameBoard().getWordLength()));
        }

        /*
        @ParameterizedTest(name = "Indice: {0}")
        @ValueSource(ints = { -1, 5})
        @DisplayName("lancia ArrayIndexOutOfBoundsException con un indice invalido")
        void throwsExceptionWhenIndexOutOfBound(int i) {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> g.getLetter(i));
        }

        @Test
        @DisplayName("contiene lettere impostate a NO_COLOR")
        void testGetColor() {
            assertAll(() -> assertEquals(Color.NO_COLOR, g.getColor(0)),
                    () -> assertEquals(Color.NO_COLOR, g.getColor(1)),
                    () -> assertEquals(Color.NO_COLOR, g.getColor(2)),
                    () -> assertEquals(Color.NO_COLOR, g.getColor(3)),
                    () -> assertEquals(Color.NO_COLOR, g.getColor(4)));
        }

        @Nested
        @DisplayName("quando P è colorata di verde")
        class AfterSetColorGreenTest {

            @Test
            @DisplayName("risulta verde")
            void testSetColor() {
                g.setColor(0, Color.GREEN);
                assertEquals(Color.GREEN, g.getColor(0));
            }

        }

        @Nested
        @DisplayName("quando P è colorata di giallo")
        class AfterSetColorYellowTest {

            @Test
            @DisplayName("risulta gialla")
            void testSetColor() {
                g.setColor(0, Color.YELLOW);
                assertEquals(Color.YELLOW, g.getColor(0));
            }

        }

        @Nested
        @DisplayName("quando P è colorata di grigio")
        class AfterSetColorGreyTest {

            @Test
            @DisplayName("risulta grigia")
            void testSetColor() {
                g.setColor(0, Color.GREY);
                assertEquals(Color.GREY, g.getColor(0));
            }

        }

    }

    @Nested
    @DisplayName("quando è istanziato con new Guess(null)")
    class CreatedWithNullTest {
        @Test
        @DisplayName("lancia NullPointerException")
        void createNullGuess() {
            assertThrows(NullPointerException.class, () -> new Guess(null));
        }
    }
    */
    }
}


