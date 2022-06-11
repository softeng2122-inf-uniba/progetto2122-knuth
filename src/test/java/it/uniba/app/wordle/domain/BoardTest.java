package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Una board")
class BoardTest {

    /** Board per effettuare i test. */
    private Board board;
    /** Valore di default per il numero di righe. */
    private static final int DEFAULT_ROW = 6;
    /** Valore di default per il numero di colonne. */
    private static final int DEFAULT_COLUMN = 5;


    @Nested
    @DisplayName("quando è istanziata con new Board(5, 6)")
    class CorrectlyCreatedTest {

        @BeforeEach
        void createNewBoard() {
            board = new Board(DEFAULT_ROW, DEFAULT_COLUMN);
        }

        @Test
        @DisplayName("contiene 6 righe")
        public void testGetRowsNumber() {
            assertEquals(DEFAULT_ROW, board.getRowsNumber());
        }

        @Test
        @DisplayName("contiene 5 colonne")
        public void testGetWordLength() {
            assertEquals(DEFAULT_COLUMN, board.getWordLength());
        }

        @Test
        @DisplayName("è vuota")
        public void testGetNumFilledRows() {
            assertEquals(0, board.getNumFilledRows());
        }

        @Nested
        @DisplayName("dopo l'inserimento del tentativo \"PROVA\"")
        class PushAGuessTest {

            /** Tentativo per effettuare i test. */
            private Guess guess;
            @BeforeEach
            void pushAGuess() {
                guess = new Guess("PROVA");
                board.acceptNewGuess(guess);
            }
            //Se si inserisce una guess null

            @Test
            @DisplayName("esattamente una riga è piena")
            void testIsNotEmpty() {
                assertEquals(1, board.getNumFilledRows());
            }

            @Test
            @DisplayName("la prima riga contiene il tentativo \"PROVA\"")
            void testReturnPushedGuess() {
                assertEquals(guess, board.getGuess(0));
            }

            @Nested
            @DisplayName("dopo aver riempito la board")
            class FullBoardTest {

                @BeforeEach
                void fillBoard() {
                    board.acceptNewGuess(new Guess("NUOVA"));
                    board.acceptNewGuess(new Guess("PROVA"));
                    board.acceptNewGuess(new Guess("TRENO"));
                    board.acceptNewGuess(new Guess("AIUTO"));
                    board.acceptNewGuess(new Guess("EBETE"));
                }

                @Test
                @DisplayName("lancia ArrayIndexOutOfBoundsException "
                        + "provando un ulteriore tentativo")
                void testGuessOverflow() {
                    assertThrows(ArrayIndexOutOfBoundsException.class,
                            () -> board.acceptNewGuess(new Guess("GATTO")));
                }

            }

        }


        @Nested
        @DisplayName("provando l'inserimento del tentativo null")
        class PushANullGuessTest {

            @Test
            @DisplayName("lancia NullPointerException")
            void testAcceptNullGuess() {
                assertThrows(NullPointerException.class,
                        () -> board.acceptNewGuess(null));
            }

        }

    }

    @Test
    @DisplayName("lancia IllegalArgumentException "
            + "se istanziata con new Board(-1, 6)")
    void testInvalidFirstIndexBoard() {
        assertThrows(IllegalArgumentException.class,
                () -> new Board(-1, DEFAULT_ROW));
    }

    @Test
    @DisplayName("lancia IllegalArgumentException "
            + "se istanziata con new Board(5, -1)")
    void testInvalidSecondIndexBoard() {
        assertThrows(IllegalArgumentException.class,
                () -> new Board(DEFAULT_COLUMN, -1));
    }

    @Test
    @DisplayName("lancia IllegalArgumentException "
            + "se istanziata con new Board(0, 0)")
    void testInvalidIndexBoard() {
        assertThrows(IllegalArgumentException.class,
                () -> new Board(0, 0));
    }

}
