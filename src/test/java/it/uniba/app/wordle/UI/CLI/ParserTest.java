package it.uniba.app.wordle.UI.CLI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Un Parser")
class ParserTest {

    private Parser parser;
    private static final String[] EMPTY_ARRAY = new String[0];

    @BeforeEach
    void initParser() {
        parser = new Parser();
    }

    static Stream<Arguments> correctNoArgsProvider() {
        return Stream.of(
                Arguments.of(App.Command.HELP, "/help"),
                Arguments.of(App.Command.ESCI, "/esci"),
                Arguments.of(App.Command.ABBANDONA, "/abbandona"),
                Arguments.of(App.Command.MOSTRA, "/mostra"),
                Arguments.of(App.Command.GIOCA, "/gioca"));
    }

    static Stream<Arguments> guessProvider() {
        return Stream.of(
                Arguments.of("prova"),
                Arguments.of("Prova"),
                Arguments.of("Parola"),
                Arguments.of("Wf:"),
                Arguments.of("t/"));
    }

    @Nested
    @DisplayName("quando è correttamente inserito un comando "
            + "che non richiede argomenti")
    class CorrectNoArgumentsCommandsTest {

        private final String parameterProvider = "it.uniba.app.wordle.UI.CLI."
                + "ParserTest#correctNoArgsProvider";

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("restituisce il comando corretto")
        void testCorrectCommand(final App.Command command, final String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertEquals(command, pt.getCommand());
        }

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("restituisce un array vuoto di argomenti")
        void testCorrectArgs(final App.Command command, final String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertArrayEquals(EMPTY_ARRAY, pt.getArgs());
        }

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("restituisce un array vuoto di comandi simili")
        void testNoCloseCommands(final App.Command command,
                                 final String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertArrayEquals(EMPTY_ARRAY, pt.getCloseCommandsStrings());
        }

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("non segnala argomenti mancanti")
        void testNoMissingArgs(final App.Command command, final String input) {
            parser.feed(input);
            ParserToken pt = parser.getParserToken();

            assertAll(
                    () -> assertFalse(pt.hasMissingArgs()),
                    () -> assertEquals(0, pt.getNumMissingArgs()));

        }
    }

    @Nested
    @DisplayName("quando viene inserito un tentativo")
    class GuessTest {

        private final String parameterProvider = "it.uniba.app.wordle.UI.CLI."
                + "ParserTest#guessProvider";
        private ParserToken pt;

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("restituisce il comando corretto (GUESS)")
        void testCorrectCommand(final String input) {
            parser.feed(input);
            pt = parser.getParserToken();

            assertEquals(App.Command.GUESS, pt.getCommand());
        }

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("restituisce un array di argomenti"
                    + " contenente solo la parola del tentativo")
        void testCorrectArgs(final String input) {
            parser.feed(input);
            pt = parser.getParserToken();

            assertAll(
                    () -> assertEquals(1, pt.getArgs().length),
                    () -> assertEquals(input, pt.getArgs()[0]));
        }

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("restituisce un array vuoto di comandi simili")
        void testNoCloseCommands(final String input) {
            parser.feed(input);
            pt = parser.getParserToken();

            assertArrayEquals(EMPTY_ARRAY, pt.getCloseCommandsStrings());
        }

        @ParameterizedTest
        @MethodSource(parameterProvider)
        @DisplayName("non segnala argomenti mancanti")
        void testNoMissingArgs(final String input) {
            parser.feed(input);
            pt = parser.getParserToken();

            assertAll(() -> assertFalse(pt.hasMissingArgs()),
                      () -> assertEquals(0, pt.getNumMissingArgs()));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\t \t \t"})
    @DisplayName("riconosce i caratteri di spaziatura come comandi SPACE")
    void spaceTest(final String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();

        assertEquals(App.Command.SPACE, pt.getCommand());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/ciao", "/", "//", "/muori", "/hhhellp"})
    @DisplayName("riconosce comandi invalidi")
    void invalidTest(final String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();

        assertEquals(App.Command.INVALID, pt.getCommand());
    }

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
        @DisplayName("restituisce il comando corretto (NUOVA)")
        void testCorrectCommand() {
            assertEquals(App.Command.NUOVA, pt.getCommand());
        }

        @Test
        @DisplayName("restituisce un array di argomenti "
                    + "contenente solo \"treno\"")
        void testCorrectArgs() {
            assertAll(
                    () -> assertEquals(1, pt.getArgs().length),
                    () -> assertEquals("treno", pt.getArgs()[0]));
        }

        @Test
        @DisplayName("non segnala argomenti mancanti")
        void testNoMissingArgs() {
            assertAll(
                    () -> assertFalse(pt.hasMissingArgs()),
                    () -> assertEquals(0, pt.getNumMissingArgs()));
        }

    }

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
        @DisplayName("restituisce il comando corretto (NUOVA)")
        void testCorrectCommand() {
            assertEquals(App.Command.NUOVA, pt.getCommand());
        }

        @Test
        @DisplayName("restituisce un array vuoto di argomenti")
        void testCorrectArgs() {
            assertEquals(0, pt.getArgs().length);
        }


        @Test
        @DisplayName("segnala un argomento mancante")
        void testOneMissingArg() {
            assertAll(() -> assertTrue(pt.hasMissingArgs()),
                      () -> assertEquals(1, pt.getNumMissingArgs()));
        }

    }

    // per il testing sui comandi simili
    private static Stream<Arguments> closeCommandParameterProvider() {
        return Stream.of(
                Arguments.of(new String[] {App.Command
                                           .HELP.toString()}, "/helpa"),
                Arguments.of(new String[] {App.Command
                                           .ESCI.toString()}, "/eci"),
                Arguments.of(new String[] {App.Command
                                         .ABBANDONA.toString()}, "/abandoma"),
                Arguments.of(new String[] {App.Command
                                          .MOSTRA.toString()}, "/mostro"),
                Arguments.of(new String[] {App.Command
                                          .GIOCA.toString()}, "/gicoa"),
                Arguments.of(new String[] {App.Command.HELP.toString(),
                        App.Command.ESCI.toString()}, "/eli"));
    }

    @ParameterizedTest
    @MethodSource("closeCommandParameterProvider")
    @DisplayName("se viene inserito un comando invalido simile a qualche "
                + "comando esistente allora l'array di comandi simili "
                + "contiene i giusti elementi ")
    void closeCommandTest(final String[] expectedCloseCommands,
                          final String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();

        List<String> expected = Arrays.asList(expectedCloseCommands);
        List<String> actual = Arrays.asList(pt.getCloseCommandsStrings());

        // controllo sugli elementi dei due array, devono essere gli stessi
        // anche se l'ordine è diverso (questo tipo di controllo è necessario
        // solo quando l'array ha più di un elemento, caso che si può
        // verificare, e funziona in modo analogo a un assertArrayEquals
        // nel caso in cui i due array da confrontare contengano un solo
        // elemento
        assertTrue(expected.size() == actual.size()
                    && expected.containsAll(actual)
                    && actual.containsAll(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/help nuova cosa si", "/abbandona la partita ",
                            "/nuova input parola", "tento in ",
                            "/elp aiuto"})
    @DisplayName("ignora eventuali argomenti in eccesso rispetto "
                 + "al numero di argomenti attesi dal comando")
    void excessiveArgsTest(final String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();

        assertEquals(pt.getCommand().getNumArgs(), pt.getArgs().length);
    }
}
