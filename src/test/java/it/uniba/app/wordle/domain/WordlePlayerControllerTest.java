package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Un WordlePlayerController")
class WordlePlayerControllerTest {

    /** Controller per effettuare i test. */
    private WordlePlayerController pc;

    @BeforeEach
    void createController() {
        pc = new WordlePlayerController();
    }

    @Nested
    @DisplayName("quando non ha partite in corso")
    class NoGameRunningTest {

        @Test
        @DisplayName("nessuna partita è in corso")
        void testGameNotRunning() {
            assertFalse(pc.isGameRunning());
        }

        @Test
        @DisplayName("lancia WordleGameException se si chiede "
                + "il numero massimo di tentativi")
        void testThrowGameExceptionGetMaxGuesses() {
            assertThrows(WordleGameException.class,
                    () -> pc.getMaxGuesses());
        }

        @Test
        @DisplayName("lancia WordleGameException se si chiede "
                       + "il numero di tentativi rimanenti")
        void testThrowGameExceptionGetNumRemainingGuesses() {
            assertThrows(WordleGameException.class,
                    () -> pc.getNumRemainingGuesses());
        }

        @Test
        @DisplayName("lancia WordleGameException se si chiede "
                        + "la lunghezza della parola segreta")
        void testThrowGameExceptionGetWordLength() {
            assertThrows(WordleGameException.class,
                    () -> pc.getWordLength());
        }

        @Test
        @DisplayName("lancia WordleGameException se si prova "
                        + "a effettuare un tentativo")
        void testThrowGameExceptionGuess() {
            assertThrows(WordleGameException.class,
                    () -> pc.guess("PROVA"));
        }

        @Test
        @DisplayName("lancia WordleGameException se si chiede "
                + "l'esito dell'ultimo tentativo")
        void testThrowGameExceptionGetGuessResult() {
            assertThrows(WordleGameException.class,
                     () -> pc.getGuessResult());
        }

        @Test
        @DisplayName("lancia WordleGameException se si chiede "
                + "la lettera di una cella della board")
        void testThrowGameExceptionGetLetter() {
            assertThrows(WordleGameException.class,
                    () -> pc.getLetter(0, 0));
        }

        @Test
        @DisplayName("lancia WordleGameException se si chiede "
                      + "il colore di una cella della board")
        void testThrowGameExceptionGetColor() {
            assertThrows(WordleGameException.class,
                    () -> pc.getColor(0, 0));
        }

        @Test
        @DisplayName("lancia WordleGameException se si prova "
                      + "a terminare la partita")
        void testThrowGameExceptionEndGame() {
            assertThrows(WordleGameException.class,
                    () -> pc.endGame());
        }

        // non può restituire la parola segreta della partita
        @Test
        @DisplayName("lancia WordleGameException se si chiede "
                      + "la parola segreta della partita")
        void testThrowGameExceptionGetGameSecretWord() {
            assertThrows(WordleGameException.class,
                    () -> pc.getGameSecretWord());
        }

        @Test
        @DisplayName("lancia WordleSettingExceptions se si prova "
                + "ad iniziare una partita prima di aver "
                + "impostato la parola segreta")
        void testThrowSettingExceptionStartGame() {
            assertThrows(WordleSettingException.class,
                    () -> pc.startGame());
        }
        // possiamo iniziare una nuova partita

        @Nested
        @DisplayName("quando viene impostata la parola segreta \"TRONO\" "
                     + "e viene iniziata la partita")
        class GameStartedTest {

            /** Valore di default per il numero massimo di tentativi. */
            private static final int DEFAULT_MAX_GUESSES = 6;
            /** Valore di default per la lunghezza dei tentativi. */
            private static final int DEFAULT_WORD_LENGTH = 5;

            @BeforeEach
            void initGame() {
                WordleWordsmithController wc =
                        new WordleWordsmithController(pc);

                wc.setSecretWord("TRONO");
                pc.startGame();
            }

            @Test
            @DisplayName("la partita è in corso")
            void testGameRunning() {
                assertTrue(pc.isGameRunning());
            }

