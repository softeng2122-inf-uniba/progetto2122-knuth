package it.uniba.app.wordle.UI.CLI;

import java.util.*;

/**
 * {@literal <<Boundary>>} <br>
 * Classe per il parsing dell'input dell'utente. <p></p>
 * Estrae {@link App.Command} e relativi argomenti
 * a partire dalle linee digitate.
 */
public final class Parser {
    private String input;
    private String[] tokens;
    private ParserToken parserToken;

    private static final List<App.Command> EMPTY_COMMAND_LIST = Collections.emptyList();
    private static final String[] EMPTY_STRING_ARRAY = new String[0];


    public Parser() {
        input = null;
        tokens = null;
        parserToken = null;
    }

    public void feed(final String inputLine) {
        Objects.requireNonNull(inputLine);

        this.input = inputLine.trim();
        tokens = tokenizeInput();
        App.Command command = extractCommand();
        String[] args = extractArgs(command);
        this.parserToken = new ParserToken(command, args);

        //Riconoscimento comando simile
        if (command == App.Command.INVALID) {
            String wrongCommandToken = tokens[0].substring(1);
            parserToken.setCloseCommands(getCloseCommands(wrongCommandToken));
        }
    }

    //un input "" ritorna un array con 0 elementi
    private String[] tokenizeInput() {
        if (input.length() == 0) {
            return EMPTY_STRING_ARRAY;
        }

        String[] tokens;
        tokens = input.split("\\s+");
        return tokens;
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

    private String[] extractArgs(final App.Command command) {
        Objects.requireNonNull(command);

        if (tokens.length == 0) {
            return EMPTY_STRING_ARRAY;
        }

        List<String> argsList = new LinkedList<>(Arrays.asList(tokens));

        if (command != App.Command.GUESS) {
            argsList.remove(0);
        }

        int numArgsExpected = command.getNumArgs();

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

    private List<App.Command> getCloseCommands(final String wrongCommandString) {
        Objects.requireNonNull(wrongCommandString);

        List<App.Command> closeCommands = EMPTY_COMMAND_LIST;
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
                if (closeCommands.isEmpty()) {
                    closeCommands = new ArrayList<>();
                }
                closeCommands.add(comparedCommand);
            }
        }
        return closeCommands;
    }

    public ParserToken getParserToken() {
        return parserToken;
    }
}
