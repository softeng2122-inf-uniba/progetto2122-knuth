package it.uniba.app.wordle.UI.CLI;

import com.sun.tools.jdeprscan.scan.Scan;
import it.uniba.app.wordle.domain.WordlePlayerController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("eseguo AppTest")
class AppTest {

    static Field appPlayerController;
    static Field appWordsmithController;
    static Field appKeyboard;
    static Field appConsole;
    static Field appRunning;
    private static ByteArrayOutputStream outContent;
    private final PrintStream originalOut = System.out;
    private ByteArrayInputStream inContent;
    private final InputStream originalIn = System.in;

    private final String[] EMPTY_ARRAY_STRING = new String[0];


    @BeforeAll
    public static void setup() throws NoSuchFieldException, IllegalAccessException {
        outContent = new ByteArrayOutputStream();
        // System.setOut(new PrintStream(outContent));


        appPlayerController = App.class.getDeclaredField("PLAYER_CONTROLLER");
        appPlayerController.setAccessible(true);

        appWordsmithController = App.class.getDeclaredField("WORDSMITH_CONTROLLER");
        appWordsmithController.setAccessible(true);

        appKeyboard = App.class.getDeclaredField("keyboard");
        appKeyboard.setAccessible(true);
        appConsole = App.class.getDeclaredField("console");
        appConsole.setAccessible(true);

        appRunning = App.class.getDeclaredField("running");
        appRunning.setAccessible(true);

        outContent = new ByteArrayOutputStream();
        appConsole.set(null, new WordlePrinter(new OutputStreamWriter(outContent),
                (WordlePlayerController) appPlayerController.get(null)));

        //inContent = new ByteArrayInputStream();
        // appKeyboard.set(null, new Scanner(new InputStreamReader(inContent)));
        //appConsole.set(null, new WordlePrinter(new OutputStreamWriter(outContent),
        //                (WordlePlayerController) appPlayerController.get(null)));
    }


    @Test
    @DisplayName("/help non lancia eccezioni")
    void testHelp() {
        assertDoesNotThrow(() -> App.Command.HELP.execute(EMPTY_ARRAY_STRING));
    }


    @Nested
    @DisplayName("quando si imposta la parola segreta TRENO")
    class SecretWordSetTest {
        @BeforeEach
        void setupSecretWord() throws IllegalAccessException {
            String[] args = {"TRENO"};
            //App.Command.NUOVA.execute(args);
            // String string = "/nuova treno";
            App.Command.NUOVA.execute(args);
        }


        @Test
        @DisplayName("effettuo tentativo non corretto")
        void testGuess() {
            String[] args = {"TRAMA"};
            assertDoesNotThrow(() -> App.Command.GUESS.execute(args));
        }

        @Nested
        @DisplayName("quando si avvia la partita")
        class StartGameTest {
            @BeforeEach
            void startGame() {
                App.Command.GIOCA.execute(EMPTY_ARRAY_STRING);
            }
            @Test
            @DisplayName("test abbandona")
            void testMain() throws IllegalAccessException{
                // outContent = new ByteArrayOutputStream();
                // System.setOut(new PrintStream(outContent));
                String s = "si\n";
                inContent = new ByteArrayInputStream(s.getBytes());
                System.setIn(inContent);

                appKeyboard.set(null, new Scanner(inContent));

                assertDoesNotThrow(() -> App.Command.ABBANDONA.execute(EMPTY_ARRAY_STRING));
            }

            @Test
            @DisplayName("effettuo un tentativo non corretto")
            void testGuess() {
                String[] args = {"TERNA"};
                assertDoesNotThrow(() -> App.Command.GUESS.execute(args));
            }

            @Test
            @DisplayName("effettuo un tentativo corretto")
            void testCorrectGuess() {
                String[] args = {"TRENO"};
                assertDoesNotThrow(() -> App.Command.GUESS.execute(args));
            }

