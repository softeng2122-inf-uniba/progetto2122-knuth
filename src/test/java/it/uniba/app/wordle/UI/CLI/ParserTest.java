package it.uniba.app.wordle.UI.CLI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Un Parser")
public class ParserTest {

    Parser parser;
    private final String[] EMPTY_ARRAY = new String[0];

    @BeforeEach
    void initParser() {
        parser = new Parser();
    }

    static Stream<Arguments> correctNoArgumentsCommandsParameterProvider() {
        return Stream.of(
                Arguments.of(App.Command.HELP, "/help"),
                Arguments.of(App.Command.ESCI, "/esci"),
                Arguments.of(App.Command.ABBANDONA, "/abbandona"),
                Arguments.of(App.Command.MOSTRA, "/mostra"),
                Arguments.of(App.Command.GIOCA, "/gioca"));
    }

    @Nested
    @DisplayName("quando è correttamente inserito un comando "
            + "che non richiede argomenti")
    class CorrectNoArgumentsCommandsTest {

        private final String parameterProvider = "it.uniba.app.wordle.UI.CLI."
                + "ParserTest#correctNoArgumentsCommandsParameterProvider";

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("restituisce il comando corretto")
        void testCorrectCommand(App.Command command, String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertEquals(command, pt.getCommand());
        }

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("restituisce un array vuoto di argomenti")
        void testCorrectArgs(App.Command command, String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertArrayEquals(EMPTY_ARRAY, pt.getArgs());
        }

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("restituisce un array vuoto di comandi simili")
        void testNoCloseCommands(App.Command command, String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertArrayEquals(EMPTY_ARRAY, pt.getCloseCommandsStrings());
        }

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("non segnala argomenti mancanti")
        void testNoMissingArgs(App.Command command, String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertAll(
                    () -> assertFalse(pt.hasMissingArgs()),
                    () -> assertEquals(0, pt.getNumMissingArgs()));

        }

        /*
        @ParameterizedTest
        @MethodSource("correctNoArgumentsCommandsParameterProvider")
        @DisplayName("riconosce i comandi che non prevedono argomenti")
        void feedTest(App.Command command, String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertAll(
                    () -> assertEquals(command, pt.getCommand()),
                    () -> assertEquals(command.getNumArgs(),
                            pt.getArgs().length + pt.getNumMissingArgs()),
                    () -> assertArrayEquals(EMPTY_ARRAY, pt.getArgs()),
                    () -> assertArrayEquals(EMPTY_ARRAY,
                            pt.getCloseCommandsStrings()),
                    () -> assertFalse(pt.hasMissingArgs()),
                    () -> assertEquals(0, pt.getNumMissingArgs()));
        }*/

    }

    @Nested
    @DisplayName("quando viene inserito un tentativo")
    class GuessTest {


        /*
        @ParameterizedTest
        @ValueSource(strings = {"prova", "Prova", "Treno", "Wf:", "t/"})
        @DisplayName("riconosce che non è stato inserito un comando ma un tentativo")
        void guessTest(String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertAll(
                    () -> assertEquals(App.Command.GUESS, pt.getCommand()),
                    () -> assertEquals(App.Command.GUESS.getNumArgs(),
                            pt.getArgs().length + pt.getNumMissingArgs()),
                    () -> assertEquals(1, pt.getArgs().length),
                    () -> assertEquals(input, pt.getArgs()[0]));
                    () -> assertArrayEquals(EMPTY_ARRAY,
                            pt.getCloseCommandsStrings()),
                    () -> assertFalse(pt.hasMissingArgs()),
                    () -> assertEquals(0, pt.getNumMissingArgs())
            );
        }

         */

        @ParameterizedTest
        @ValueSource(strings = {"prova", "Prova", "Treno", "Wf:", "t/"})
        @DisplayName("restituisce il comando corretto")
        void testCorrectCommand(String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertEquals(App.Command.GUESS, pt.getCommand());
        }

