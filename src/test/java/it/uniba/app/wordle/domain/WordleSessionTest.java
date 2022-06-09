package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName("Una nuova sessione")
class WordleSessionTest {

    private WordleSession wordleSession;
    private static final int DEFAULT_MAX_GUESSES = 6;
    private static final int DEFAULT_WORD_LENGTH = 5;

    @BeforeEach
    void createNewWordleSession() {
        wordleSession = new WordleSession();
    }

    @Test
    @DisplayName("non ha alcuna partita in corso")
    void testAbsentGame() {
        assertAll(() -> assertFalse(wordleSession.isGameRunning()),
                  () -> assertNull(wordleSession.getCurrentGame()));
    }

    @Test
    @DisplayName("non ha la parola segreta impostata")
    void testSecretWordNull() {
        assertAll(() -> assertFalse(wordleSession.hasSecretWord()),
                  () -> assertNull(wordleSession.getSecretWord()));
    }

    @Test
    @DisplayName("di default prevede partite da 6 tentativi")
    void testGetNMaxGuesses() {
        assertEquals(DEFAULT_MAX_GUESSES, wordleSession.getNMaxGuesses());
    }

    @Test
    @DisplayName("di default prevede parole da 5 lettere")
    void testGetWordLength() {
        assertEquals(DEFAULT_WORD_LENGTH, wordleSession.getWordLength());
    }

    @Nested
    @DisplayName("scegliendo \"TESTA\" come parola segreta")
    class AfterSettingSecretWordTest {

        @BeforeEach
        void setSecretWord() {
            wordleSession.setSecretWord("TESTA");
        }

        @Test
        @DisplayName("viene impostata correttamente")
        void testGetSecretWord() {
            assertAll(() -> assertTrue(wordleSession.hasSecretWord()),
                      () -> assertEquals("TESTA",
                                         wordleSession.getSecretWord()));
        }

        @Nested
        @DisplayName("creando una nuova partita")
        class AfterSettingCurrentGameTest {

            private WordleGame cg;

            @BeforeEach
            void insertCurrentGame() {
                cg = new WordleGame(wordleSession.getSecretWord(),
                        wordleSession.getNMaxGuesses(),
                        wordleSession.getWordLength());
                wordleSession.setCurrentGame(cg);
            }

            @Test
            @DisplayName("è impostata come partita corrente")
            void testCorrectGame() {
                assertAll(() -> assertTrue(wordleSession.isGameRunning()),
                         () -> assertSame(cg, wordleSession.getCurrentGame()));
            }

            @Test
            @DisplayName("la sua parola segreta è \"TESTA\"")
            void testSameSecretWord() {
                assertEquals("TESTA", cg.getSecretWord());
            }
        }
    }
}

