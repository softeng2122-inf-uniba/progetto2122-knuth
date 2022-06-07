package it.uniba.app.wordle.UI.CLI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class ParserTest {

    Parser parser;

    @BeforeEach
    void initParser() {
        parser = new Parser();
    }


    private static Stream<Arguments> feedTestParameterProvider() {
        return Stream.of(
                Arguments.of(App.Command.HELP, "/help"),
                Arguments.of(App.Command.ESCI, "/esci"),
                Arguments.of(App.Command.ABBANDONA, "/abbandona"),
                Arguments.of(App.Command.MOSTRA, "/mostra"),
                Arguments.of(App.Command.GIOCA, "/gioca"));
    }

    @ParameterizedTest
    @MethodSource("feedTestParameterProvider")
    void feedTest(App.Command command, String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();
        final String[] EMPTY_ARRAY = new String[0];
        assertAll(
                () -> assertEquals(command, pt.getCommand()),
                () -> assertEquals(command.getNumArgs(),
                        pt.getArgs().length + pt.getNumMissingArgs()),
                () -> assertArrayEquals(EMPTY_ARRAY, pt.getArgs()),
                () -> assertArrayEquals(EMPTY_ARRAY, pt.getCloseCommandsStrings()),
                () -> assertFalse(pt.hasMissingArgs()),
                () -> assertEquals(0, pt.getNumMissingArgs()));
    }


    @ParameterizedTest
    @ValueSource(strings = {"prova", "Prova", "Treno", "Wf:", "t/"})
    void guessTest(String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();
        final String[] EMPTY_ARRAY = new String[0];
        assertAll(
                () -> assertEquals(App.Command.GUESS, pt.getCommand()),
                () -> assertEquals(App.Command.GUESS.getNumArgs(),
                        pt.getArgs().length + pt.getNumMissingArgs()),
                () -> assertEquals(1, pt.getArgs().length),
                () -> assertEquals(input, pt.getArgs()[0]),
                () -> assertArrayEquals(EMPTY_ARRAY, pt.getCloseCommandsStrings()),
                () -> assertFalse(pt.hasMissingArgs()),
                () -> assertEquals(0, pt.getNumMissingArgs())
        );
    }

    //testiamo space
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\t \t \t"})
    void spaceTest(String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();
        final String[] EMPTY_ARRAY = new String[0];
        assertAll(
                () -> assertEquals(App.Command.SPACE, pt.getCommand()),
                () -> assertEquals(App.Command.SPACE.getNumArgs(),
                        pt.getArgs().length + pt.getNumMissingArgs()),
                () -> assertEquals(0, pt.getArgs().length),
                () -> assertArrayEquals(EMPTY_ARRAY, pt.getCloseCommandsStrings()),
                () -> assertFalse(pt.hasMissingArgs()),
                () -> assertEquals(0, pt.getNumMissingArgs())
        );
    }

    // test input invalidi
    @ParameterizedTest
    @ValueSource(strings = {"/ciao", "/", "//", "/muori", "/hhhellp"})
    void invalidTest(String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();
        final String[] EMPTY_ARRAY = new String[0];
        assertAll(
                () -> assertEquals(App.Command.INVALID, pt.getCommand()),
                () -> assertEquals(App.Command.INVALID.getNumArgs(),
                        pt.getArgs().length + pt.getNumMissingArgs()),
                () -> assertEquals(0, pt.getArgs().length),
                () -> assertArrayEquals(EMPTY_ARRAY, pt.getCloseCommandsStrings()),
                () -> assertFalse(pt.hasMissingArgs()),
                () -> assertEquals(0, pt.getNumMissingArgs())
        );
    }

    // test nuova
    @Test
    void nuovaTest() {
        parser.feed("/nuova treno");
        ParserToken pt = parser.getParserToken();
        final String[] EMPTY_ARRAY = new String[0];
        assertAll(
                () -> assertEquals(App.Command.NUOVA, pt.getCommand()),
                () -> assertEquals(App.Command.NUOVA.getNumArgs(),
                        pt.getArgs().length + pt.getNumMissingArgs()),
                () -> assertEquals(1, pt.getArgs().length),
                () -> assertEquals("treno", pt.getArgs()[0]),
                () -> assertArrayEquals(EMPTY_ARRAY, pt.getCloseCommandsStrings()),
                () -> assertFalse(pt.hasMissingArgs()),
                () -> assertEquals(0, pt.getNumMissingArgs())
        );
    }

    // test nuova con argomento mancante
    @Test
    void nuovaMissingArgTest() {
        parser.feed("/nuova ");
        ParserToken pt = parser.getParserToken();
        final String[] EMPTY_ARRAY = new String[0];
        assertAll(
                () -> assertEquals(App.Command.NUOVA, pt.getCommand()),
                () -> assertEquals(App.Command.NUOVA.getNumArgs(),
                        pt.getArgs().length + pt.getNumMissingArgs()),
                () -> assertEquals(0, pt.getArgs().length),
                () -> assertArrayEquals(EMPTY_ARRAY, pt.getCloseCommandsStrings()),
                () -> assertTrue(pt.hasMissingArgs()),
                () -> assertEquals(1, pt.getNumMissingArgs())
        );
    }


    // test altri metodi
    private static Stream<Arguments> closeCommandParameterProvider() {
        return Stream.of(
                Arguments.of(new String[] {App.Command.HELP.toString()}, "/helpa"),
                Arguments.of(new String[] {App.Command.ESCI.toString()}, "/eci"),
                Arguments.of(new String[] {App.Command.ABBANDONA.toString()}, "/abbandoma"),
                Arguments.of(new String[] {App.Command.MOSTRA.toString()}, "/mostro"),
                Arguments.of(new String[] {App.Command.GIOCA.toString()}, "/gicoa"));
    }

    @ParameterizedTest
    @MethodSource("closeCommandParameterProvider")
    void closeCommandTest(String[] expectedCloseCommands, String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();
        final String[] EMPTY_ARRAY = new String[0];

        assertAll(
                () -> assertEquals(App.Command.INVALID, pt.getCommand()),
                () -> assertArrayEquals(EMPTY_ARRAY, pt.getArgs()),
                () -> assertArrayEquals(expectedCloseCommands, pt.getCloseCommandsStrings()),
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
    void closeCommand2Test(String[] expectedCloseCommands, String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();
        final String[] EMPTY_ARRAY = new String[0];

        assertAll(
                () -> assertEquals(App.Command.INVALID, pt.getCommand()),
                () -> assertArrayEquals(EMPTY_ARRAY, pt.getArgs()),
                () -> assertArrayEquals(expectedCloseCommands, pt.getCloseCommandsStrings()),
                () -> assertFalse(pt.hasMissingArgs()),
                () -> assertEquals(0, pt.getNumMissingArgs()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/help nuova cosa  si", "/abbandona la partita   ",
            "/esci  dalla   partita", "/nuova input parola", "tento in "
            , "/elp aiuto"})
    void excessiveArgsTest(String input) {
        parser.feed(input);
        ParserToken pt = parser.getParserToken();

        assertEquals(pt.getCommand().getNumArgs(), pt.getArgs().length);
    }

}
