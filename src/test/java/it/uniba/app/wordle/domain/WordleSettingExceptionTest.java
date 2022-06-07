package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Una WordleSettingExceptionTest")
public class WordleSettingExceptionTest {

    WordleSettingException wordSetE;

    @BeforeEach
    void createNewWordleSettingException() {
        wordSetE = new WordleSettingException("stringa di prova");
    }

    @ParameterizedTest
    @ValueSource(strings = {WordleSettingException.ABSENT_SECRET_WORD})
    @DisplayName("quando lanciata con {0} restituisce il messaggio corretto")
    void testExceptionWithSpecialString(String message) {
        assertEquals(message, (new WordleSettingException(message)).getMessage());
    }
}
