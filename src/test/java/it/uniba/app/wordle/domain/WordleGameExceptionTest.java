package it.uniba.app.wordle.domain;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


@DisplayName("Un WordleGameExceptionTest")
public class WordleGameExceptionTest {

    WordleGameException gameEx;

    @BeforeEach
    void createdNewGameException() {
        gameEx = new WordleGameException("stringa di prova");
    }

    @Nested
    @DisplayName("quando è lanciata con"
               + "throw new WordleGameException(\"stringa di prova\"")
    class CorrectlyCreatedWordleGameExceptionTest  {

        @Test
        @DisplayName("contiene \"stringa di prova\" come messaggio")
        void testCorrectGameException() {
            assertEquals("stringa di prova", gameEx.getMessage());
        }
    }

    @Nested
    @DisplayName("quando lanciata con "
                + "throw new WordleGameException(Motivation motivation)")
    class CorrectlyCreatedWithMotivationTest {

        @Test
        @DisplayName("se motivation è EXISTS_GAME "
                + "restituisce la stringa associata")
        void testCorrectlyCreatedWithMotivationExistsGame() {
            WordleGameException gameExp1 = new
                             WordleGameException(
                             WordleGameException.Motivation.EXISTS_GAME);
            assertEquals("Partita in corso", gameExp1.getMessage());
        }

        @Test
        @DisplayName("se motivation è NOT_EXISTS_GAME"
                    + "restituisce la stringa associata")
        void testCorrectlyCreatedWithMotivationNotExistsGame() {
            WordleGameException gameExp2 = new
                              WordleGameException(
                                      WordleGameException
                                              .Motivation.NOT_EXISTS_GAME);

            assertEquals("Partita inesistente", gameExp2.getMessage());
        }

        @Test
        @DisplayName("se motivation è NO_GUESSES_LEFT"
                + "restituisce la stringa associata")
        void testCorrectlyCreatedWithMotivationNoGuessesLeft() {
            WordleGameException gameExp3 = new
                    WordleGameException(
                            WordleGameException
                                    .Motivation.NO_GUESSES_LEFT);
            assertEquals("Massimo numero di tentativi raggiunto",
                             gameExp3.getMessage());
        }
    }



    }

