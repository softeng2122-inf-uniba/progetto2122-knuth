package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Una board")
class BoardTest {

    @Nested
    @DisplayName("quando è istanziata con new Board(5, 6)")
    class CorrectlyCreatedTest {

        Board board;

        @BeforeEach
        void createNewBoard() {
            board = new Board(5, 6);
        }

        @Test
        @DisplayName("contiene 6 righe")
        public void testGetRowsNumber() {
            assertEquals(6, board.getRowsNumber());
        }

        @Test
        @DisplayName("contiene 5 colonne")
        public void testGetWordLength() {
            assertEquals(5, board.getWordLength());
        }

        @Test
        @DisplayName("è vuota")
        public void testGetNumFilledRows() {
            assertEquals(0, board.getNumFilledRows());
        }

        @Nested
        @DisplayName("dopo l'inserimento del tentativo \"PROVA\"")
        class PushAGuessTest {

            Guess guess;
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
                @DisplayName("lancia ArrayIndexOutOfBoundsException " +
                        "provando un ulteriore tentativo")
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
                assertThrows(NullPointerException.class, () -> board.acceptNewGuess(null));
            }

        }

    }

    @Test
    @DisplayName("lancia IllegalArgumentException se istanziata con new Board(-3, 6)")
    void testInvalidFirstIndexBoard() {
        assertThrows(IllegalArgumentException.class, () -> new Board(-3, 6));
    }

    @Test
    @DisplayName("lancia NegativeArraySizeException se istanziata con new Board(5, -3)")
    void testInvalidSecondIndexBoard() {
        assertThrows(IllegalArgumentException.class, () -> new Board(5, -3));
    }

    @Test
    @DisplayName("quando è istanziata con new Board(0, 0) accetta il fesso")
    void testInvalidIndexBoard() {
        assertThrows(IllegalArgumentException.class, () -> new Board(0, 0));
    }

}
