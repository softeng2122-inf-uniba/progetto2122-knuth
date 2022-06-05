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

/*

@DisplayName("Un tentativo")
class GuessTest {

    Guess g;

    @Test
    @DisplayName("è istanziato con new Guess(guessingWord)")
    void isInstantiatedWithNew() {
        new Guess("PROVA");
    }

    @Nested
    @DisplayName("quando si instanzia con new e una stringa come parametro")
    class createdWithPROVA {

        @BeforeEach
        void createNewGuess() {
            g = new Guess("PROVA");
        }

        @Test
        @DisplayName("restituisce la stringa \"PROVA\"")
        void testGetWord(){
            assertEquals("PROVA", g.getWord());
        }

        @Test
        @DisplayName("contiene i caratteri 'P', 'R', 'O', 'V', 'A'")
        void testGetLetter() {
            assertAll(() -> assertEquals('P',g.getLetter(0)),
                    () -> assertEquals('R',g.getLetter(1)),
                    () -> assertEquals('O',g.getLetter(2)),
                    () -> assertEquals('V',g.getLetter(3)),
                    () -> assertEquals('A',g.getLetter(4)));
        }

        @ParameterizedTest(name = "Indice: {0}")
        @ValueSource(ints = { -1, 5})
        @DisplayName("lancia ArrayIndexOutOfBoundsException" +
                " con un indice invalido")
        void testThrowsExceptionWhenIndexOutOfBound(int i) {
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> g.getLetter(i));
        }

        @Test
        @DisplayName("contiene lettere impostate a NO_COLOR")
        void testGetColor() {
            assertAll(() -> assertEquals(Color.NO_COLOR, g.getColor(0)),
                    () -> assertEquals(Color.NO_COLOR, g.getColor(1)),
                    () -> assertEquals(Color.NO_COLOR, g.getColor(2)),
                    () -> assertEquals(Color.NO_COLOR, g.getColor(3)),
                    () -> assertEquals(Color.NO_COLOR, g.getColor(4)));
        }

        @Nested
        @DisplayName("quando P è colorata di verde")
        class AfterSetColorGreenTest {

            @Test
            @DisplayName("risulta verde")
            void testSetColor() {
                g.setColor(0, Color.GREEN);
                assertEquals(Color.GREEN, g.getColor(0));
            }

        }

        @Nested
        @DisplayName("quando P è colorata di giallo")
        class AfterSetColorYellowTest {

            @Test
            @DisplayName("risulta gialla")
            void testSetColor() {
                g.setColor(0, Color.YELLOW);
                assertEquals(Color.YELLOW, g.getColor(0));
            }

        }

        @Nested
        @DisplayName("quando P è colorata di grigio")
        class AfterSetColorGreyTest {

            @Test
            @DisplayName("risulta grigia")
            void testSetColor() {
                g.setColor(0, Color.GREY);
                assertEquals(Color.GREY, g.getColor(0));
            }

        }

    }

    @Test
    @DisplayName("lancia NullPointerException se istanziato con new Guess(null)")
    void testCreateNullGuess() {
        assertThrows(NullPointerException.class, () -> new Guess(null));
    }

}
*/