        @ParameterizedTest
        @ValueSource(strings = {"prova", "Prova", "Treno", "Wf:", "t/"})
        @DisplayName("restituisce un array di argomenti"
                    + " contenente solo la parola")
        void testCorrectArgs(String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertAll(
                    () -> assertEquals(1, pt.getArgs().length),
                    () -> assertEquals(input, pt.getArgs()[0]));
        }

        @ParameterizedTest
        @ValueSource(strings = {"prova", "Prova", "Treno", "Wf:", "t/"})
        @DisplayName("restituisce un array vuoto di comandi simili")
        void testNoCloseCommands(String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertArrayEquals(EMPTY_ARRAY, pt.getCloseCommandsStrings());
        }

        @ParameterizedTest
        @ValueSource(strings = {"prova", "Prova", "Treno", "Wf:", "t/"})
        @DisplayName("non segnala argomenti mancanti")
        void testNoMissingArgs(String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertAll(
                    () -> assertFalse(pt.hasMissingArgs()),
                    () -> assertEquals(0, pt.getNumMissingArgs()));
        }
    }



    /*
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\t \t \t"})
    @DisplayName("riconosce i caratteri di spaziatura come comandi SPACE")
    void spaceTest(String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();

        assertAll(
                () -> assertEquals(App.Command.SPACE, pt.getCommand()),
                () -> assertEquals(0, pt.getArgs().length),
                () -> assertArrayEquals(EMPTY_ARRAY,
                                   pt.getCloseCommandsStrings()),
                () -> assertFalse(pt.hasMissingArgs()),
                () -> assertEquals(0, pt.getNumMissingArgs())
        );
    }

     */

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\t \t \t"})
    @DisplayName("riconosce i caratteri di spaziatura come comandi SPACE")
    void spaceTest(String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();

        assertEquals(App.Command.SPACE, pt.getCommand());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/ciao", "/", "//", "/muori", "/hhhellp"})
    @DisplayName("riconosce comandi invalidi")
    void invalidTest(String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();

        assertEquals(App.Command.INVALID, pt.getCommand());
    }

    /*
    @Test
    @DisplayName("permette di riconoscere l'inserimento di una parola"
                 + "segreta con /nuova treno ove treno è la parola segreta")
    void nuovaTest() {
        parser.feed("/nuova treno");
        ParserToken pt = parser.getParserToken();

        assertAll(
                () -> assertEquals(App.Command.NUOVA, pt.getCommand()),
                () -> assertEquals(App.Command.NUOVA.getNumArgs(),
                             pt.getArgs().length + pt.getNumMissingArgs()),
                () -> assertEquals(1, pt.getArgs().length),
                () -> assertEquals("treno", pt.getArgs()[0]),
                () -> assertArrayEquals(EMPTY_ARRAY,
                                   pt.getCloseCommandsStrings()),
                () -> assertFalse(pt.hasMissingArgs()),
                () -> assertEquals(0, pt.getNumMissingArgs())
        );
    }

     */

    @Nested
    @DisplayName("al comando \"/nuova treno\"")
    class NuovaCorrectTest {

        private ParserToken pt;

        @BeforeEach
        void setup() {
            parser.feed("/nuova treno");
            pt = parser.getParserToken();
        }

        @Test
        @DisplayName("restituisce il comando corretto")
        void testCorrectCommand() {
            assertEquals(App.Command.NUOVA, pt.getCommand());
        }

        @Test
        @DisplayName("restituisce un array di argomenti contenente solo \"treno\"")
        void testCorrectArgs() {
            assertAll(
                    () -> assertEquals(1, pt.getArgs().length),
                    () -> assertEquals("treno", pt.getArgs()[0]));
        }

        @Test
        @DisplayName("non segnala argomenti mancanti")
        void NoMissingArgs() {
            assertAll(
                    () -> assertFalse(pt.hasMissingArgs()),
                    () -> assertEquals(0, pt.getNumMissingArgs()));
        }

    }
    /*
    @Test
    @DisplayName("permette di riconoscere l'inserimento del comando"
                 + "/nuova senza argomenti")
    void nuovaMissingArgTest() {
        parser.feed("/nuova ");
        ParserToken pt = parser.getParserToken();

        assertAll(
                () -> assertEquals(App.Command.NUOVA, pt.getCommand()),
                () -> assertEquals(0, pt.getArgs().length),
                () -> assertArrayEquals(EMPTY_ARRAY,
                                   pt.getCloseCommandsStrings()),
                () -> assertTrue(pt.hasMissingArgs()),
                () -> assertEquals(1, pt.getNumMissingArgs())
        );
    }

     */

    @Nested
    @DisplayName("al comando \"/nuova\" (senza argomenti)")
    class NuovaMissingArgsTest {

        private ParserToken pt;

        @BeforeEach
        void setup() {
            parser.feed("/nuova ");
            pt = parser.getParserToken();
        }

        @Test
        @DisplayName("restituisce il comando corretto")
        void testCorrectCommand() {
            assertEquals(App.Command.NUOVA, pt.getCommand());
        }

        @Test
        @DisplayName("restituisce un array vuoto di argomenti")
        void testCorrectArgs() {
            assertEquals(0, pt.getArgs().length);
        }

        @Test
        @DisplayName("restituisce un array vuoto di comandi simili")
        void testNoCloseCommands() {

            assertArrayEquals(EMPTY_ARRAY, pt.getCloseCommandsStrings());
        }

        @Test
        @DisplayName("segnala un argomento mancante")
        void OneMissingArg() {

            assertAll(
                    () -> assertTrue(pt.hasMissingArgs()),
                    () -> assertEquals(1, pt.getNumMissingArgs()));
        }

    }

    // test altri metodi
    private static Stream<Arguments> closeCommandParameterProvider() {
        return Stream.of(
                Arguments.of(new String[] {App.Command
                                           .HELP.toString()}, "/helpa"),
                Arguments.of(new String[] {App.Command
                                           .ESCI.toString()}, "/eci"),
                Arguments.of(new String[] {App.Command
                                         .ABBANDONA.toString()}, "/abbandoma"),
                Arguments.of(new String[] {App.Command
                                          .MOSTRA.toString()}, "/mostro"),
                Arguments.of(new String[] {App.Command
                                          .GIOCA.toString()}, "/gicoa"));
    }

    @ParameterizedTest
    @MethodSource("closeCommandParameterProvider")
    @DisplayName("riconosce il comando più simile all'input inserito")
    void closeCommandTest(String[] expectedCloseCommands, String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();

        assertAll(
                () -> assertEquals(App.Command.INVALID, pt.getCommand()),
                () -> assertArrayEquals(EMPTY_ARRAY, pt.getArgs()),
                () -> assertArrayEquals(expectedCloseCommands,
                                        pt.getCloseCommandsStrings()),
                () -> assertFalse(pt.hasMissingArgs()),
                () -> assertEquals(0, pt.getNumMissingArgs()));
    }

    private static Stream<Arguments> closeCommand2ParameterProvider() {
        return Stream.of(
                Arguments.of(new String[] {App.Command.ESCI.toString(),
                         App.Command.HELP.toString()}, "/eli"));
    }

    @ParameterizedTest
    @MethodSource("closeCommand2ParameterProvider")
    @DisplayName("riconosce i comandi simili all'input inserito")
    void closeCommand2Test(String[] expectedCloseCommands, String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();

        assertAll(
                () -> assertEquals(App.Command.INVALID, pt.getCommand()),
                () -> assertArrayEquals(EMPTY_ARRAY, pt.getArgs()),
                () -> assertArrayEquals(expectedCloseCommands,
                                        pt.getCloseCommandsStrings()),
                () -> assertFalse(pt.hasMissingArgs()),
                () -> assertEquals(0, pt.getNumMissingArgs()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/help nuova cosa si", "/abbandona la partita ",
                            "/nuova input parola", "tento in ",
                            "/elp aiuto"})
    @DisplayName("ignora eventuali argomenti in eccesso rispetto "
                 + "al numero di argomenti attesi dal comando")
    void excessiveArgsTest(String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();

        assertEquals(pt.getCommand().getNumArgs(), pt.getArgs().length);
    }
}
