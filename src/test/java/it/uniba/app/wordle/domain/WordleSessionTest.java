package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Una nuova sessione")
class WordleSessionTest {

    WordleSession wordleSession;

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
        assertEquals(6, wordleSession.getNMaxGuesses());
    }

    @Test
    @DisplayName("di default prevede parole da 5 lettere")
    void testGetWordLength() {
        assertEquals(5, wordleSession.getWordLength());
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

            WordleGame cg;

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

