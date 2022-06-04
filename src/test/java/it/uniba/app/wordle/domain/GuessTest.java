package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        @DisplayName("contiene \"PROVA\"")
        void testGetWord(){
            assertEquals("PROVA", g.getWord());
        }

        @Test
        @DisplayName("il tentativo inizia con 'P'")
        void testGetLetter() {
            assertEquals('P',g.getLetter(0));
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, 5})
        @DisplayName("lancia ArrayIndexOutOfBoundsException fuori dagli indici del tentativo")
        void throwsExceptionWhenIndexOutOfBound(int i) {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> g.getLetter(i));
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 2, 3, 4})
        @DisplayName("contiene lettere impostate a NO_COLOR")
        void testGetColor(int i) {
            assertEquals(Color.NO_COLOR, g.getColor(i));
        }

        @Nested
        @DisplayName("quando P è colorata di verde")
        class AfterSetColorGreen {

            @Test
            @DisplayName("risulta verde")
            void testSetColor() {
                g.setColor(0, Color.GREEN);
                assertEquals(Color.GREEN, g.getColor(0));
            }

        }

        @Nested
        @DisplayName("quando P è colorata di giallo")
        class AfterSetColorYellow {

            @Test
            @DisplayName("risulta gialla")
            void testSetColor() {
                g.setColor(0, Color.YELLOW);
                assertEquals(Color.YELLOW, g.getColor(0));
            }

        }

        @Nested
        @DisplayName("quando P è colorata di grigio")
        class AfterSetColorGrey {

            @Test
            @DisplayName("risulta grigia")
            void testSetColor() {
                g.setColor(0, Color.GREY);
                assertEquals(Color.GREY, g.getColor(0));
            }

        }

    }

    @Nested
    @DisplayName("quando è istanziato con new Guess(null)")
    class ConstructorTry {
        @Test
        @DisplayName("lancia NullPointerException")
        void createNullGuess() {
            assertThrows(NullPointerException.class, () -> new Guess(null));
        }

    }

}