package it.uniba.app.wordle.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Un WordleGameExceptionTest")
class WordleGameExceptionTest {

    @ParameterizedTest
    @ValueSource(strings = {WordleGameException.EXISTS_GAME,
                            WordleGameException.NOT_EXISTS_GAME,
                            WordleGameException.NO_GUESSES_LEFT,
                            "Stringa di prova"})
    @DisplayName("restituisce il messaggio corretto")
    void testExceptionWithSpecialString(final String message) {
        assertEquals(message, (new WordleGameException(message)).getMessage());
    }
}

