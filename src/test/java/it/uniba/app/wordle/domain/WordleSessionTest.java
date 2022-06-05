package it.uniba.app.wordle.domain;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Nuova sessione")
class WordleSessionTest {

    static WordleSession wordleSession;

    @DisplayName("Istanziando wordleSession")
    static class wordleSessionIstantiated {

        @BeforeEach
        void createNewWordleSession() {
            wordleSession = new WordleSession();
        }

        @Test
        @DisplayName("currentGame è null")
        void testCurrentGameNull() {
            assertNull(wordleSession.getCurrentGame());
        }

        // verificare che inserendo una nuova partita corrente questa corrisponde a quella che va ad avvalorare l'oggetto

        @Test
        @DisplayName("la parola segreta è null")
        void testSecretWordNull() {
            assertNull(wordleSession.getSecretWord());
        }

        // inserire la parola segreta: senza partita in corso non posso inserire una nuova parola segreta

        @Test
        @DisplayName("Il numero massimo di tentativi è 6")
        void testGetnMaxGuesses() {
            assertEquals(6, wordleSession.getnMaxGuesses());
        }

        @Test
        @DisplayName("Il numero di lettere dei tentativi è 5")
        void testgetWordLength() {
            assertEquals(5, wordleSession.getWordLength());
        }

        @Test
        @DisplayName("La parola segreta non è inserita")
        void testHasNotSecretWord() {
            assertFalse(wordleSession.hasSecretWord());
        }

        @Test
        @DisplayName("Nessuna partita è in corso")
        void testIsGameNotRunning() {
            assertFalse(wordleSession.isGameRunning());
        }


        @Nested
        @DisplayName("Inserendo la partita corrente cg, con la parola segreta \"TESTA\"")
        class AfterSettingCurrentGame {

            WordleGame cg = new WordleGame("TESTA", 6, 5);

            @BeforeEach
            void insertCurrentGame() {
                wordleSession.setCurrentGame(cg);
                wordleSession.setSecretWord(cg.getSecretWord());
            }

            @Test
            @DisplayName("La partita corrente corrisponde a cg")
            void testGetCurrentGame() {
                assertSame(cg, wordleSession.getCurrentGame());
            }

            @Test
            @DisplayName("La paola corrente corrisponde a \"TESTA\"")
            void testGetSecretWord() {
                assertEquals(cg.getSecretWord(), wordleSession.getSecretWord());
            }

            @Test
            @DisplayName("C'è una parola corrente")
            void testhasSecretWord() {
                assertTrue(wordleSession.hasSecretWord());
            }

            @Test
            @DisplayName("C'è una partita in corso")
            void testIsGameRunning() {
                assertTrue(wordleSession.isGameRunning());
            }
        }
    }
}

