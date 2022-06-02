package it.uniba.app.wordle.UI.CLI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * {@literal <<Boundary>>} <br>
 * Classe per il parsing dell'input dell'utente. <p></p>
 * Estrae {@link Command} e relativi argomenti
 * a partire dalle linee digitate.
 */
public class Parser {
    private String input;
    private String[] tokens;
    private ParserToken parserToken;

    public Parser() {
        input = null;
        tokens = null;
        parserToken = null;
    }

    public void feed(final String inputLine) {

        this.input = inputLine.trim();
        tokens = tokenizeInput();
        Command command = extractCommand();
        String[] args = extractArgs(command);
        this.parserToken = new ParserToken(command, args);

        //Riconoscimento comando simile
        if (command == Command.INVALID) {
            String wrongCommandToken = tokens[0].substring(1);
            parserToken.setCloseCommands(getCloseCommands(wrongCommandToken));
        }
    }

    private String[] tokenizeInput() {
        if (input.length() == 0) {
            return null;
        }

        String[] tokens;
        tokens = input.split("\\s+");
        return tokens;
    }

    private Command extractCommand() {

        // CASO 1: solo spazi
        if (tokens == null) {
            return Command.SPACE;
        }

        // CASO 2: tentativo
        char firstChar = tokens[0].charAt(0);
        if (firstChar != '/') {
            return Command.GUESS;
        } else { // CASO 3: altro comando valido
            String tokenCommand = tokens[0].substring(1);

            for (Command c: Command.values()) {
                if (tokenCommand.equalsIgnoreCase(c.toString())) {
                    return c;
                }
            }
        }

        // CASO 4: comando invalido
        return Command.INVALID;
    }

    private String[] extractArgs(final Command command) {

        if (tokens == null) {
            return null;
        }

        List<String> argsList = new LinkedList<>(Arrays.asList(tokens));

        if (argsList.size() == 0) {
            return null;
        }

        if (command != Command.GUESS) {
            argsList.remove(0);
        }

        int numArgsExpected = command.getNumArgs();

        if (argsList.size() > numArgsExpected) {
            argsList = argsList.subList(0, numArgsExpected);
        }

        String[] argsArray = new String[argsList.size()];
        for (int i = 0; i < argsList.size(); i++) {
            argsArray[i] = argsList.get(i);
        }

        return argsArray;
    }

    private int editDistance(String s, String t) {
        int i, j;
        s = s.toUpperCase();
        t = t.toUpperCase();

        final int n = s.length(), m = t.length();
        int[][]l = new int[n + 1][m + 1];

        for (i = 0; i < (n + 1); i++) {
            for (j = 0; j < (m + 1); j++) {
                if (i == 0 || j == 0) {
                    l[i][j] = Math.max(i, j);
                } else {
                    l[i][j] = min3(
                            l[i - 1][j] + 1,
                            l[i][j - 1] + 1,
                            l[i - 1][j - 1] + (s.charAt(i - 1)
                                    != t.charAt(j - 1) ? 1 : 0));
                }
            }
        }
        return l[n][m];
    }

    private int min3(final  int i, final int j, final int k) {

        return Math.min(Math.min(i, j), k);
    }

    private List<Command> getCloseCommands(final String wrongCommandString) {

        List<Command> closeCommands = null;
        int distance = 2;
        int editDist;

        for (Command comparedCommand : Command.values()) {
            String comparedString = comparedCommand.toString();
            if (comparedString == null) {
                continue;
            }

            editDist = editDistance(comparedString, wrongCommandString);

            if (editDist <= distance) {
                if (closeCommands == null) {
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