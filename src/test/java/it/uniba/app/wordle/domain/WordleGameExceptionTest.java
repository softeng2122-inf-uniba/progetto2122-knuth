package it.uniba.app.wordle.domain;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


@DisplayName("Un WordleGameExceptionTest")
public class WordleGameExceptionTest {

    WordleGameException gameEx;

    @BeforeEach
    void createdNewGameException() {
        gameEx = new WordleGameException("stringa di prova");
    }

    @Nested
    @DisplayName("quando è lanciata con"
               + "throw new WordleGameException(\"stringa di prova\"")
    class CorrectlyCreatedWordleGameExceptionTest  {

        @Test
        @DisplayName("contiene \"stringa di prova\" come messaggio")
        void testCorrectGameException() {
            assertEquals("stringa di prova", gameEx.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {WordleGameException.EXISTS_GAME,
                            WordleGameException.NOT_EXISTS_GAME,
                            WordleGameException.NO_GUESSES_LEFT})
    @DisplayName("quando lanciata con {0} restituisce il messaggio corretto")
    void testExceptionWithSpecialString(String message) {
        assertEquals(message, (new WordleGameException(message)).getMessage());
    }

}

