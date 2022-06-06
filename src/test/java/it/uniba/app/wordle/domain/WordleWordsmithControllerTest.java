package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Un WordleWordsmithController")
class WordleWordsmithControllerTest {

    WordleWordsmithController wordsmithCtr;
    //private static final int DEFAULT_MAX_GUESSES = 6;
    //private static final int DEFAULT_WORD_LENGTH = 5;

    @Nested
    @DisplayName("quando è istanziato con"
                            + " new WordleWordmismithController"
                            + "(playerController)")
    class CorrectlyCreatedTest {

        @BeforeEach
        void createNewWordleWordsmithController() {
           /* WordleSession testingSession = new WordleSession();
            WordleGame game = new WordleGame(
                    "", DEFAULT_MAX_GUESSES, DEFAULT_WORD_LENGTH);

            testingSession.setCurrentGame(game);
            testingSession.setSecretWord("CORSA");
            */
            wordsmithCtr = new WordleWordsmithController(new WordlePlayerController());
        }

        @Test
        @DisplayName("lancia un'eccezione se la parola non è impostata")
        void testgetSecretWord() {
            assertThrows(WordleSettingException.class, () -> wordsmithCtr.getSecretWord());
        }

        @Test
        @DisplayName("imposta la parola \"TORTA\"")
        void testSetSecretWord() {
            wordsmithCtr.setSecretWord("TORTA");
            assertEquals("TORTA", wordsmithCtr.getSecretWord());
        }
    }
}
