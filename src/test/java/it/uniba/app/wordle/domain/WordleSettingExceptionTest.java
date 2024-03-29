package it.uniba.app.wordle.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Una WordleSettingExceptionTest")
class WordleSettingExceptionTest {

    @ParameterizedTest
    @ValueSource(strings = {WordleSettingException.ABSENT_SECRET_WORD,
            "Stringa di prova"})
    @DisplayName("restituisce il messaggio corretto")
    void testExceptionWithSpecialString(final String message) {
        assertEquals(message, (new WordleSettingException(message))
                                                     .getMessage());
    }
}