            @Test
            @DisplayName("lancia WordleGameException se si prova "
                          + "a iniziare un'altra partita")
            void testThrowGameExceptionStartGame() {
                assertThrows(WordleGameException.class,
                        () -> pc.startGame());
            }

            @Test
            @DisplayName("il numero di tentativi è 6")
            void testGetMaxGuesses() {
                assertEquals(DEFAULT_MAX_GUESSES, pc.getMaxGuesses());
            }

            @Test
            @DisplayName("la parola segreta ha lunghezza 5")
            void testGetWordLength() {
                assertEquals(DEFAULT_WORD_LENGTH, pc.getWordLength());
            }

            @Test
            @DisplayName("la parola segreta è \"TRONO\"")
            void testGetGameSecretWord() {
                assertEquals("TRONO", pc.getGameSecretWord());
            }

            @Test
            @DisplayName("si può terminare la partita")
            void testEndGame() {
                assertDoesNotThrow(() -> pc.endGame());
            }

            @Test
            @DisplayName("le celle vuote della board contengono ' '")
            void emptyCellLetterTest() {
                assertEquals(' ', pc.getLetter(0, 0));
            }

            @Test
            @DisplayName("le celle vuote della board hanno colore NO_COLOR")
            void emptyCellColorTest() {
                assertEquals(Color.NO_COLOR, pc.getColor(0, 0));
            }

