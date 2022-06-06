package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Un WordleWordsmithController")
public class WordleWordsmithControllerTest {

    WordleWordsmithController wordsmithCtr;
    WordlePlayerController playerCtr;

    @BeforeEach
    void createNewWordleWordsmithController() {
        playerCtr = new WordlePlayerController();
        wordsmithCtr = new WordleWordsmithController(playerCtr);
    }

    @Nested
    @DisplayName("quando è istanziato con"
            + " new WordleWordmismithController"
            + "(playerController)")
    class CorrectlyCreatedTest {

        @Test
        @DisplayName("lancia WordleSettingException quando la parola non è impostata")
        void testGetSecretWord() {
            assertThrows(WordleSettingException.class, () -> wordsmithCtr.getSecretWord());
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
        @DisplayName("Lancia IllegalArgumentException "
                + "se la parola ha lunghezza minore rispetto a quella prevista")
        void testTooShortSecretWord() {
            assertThrows(IllegalArgumentException.class,
                    () -> wordsmithCtr.setSecretWord("PERA"));
        }

        @Test
        @DisplayName("Lancia IllegalArgumentException "
                + "se la parola ha lunghezza maggiore rispetto a quella prevista")
        void testTooLongSecretWord() {
            assertThrows(IllegalArgumentException.class,
                    () -> wordsmithCtr.setSecretWord("PEPERA"));
        }

        @Test
        @DisplayName("Lancia IllegalArgumentException "
                + "se la parola contiene caratteri non alfabetici")
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
            @DisplayName("Lancia WordleGameException "
                    + "se si prova ad impostare una nuova parola segreta")
            void testGameExceptionSetSecretWord() {
                assertThrows(WordleGameException.class,
                        () -> wordsmithCtr.setSecretWord("PANNA"));
            }

            @Test
            @DisplayName("La parola segreta della partita"
                    + " corrisponde a quella del WordsmithController")
            void testSameSecretWord() {
                assertEquals(wordsmithCtr.getSecretWord(),
                        playerCtr.getGameSecretWord());
            }
        }
    }
}