            @ParameterizedTest
            @ValueSource(strings = {"CIAO", "PAROLA", "L£TT0"})
            @DisplayName("effettuo un tentativo invalido non lancia ecc")
            void testInvalidGuess(String arg) {
                String[] args = {arg};

                assertDoesNotThrow(() -> App.Command.GUESS.execute(args));
            }

            @Test
            @DisplayName("se riempio la board poi non vado più avanti")
            void testFillBoard() {
                String[] args = {"NUOVO", "EBETE", "BREVE", "SCAFO", "PALLA", "LETTO"};

                for(String arg : args) {
                    App.Command.GUESS.execute(new String[]{arg});
                }

                assertDoesNotThrow(() -> App.Command.GUESS.execute(new String[]{"TRENO"}));
            }
        }


        @Test
        @Order(2)
        @DisplayName("ritrova la parola segreta con \"MOSTRA\"")
        void testMostra() throws IllegalAccessException {
            outContent = new ByteArrayOutputStream();
            appConsole.set(null, new WordlePrinter(new OutputStreamWriter(outContent),
                    (WordlePlayerController) appPlayerController.get(null)));

            App.Command.MOSTRA.execute(EMPTY_ARRAY_STRING);
            String outputLine = outContent.toString().trim();
            assertEquals("Parola segreta: TRENO", outputLine);
        }

    }

    static Stream<Arguments> commandProvider() {
        return Stream.of(
                Arguments.of(App.Command.SPACE),
                Arguments.of(App.Command.INVALID),
                Arguments.of(App.Command.HELP),
                Arguments.of(App.Command.MOSTRA),
                Arguments.of(App.Command.GIOCA),
                Arguments.of(App.Command.ABBANDONA));
    }

    @ParameterizedTest
    @MethodSource("commandProvider")
    @DisplayName("l'esecuzione di un comando non lancia eccezioni")
    void testGiocaNoWord(App.Command command) {
        assertDoesNotThrow(() -> command.execute(EMPTY_ARRAY_STRING));
    }


    @Test
    @DisplayName("encooding test")
    void testEncodingUTF8() {
        System.setProperty("file.encoding", "UTF-8");
        Charset encoding = StandardCharsets.UTF_8;

        assertAll(
                () -> assertDoesNotThrow(App::getSystemEncoding),
                () -> assertEquals(encoding, App.getSystemEncoding())
        );
    }

    @Test
    @DisplayName("encooding test")
    void testEncodingUTF16() {
        System.setProperty("file.encoding", "UTF-16");
        Charset encoding = StandardCharsets.UTF_16;

        assertAll(
                () -> assertDoesNotThrow(App::getSystemEncoding),
                () -> assertEquals(encoding, App.getSystemEncoding())
        );
    }

    @Test
    @DisplayName("encooding test")
    void testEncodingInvalid() {
        System.setProperty("file.encoding", "US-ASCII");

        assertThrows(UnsupportedEncodingException.class, App::getSystemEncoding);
    }

    @Test
    @Disabled
    @DisplayName("test main")
    void testMain() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String s = "no\n";
        inContent = new ByteArrayInputStream(s.getBytes());
        System.setIn(inContent);

        assertDoesNotThrow(() -> App.main(new String[]{"--help"}));
    }

    @Test
    @DisplayName("esci test")
    void testEsci() throws IllegalAccessException{
        //outContent = new ByteArrayOutputStream();
        //System.setOut(new PrintStream(outContent));
        String s = "/esci\nsi\n";
        inContent = new ByteArrayInputStream(s.getBytes());
        System.setIn(inContent);

        appKeyboard.set(null, new Scanner(inContent));

        assertAll(() -> assertDoesNotThrow(() -> App.Command.ESCI.execute(EMPTY_ARRAY_STRING)),
                () -> assertFalse((boolean) appRunning.get(null)));
    }

    @Test
    @DisplayName("test invalid")
    void testInvalidWithArgs() {
        assertDoesNotThrow(() -> App.Command.INVALID.execute(new String[]{"ESCI"}));
        assertDoesNotThrow(() -> App.Command.INVALID.execute(new String[]{"ESCI", "HELP"}));
    }


}
