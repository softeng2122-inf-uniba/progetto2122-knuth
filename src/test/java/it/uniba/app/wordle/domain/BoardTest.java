package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {

    static Board board;

    @BeforeAll
    static void setUpAllBoard() {
        board = new Board(5, 6);
    }

    @Test
    @DisplayName("Test del costruttore")
    public void testBoard(){
        assertEquals(5, board.getWordLength());
        assertEquals(6, board.getRowsNumber());
        assertEquals(0, board.getNumFilledRows());
    }

    @Test
    public void testGetRowsNumber() {
        assertEquals(6, board.getRowsNumber());
    }

    @Test
    public void testGetWordLength() {
        assertEquals(5, board.getWordLength());
    }

    @Test
    public void testGetNumFilledRows() {
        assertEquals(0, board.getNumFilledRows());
    }

    @Test
    @DisplayName("Test di acceptNewGuess")
    public void testAcceptNewGuess() {
        Board board = new Board(5,6);
        Guess guess = new Guess("nuova");
        board.acceptNewGuess(guess);
        assertEquals(guess,board.getGuess(board.getNumFilledRows()-1));
    }

    @Test
    @DisplayName("Test di getGuess")
    public void testGetGuess() {
        Guess[] arrayGuess = {new Guess("nuova"),
                              new Guess("treno"),
                              new Guess("aiuto"),
                              new Guess("piove"),
                              new Guess("sauna")};

        Board board = new Board(5,6);
        for(int i=0; i < 5; i++){
            board.acceptNewGuess(arrayGuess[i]);
        }
        assertEquals(arrayGuess[0], board.getGuess(0));
    }

}
