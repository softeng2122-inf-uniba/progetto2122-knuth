package it.uniba.app.wordle.UI.CLI;

import it.uniba.app.wordle.domain.WordlePlayerController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("La classe App")
class AppTest {

    // attributi privati e statici di App a cui serve accedere
    /** Field per l'accesso all'attributo static final
     * PLAYER_CONTROLLER di App. */
    private static Field appPlayerController;
    /** Field per l'accesso all'attributo static
     * keyboard di App. */
    private static Field appKeyboard;
    /** Field per l'accesso all'attributo static
     * console di App. */
    private static Field appConsole;
    /** Field per l'accesso all'attributo static
     * running (boolean) di App. */
    private static Field appRunning;

    /** Stream di output per il testing. */
    private static ByteArrayOutputStream outContent;

    /** Array di stringhe vuoto. */
    private static final String[] EMPTY_ARRAY_STRING = new String[0];


    // crea un nuovo ByteArrayOutputStream e lo utilizza
    // come output stream per appConsole
    static ByteArrayOutputStream newOutputStream()
                                        throws IllegalAccessException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        appConsole.set(null, new WordlePrinter(
                new OutputStreamWriter(out),
                (WordlePlayerController) appPlayerController.get(null)));

        return out;
    }

    // utilizza la stringa input come input stream per appKeyboard
    static void redirectInputStream(final String input)
                                        throws IllegalAccessException {

        ByteArrayInputStream in =
                new ByteArrayInputStream(input.getBytes());

        appKeyboard.set(null, new Scanner(in));

    }


    @BeforeAll
    public static void setup()
            throws NoSuchFieldException, IllegalAccessException {

        // Utilizza reflection per rendere accessibili gli attributi privati
        appPlayerController = App.class.getDeclaredField("PLAYER_CONTROLLER");
        appPlayerController.setAccessible(true);

        appKeyboard = App.class.getDeclaredField("keyboard");
        appKeyboard.setAccessible(true);

        appConsole = App.class.getDeclaredField("console");
        appConsole.setAccessible(true);

        appRunning = App.class.getDeclaredField("running");
        appRunning.setAccessible(true);

        // inizializza l'outContent come stream di output
        outContent = newOutputStream();

    }

    @Nested
    @DisplayName("quando viene impostata la parola segreta TRENO")
    class SecretWordSetTest {

        @BeforeEach
        void setupSecretWord() {
            App.Command.NUOVA.execute(new String[]{"TRENO"});
        }

        @Test
        @DisplayName("la stampa correttamente a seguito "
                    + "del comando \"MOSTRA\"")
        void testMostra() throws IllegalAccessException {

            // serve ignorare l'output accumulato nel BeforeEach
            // quindi viene creato un nuovo outputStream e
            // si legge il suo contenuto
            outContent = newOutputStream();

            App.Command.MOSTRA.execute(EMPTY_ARRAY_STRING);
            String outputLine = outContent.toString().trim();
            assertEquals("Parola segreta: TRENO", outputLine);
        }


        @Nested
        @DisplayName("quando si avvia la partita")
        class StartGameTest {

            @BeforeEach
            void startGame() {
                App.Command.GIOCA.execute(EMPTY_ARRAY_STRING);
            }
            @Test
            @DisplayName("non lancia eccezioni al comando \"ABBANDONA\"")
            void testAbbandona() throws IllegalAccessException {

                // prima di confermare scrivo un input non previsto
                // per verificare che sia correttamente gestito
                redirectInputStream("forse\nsi\n");

                assertDoesNotThrow(() -> App.Command.ABBANDONA
                                            .execute(EMPTY_ARRAY_STRING));
            }

            @Test
            @DisplayName("non lancia eccezioni se viene inserito un tentativo "
                        + "valido")
            void testValidGuess() {
                assertDoesNotThrow(
                        () -> App.Command.GUESS
                                .execute(new String[]{"TERNA"}));
            }

            @Test
            @DisplayName("non lancia eccezioni se viene indovinata la parola "
                        + "segreta")
            void testCorrectGuess() {
                assertDoesNotThrow(
                        () -> App.Command.GUESS
                                .execute(new String[]{"TRENO"}));
            }

            @ParameterizedTest
            @ValueSource(strings = {"CIAO", "PAROLA", "L£TT0"})
            @DisplayName("non lancia eccezioni se viene inserito un tentativo "
                        + "non valido")
            void testInvalidGuess(final String arg) {
                assertDoesNotThrow(
                        () -> App.Command.GUESS
                                .execute(new String[]{arg}));
            }

            @Test
            @DisplayName("non lancia eccezioni se viene inserito un tentativo "
                        + "quando sono terminati i tentativi disponibili")
            void testFillBoard() {
                String[] args = {"NUOVO", "EBETE", "BREVE",
                                 "SCAFO", "PALLA", "LETTO"};

                for (String arg : args) {
                    App.Command.GUESS.execute(new String[]{arg});
                }

                assertDoesNotThrow(() -> App.Command.GUESS
                                            .execute(new String[]{"TRENO"}));
            }
        }
    }

    // comandi che non richiedono argomenti né interazioni da parte dell'utente
    static Stream<Arguments> commandProvider() {
        return Stream.of(
                Arguments.of(App.Command.SPACE),
                Arguments.of(App.Command.INVALID),
                Arguments.of(App.Command.HELP),
                Arguments.of(App.Command.MOSTRA),
                Arguments.of(App.Command.GIOCA),
                // non richiede interazione in quanto non è attiva la partita
                Arguments.of(App.Command.ABBANDONA));

    }

    @ParameterizedTest
    @MethodSource("commandProvider")
    @DisplayName("non lancia eccezioni all'esecuzione di un comando "
                + "che non richiede interazioni")
    void testNoInteractionCommand(final App.Command command) {
        assertDoesNotThrow(() -> command.execute(EMPTY_ARRAY_STRING));
    }

    @Test
    @DisplayName("non lancia eccezioni al comando \"MOSTRA\" "
                + "se non è stata impostata la parola segreta")
    void testNuovaAbsentSecretWord() {
        assertDoesNotThrow(() -> App.Command.MOSTRA
                                    .execute(EMPTY_ARRAY_STRING));
    }

    @Test
    @DisplayName("non lancia eccezioni se viene inserito un tentativo "
            + "ma non è stata impostata la parola segreta")
    void testGuessAbsentSecretWord() {
        assertDoesNotThrow(() -> App.Command.GUESS
                                    .execute(new String[]{"PROVA"}));
    }

    @Test
    @DisplayName("non lancia eccezioni al comando \"ESCI\" e imposta "
            + "il flag \"running\" a false")
    void testEsci() throws IllegalAccessException {
        redirectInputStream("forse\nsi\n");

        assertAll(
                () -> assertDoesNotThrow(() -> App.Command.ESCI
                        .execute(EMPTY_ARRAY_STRING)),
                () -> assertFalse((boolean) appRunning.get(null)));
    }

    @Test
    @DisplayName("non lancia eccezioni se un comando invalido ha "
            + "comandi simili")
    void testInvalidWithArgs() {
        assertDoesNotThrow(() -> App.Command.INVALID
                                    .execute(new String[]{"ESCI"}));
        assertDoesNotThrow(() -> App.Command.INVALID.
                                    execute(new String[]{"ESCI", "HELP"}));
    }


    @Test
    @DisplayName("non lancia eccezioni e restituisce il Charset UTF-8 "
            + "se tale è la codifica del sistema")
    void testEncodingUTF8() {
        System.setProperty("file.encoding", "UTF-8");
        Charset encoding = StandardCharsets.UTF_8;

        assertAll(
                () -> assertDoesNotThrow(App::getSystemEncoding),
                () -> assertEquals(encoding, App.getSystemEncoding())
        );
    }

    @Test
    @DisplayName("non lancia eccezioni e restituisce il Charset UTF-16 "
                + "se tale è la codifica del sistema")
    void testEncodingUTF16() {
        System.setProperty("file.encoding", "UTF-16");
        Charset encoding = StandardCharsets.UTF_16;

        assertAll(
                () -> assertDoesNotThrow(App::getSystemEncoding),
                () -> assertEquals(encoding, App.getSystemEncoding())
        );
    }

    @Test
    @DisplayName("lancia UnsupportedEncodingException se la codifica "
            + "del sistema è diversa da UTF-8 e UTF-16")
    void testInvalidEncoding() {
        // esempio: utilizziamo US-ASCII
        System.setProperty("file.encoding", "US-ASCII");

        assertThrows(UnsupportedEncodingException.class,
                                App::getSystemEncoding);
    }

}
