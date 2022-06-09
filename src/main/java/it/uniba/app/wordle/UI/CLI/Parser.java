package it.uniba.app.wordle.UI.CLI;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import java.util.Set;

/**
 * {@literal <<Boundary>>} <br>
 * Classe per il parsing dell'input dell'utente. <p></p>
 * Estrae {@link App.Command} e relativi argomenti
 * a partire dalle linee digitate.
 */
public final class Parser {

    /** Input inserito dall'utente. */
    private String input;
    /** Array contenente i token in cui viene diviso l'input
     * in base agli spazi inseriti. */
    private String[] tokens;
    /** Comando estratto dall'input. */
    private App.Command command;
    /** Argomenti estratti dall'input. */
    private String[] args;
    /** Insieme di comandi simili a quello inserito (eventualmente vuoto). */
    private Set<App.Command> closeCommands;

    /** Insieme vuoto di comandi (immutabile). */
    private static final Set<App.Command> EMPTY_COMMAND_SET
                                    = Collections.emptySet();
    /** Array vuoto di stringhe, restituito all'occorrenza (immutabile). */
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Crea Parser inizialmente vuoto.
     */
    public Parser() {
        input = null;
        tokens = EMPTY_STRING_ARRAY;
        command = null;
        args = EMPTY_STRING_ARRAY;
        closeCommands = EMPTY_COMMAND_SET;
    }

    /**
     * Elabora l'input fornito per riconoscere il comando inserito e
     * suddividere eventuali argomenti.
     *
     * @param inputLine linea di testo inserito da elaborare
     */
    public void feed(final String inputLine) {
        Objects.requireNonNull(inputLine);

        this.input = inputLine.trim();
        tokens = tokenizeInput();
        command = extractCommand();
        args = extractArgs(command);

        //Riconoscimento comando simile
        if (command == App.Command.INVALID) {
            String wrongCommandToken = tokens[0].substring(1);
            closeCommands = getCloseCommands(wrongCommandToken);
        }
    }

    //un input "" ritorna un array con zero elementi
    private String[] tokenizeInput() {
        if (input.length() == 0) {
            return EMPTY_STRING_ARRAY;
        }

        String[] tempTokens;
        tempTokens = input.split("\\s+");
        return tempTokens;
    }

    private App.Command extractCommand() {

        // CASO 1: solo spazi
        if (tokens.length == 0) {
            return App.Command.SPACE;
        }

        // CASO 2: tentativo
        char firstChar = tokens[0].charAt(0);
        if (firstChar != '/') {
            return App.Command.GUESS;
        } else { // CASO 3: altro comando
            String tokenCommand = tokens[0].substring(1);

            for (App.Command c: App.Command.values()) {
                if (tokenCommand.equalsIgnoreCase(c.toString())) {
                    return c;
                }
            }
            // CASO 4: comando invalido
            return App.Command.INVALID;
        }
    }

    private String[] extractArgs(final App.Command extractedCommand) {
        Objects.requireNonNull(extractedCommand);

        if (tokens.length == 0) {
            return EMPTY_STRING_ARRAY;
        }

        List<String> argsList = new LinkedList<>(Arrays.asList(tokens));

        if (extractedCommand != App.Command.GUESS) {
            argsList.remove(0);
        }

        int numArgsExpected = extractedCommand.getNumArgs();

        if (argsList.size() > numArgsExpected) {
            argsList = argsList.subList(0, numArgsExpected);
        }
        //passo EMPTY_STRING_ARRAY per comunicarne il tipo
        return argsList.toArray(EMPTY_STRING_ARRAY);
    }

    private int editDistance(final String wrightString,
                             final String stringToEvaluate) {

        String wright = wrightString.toUpperCase();
        String stringToCheck = stringToEvaluate.toUpperCase();

        int[][]l = new int[wright.length() + 1][stringToCheck.length() + 1];

        for (int i = 0; i < (wright.length() + 1); i++) {
            for (int j = 0; j < (stringToCheck.length() + 1); j++) {

                if (i == 0 || j == 0) {
                    l[i][j] = Math.max(i, j);
                } else {
                    l[i][j] = min3(
                            l[i - 1][j] + 1,
                            l[i][j - 1] + 1,
                            l[i - 1][j - 1] + (wright.charAt(i - 1)
                                    != stringToCheck.charAt(j - 1) ? 1 : 0));
                }
            }
        }
        return l[wright.length()][stringToCheck.length()];
    }

    private int min3(final  int i, final int j, final int k) {

        return Math.min(Math.min(i, j), k);
    }

    private Set<App.Command> getCloseCommands(
                    final String wrongCommandString) {
        Objects.requireNonNull(wrongCommandString);

        Set<App.Command> tempCloseCommands = EMPTY_COMMAND_SET;
        int distance = 2;
        int editDist;

        for (App.Command comparedCommand : App.Command.values()) {
            if (comparedCommand == App.Command.INVALID
                    || comparedCommand == App.Command.SPACE) {
                continue;
            }
            String comparedString = comparedCommand.toString();
            editDist = editDistance(comparedString, wrongCommandString);

            if (editDist <= distance) {
                if (tempCloseCommands.isEmpty()) {
                    tempCloseCommands = new HashSet<>();
                }
                tempCloseCommands.add(comparedCommand);
            }
        }
        return tempCloseCommands;
    }

    /**
     * Restituisce il risultato dell'elaborazione sottoforma di
     * {@link ParserToken}.
     *
     * @return token risultato dell'elaborazione
     */
    public ParserToken getParserToken() {

        return new ParserToken(command, args, closeCommands);
    }
}
