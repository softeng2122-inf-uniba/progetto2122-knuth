package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Un tentativo")
public class GuessTest {

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
        public void testGetWord(){
            assertEquals("PROVA", g.getWord());
        }

        @Test
        @DisplayName("inizia con 'P'")
        public void testGetLetter() {
            assertEquals('P',g.getLetter(0));
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, 5})
        @DisplayName("lancia ArrayIndexOutOfBoundsException fuori dagli indici")
        public void throwsExceptionWhenIndexOutOfBound(int i) {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> g.getLetter(i));
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, 1, 2, 3, 4})
        @DisplayName("contiene lettera NO_COLOR")
        public void testGetColor(int i) {
            assertEquals(Color.NO_COLOR, g.getColor(i));
        }

        @Nested
        @DisplayName("quando P è colorata di verde")
        class AfterSetColorGreen {

            @Test
            @DisplayName("risulta verde")
            public void testSetColor() {
                g.setColor(0, Color.GREEN);
                assertEquals(Color.GREEN, g.getColor(0));
            }

        }

        @Nested
        @DisplayName("quando P è colorata di giallo")
        class AfterSetColorYellow {

            @Test
            @DisplayName("risulta gialla")
            public void testSetColor() {
                g.setColor(0, Color.YELLOW);
                assertEquals(Color.YELLOW, g.getColor(0));
            }

        }

        @Nested
        @DisplayName("quando P è colorata di grigio")
        class AfterSetColorGrey {

            @Test
            @DisplayName("risulta grigio")
            public void testSetColor() {
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

/*
@DisplayName("A stack")
class TestingAStackDemo {

    Stack<Object> stack;

    @Test
    @DisplayName("is instantiated with new Stack()")
    void isInstantiatedWithNew() {
        new Stack<>();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createNewStack() {
            stack = new Stack<>();
        }

        @Test
        @DisplayName("is empty")
        void isEmpty() {
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("throws EmptyStackException when popped")
        void throwsExceptionWhenPopped() {
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("throws EmptyStackException when peeked")
        void throwsExceptionWhenPeeked() {
            assertThrows(EmptyStackException.class, stack::peek);
        }

        @Nested
        @DisplayName("after pushing an element")
        class AfterPushing {

            String anElement = "an element";

            @BeforeEach
            void pushAnElement() {
                stack.push(anElement);
            }

            @Test
            @DisplayName("it is no longer empty")
            void isNotEmpty() {
                assertFalse(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when popped and is empty")
            void returnElementWhenPopped() {
                assertEquals(anElement, stack.pop());
                assertTrue(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when peeked but remains not empty")
            void returnElementWhenPeeked() {
                assertEquals(anElement, stack.peek());
                assertFalse(stack.isEmpty());
            }
        }
    }
}

*/
