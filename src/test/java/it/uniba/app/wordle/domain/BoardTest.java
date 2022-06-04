package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName("Una board")
class BoardTest {

    static Board board;

    @DisplayName("quando è istanziata con new Board(5, 6)")
    static class WhenNew {

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
        class AfterPushingAGuess {

            Guess guess = new Guess("PROVA");
            @BeforeEach
            void pushAGuess() {
                board.acceptNewGuess(guess);
            }
            //Se si inserisce una guess null

            @Test
            @DisplayName("la prima riga è piena")
            void isNotEmpty() {
                assertEquals(1, board.getNumFilledRows());
            }

            @Test
            @DisplayName("la prima riga contiene il tentativo \"PROVA\"")
            void returnPushedGuess() {

                //Guess g = new Guess("PROVA");
                //Per usare assertEquals è necessario l'override del metodo equals di Guess

                assertSame(guess, board.getGuess(board.getNumFilledRows()-1));
            }

        }

    }

}
