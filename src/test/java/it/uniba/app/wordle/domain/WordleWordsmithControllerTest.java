package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Un WordleWordsmithController")
class WordleWordsmithControllerTest {

    /** Controller del giocatore per effettuare i test. */
    private WordleWordsmithController wordsmithCtr;
    /** Controller del paroliere per effettuare i test. */
    private WordlePlayerController playerCtr;

    @BeforeEach
    void createNewWordleWordsmithController() {
        playerCtr = new WordlePlayerController();
        wordsmithCtr = new WordleWordsmithController(playerCtr);
    }

    @Nested
    @DisplayName("quando è istanziato con"
                 + " new WordleWordsmithController"
                 + "(playerController)")
    class CorrectlyCreatedTest {

        @Test
        @DisplayName("lancia NullPointerException se si prova ad inserire "
                      + "null come parola segreta")
        void testSetWordAsNull() {
            assertThrows(NullPointerException.class,
                         () -> wordsmithCtr.setSecretWord(null));
        }

        @Test
        @DisplayName("lancia WordleSettingException se si richiede la "
                      + "parola segreta quando non impostata")
        void testGetSecretWord() {
            assertThrows(WordleSettingException.class,
                         () -> wordsmithCtr.getSecretWord());
        }

        @Nested
        @DisplayName("quando viene impostata la parola segreta \"TRONO\"")
        class ValidSecretWordTest {

            @BeforeEach
            void setValidSecretWord() {
                wordsmithCtr.setSecretWord("TRONO");
            }

            @Test
            @DisplayName("la restituisce correttamente")
            void testGetSecretWord() {
                assertEquals("TRONO", wordsmithCtr.getSecretWord());
            }
        }

        @Test
        @DisplayName("lancia IllegalArgumentException se si prova "
                     + "ad inserire una parola segreta troppo corta")
        void testTooShortSecretWord() {
            assertThrows(IllegalArgumentException.class,
                         () -> wordsmithCtr.setSecretWord("PERA"));
        }

        @Test
        @DisplayName("lancia IllegalArgumentException se si prova "
                     + "ad inserire una parola segreta troppo lunga")
        void testTooLongSecretWord() {
            assertThrows(IllegalArgumentException.class,
                         () -> wordsmithCtr.setSecretWord("PEPERA"));
        }

        @Test
        @DisplayName("lancia IllegalArgumentException se si prova"
                     + "ad inserire una parola segreta contenente "
                     + "caratteri non alfabetici")
        void testInvalidSecretWord() {
            assertThrows(IllegalArgumentException.class,
                         () -> wordsmithCtr.setSecretWord("P/(++"));
        }

        @Nested
        @DisplayName("quando una partita è già in corso")
        class GameRunningTest {

            @BeforeEach
            void initGame() {
                wordsmithCtr.setSecretWord("TAPPO");
                playerCtr.startGame();
            }

            @Test
            @DisplayName("lancia WordleGameException se si prova "
                         + "ad impostare una nuova parola segreta")
            void testGameExceptionSetSecretWord() {
                assertThrows(WordleGameException.class,
                             () -> wordsmithCtr.setSecretWord("PANNA"));
            }

            @Test
            @DisplayName("la parola segreta della partita"
                         + " corrisponde a quella del WordsmithController")
            void testSameSecretWord() {
                assertEquals(wordsmithCtr.getSecretWord(),
                             playerCtr.getGameSecretWord());
            }
        }
    }
}
