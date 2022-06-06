package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Una WordleSettingExceptionTest")
public class WordleSettingExceptionTest {

    WordleSettingException wordSetE;

    @BeforeEach
    void createNewWordleSettingException() {
        wordSetE = new WordleSettingException("stringa di prova");
    }

    @Nested
    @DisplayName("quando è lanciata con "
            + "throw new WordleSettingException(\"stringa di prova\")")
    class CorrectlyCreatedWordleSettingExceptionTest {

        @Test
        @DisplayName("contiene \"stringa di prova\" come messaggio")
        void testCorrectSettingException() {
            assertEquals("stringa di prova", wordSetE.getMessage());
        }

    }

    @Nested
    @DisplayName("quando lanciata con"
                 + "throw new WordleSettingException(ABSENT_SECRET_WORD)")
    class CorrectlyCreatedWithMotivationTest {

        @BeforeEach
        void initWithMotivation() {
            wordSetE = new WordleSettingException(
                       WordleSettingException.Motivation.ABSENT_SECRET_WORD);
        }

        @Test
        @DisplayName("contine la stringa associata alla motivation")
        void testReadingMotivation() {
            assertEquals(WordleSettingException
                         .Motivation
                         .ABSENT_SECRET_WORD.getMessage(),
                         wordSetE.getMessage());
        }
    }

}