            @Test
            @DisplayName("lancia ArrayIndexOutOfBoundsException se prova "
                    + "a leggere la lettera fuori dai bordi della board")
            void outOfBoundsGetLetterTest() {
                assertAll(
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> pc.getLetter(-1, 0)),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> pc.getLetter(0, -1)),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> pc.getLetter(0, DEFAULT_WORD_LENGTH)),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> pc.getLetter(DEFAULT_MAX_GUESSES, 0)));
            }

            @Test
            @DisplayName("lancia ArrayIndexOutOfBoundsException se prova "
                    + "a leggere il colore fuori dai bordi della board")
            void outOfBoundsGetColorTest() {
                assertAll(
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> pc.getColor(-1, 0)),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> pc.getColor(0, -1)),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> pc.getColor(0, DEFAULT_WORD_LENGTH)),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> pc.getColor(DEFAULT_MAX_GUESSES, 0)));
            }

            @Nested
            @DisplayName("quando non sono stati effettuati tentativi")
            class NoGuessesDoneTest {

                @Test
                @DisplayName("getGuessResult restituisce falso")
                void testGetGuessResult() {
                    assertFalse(pc.getGuessResult());
                }

                @Test
                @DisplayName("il numero di tentativi rimanenti "
                              + "è pari al numero massimo")
                void testGetNumRemainingGuesses() {
                    assertEquals(pc.getMaxGuesses(),
                            pc.getNumRemainingGuesses());
                }
            }

            @Nested
            @DisplayName("quando viene effettuato un tentativo")
            class PushingNewGuessTest {

                @Test
                @DisplayName("lancia NullPointerException se "
                             + "viene passato null come tentativo")
                void testNullGuess() {
                    assertThrows(NullPointerException.class,
                            () -> pc.guess(null));
                }

                @Test
                @DisplayName("lancia IllegalArgumentException se "
                              + "la lunghezza del tentativo è minore di 5")
                void testTooShortGuess() {
                    assertThrows(IllegalArgumentException.class,
                            () -> pc.guess("CIAO"));
                }

                @Test
                @DisplayName("lancia IllegalArgumentException se "
                              + "la lunghezza del tentativo è maggiore di 5")
                void testTooLongGuess() {
                    assertThrows(IllegalArgumentException.class,
                            () -> pc.guess("SCORPIONE"));
                }

                @Test
                @DisplayName("lancia IllegalArgumentException se "
                             + "il tentativo contiene caratteri non validi")
                void testIllegalGuess() {
                    assertThrows(IllegalArgumentException.class,
                            () -> pc.guess(":-)"));
                }

                @Nested
                @DisplayName("se viene inserito \"PERNO\"")
                class PushingValidGuessTest {

                    @BeforeEach
                    void insertValidGuess() {
                        pc.guess("PERNO");
                    }

                    @Test
                    @DisplayName("il tentativo non è vincente")
                    void testGetGuessResult() {
                        assertFalse(pc.getGuessResult());
                    }

                    @Test
                    @DisplayName("le lettere inserite sono corrette")
                    void testGetLetter() {
                        assertAll(() -> assertEquals('P', pc.getLetter(0, 0)),
                                () -> assertEquals('E', pc.getLetter(0, 1)),
                                () -> assertEquals('R', pc.getLetter(0, 2)),
                                () -> assertEquals('N', pc.getLetter(0, 3)),
                                () -> assertEquals('O', pc.getLetter(0, 4)));
                    }

                    @Test
                    @DisplayName("i colori calcolati sono corretti")
                    void testGetColor() {
                        assertAll(() -> assertEquals(Color.GREY,
                                        pc.getColor(0, 0)),
                                () -> assertEquals(Color.GREY,
                                        pc.getColor(0, 1)),
                                () -> assertEquals(Color.YELLOW,
                                        pc.getColor(0, 2)),
                                () -> assertEquals(Color.GREEN,
                                        pc.getColor(0, 3)),
                                () -> assertEquals(Color.GREEN,
                                        pc.getColor(0, 4)));
                    }

                    @Test
                    @DisplayName("il numero di tentativi "
                                 + "disponibili viene decrementato")
                    void testGetNumRemainingGuesses() {
                        assertEquals(pc.getMaxGuesses() - 1,
                                pc.getNumRemainingGuesses());
                    }
                }

                @Nested
                @DisplayName("se viene inserito \"trono\" "
                        + "(minuscolo, case insensitive)")
                class PushingCorrectGuessTest {

                    @BeforeEach
                    void insertCorrectGuess() {
                        pc.guess("trono");
                    }

                    @Test
                    @DisplayName("il tentativo è vincente")
                    void testGetGuessResult() {
                        assertTrue(pc.getGuessResult());
                    }

                    @Test
                    @DisplayName("le lettere inserite sono corrette")
                    void testGetLetter() {
                        assertAll(() -> assertEquals('T', pc.getLetter(0, 0)),
                                () -> assertEquals('R', pc.getLetter(0, 1)),
                                () -> assertEquals('O', pc.getLetter(0, 2)),
                                () -> assertEquals('N', pc.getLetter(0, 3)),
                                () -> assertEquals('O', pc.getLetter(0, 4)));
                    }

                    @Test
                    @DisplayName("tutte le lettere sono verdi")
                    void testGetColor() {
                        assertAll(() -> assertEquals(Color.GREEN,
                                        pc.getColor(0, 0)),
                                () -> assertEquals(Color.GREEN,
                                        pc.getColor(0, 1)),
                                () -> assertEquals(Color.GREEN,
                                        pc.getColor(0, 2)),
                                () -> assertEquals(Color.GREEN,
                                        pc.getColor(0, 3)),
                                () -> assertEquals(Color.GREEN,
                                        pc.getColor(0, 4)));
                    }

                    @Test
                    @DisplayName("il numero di tentativi"
                                 + " disponibili viene decrementato")
                    void testGetNumRemainingGuesses() {
                        assertEquals(pc.getMaxGuesses() - 1,
                                pc.getNumRemainingGuesses());
                    }
                }

                @Nested
                @DisplayName("quando terminano i tentativi disponibili")
                class NoGuessesLeftTest {

                    @BeforeEach
                    void fillBoard() {
                        pc.guess("FRANA");
                        pc.guess("NUOVA");
                        pc.guess("PROVA");
                        pc.guess("OZONO");
                        pc.guess("EBETE");
                        pc.guess("BREVE");
                    }

                    @Test
                    @DisplayName("lancia WordleGameException se si prova "
                                    + "un ulteriore tentativo")
                    void testThrowGameExceptionGuess() {
                        assertThrows(WordleGameException.class,
                                () -> pc.guess("TRONO"));
                    }

                    @Test
                    @DisplayName("rimangono 0 tentativi")
                    void testGetNumRemainingGuesses() {
                        assertEquals(0, pc.getNumRemainingGuesses());
                    }
                }
            }
        }
    }
}
