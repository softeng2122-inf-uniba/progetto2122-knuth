package it.uniba.app;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@literal <<Boundary>>} <br>
 * Classe per il parsing dell'input dell'utente. <p></p>
 * Estrae {@link Command} e relativi argomenti a partire dalle linee digitate.
 */
public class Parser
{
    private String input;
    private String[] tokens;
    private ParserToken parserToken;

    public Parser()
    {
        input = null;
        tokens = null;
        parserToken = null;
    }

    public void feed(String inputLine)
    {
        this.input = inputLine.trim();
        tokens = tokenizeInput();
        Command command = extractCommand();
        String[] args = extractArgs(command);
        this.parserToken = new ParserToken(command, args);
    }

    private String[] tokenizeInput()
    {
        if (input.length() == 0)
            return null;

        String[] tokens;
        tokens = input.split("\\s+");
        return tokens;
    }

    private Command extractCommand()
    {
        // CASO 1: solo spazi
        if (tokens == null) {
            return Command.SPACE;
        }

        // CASO 2: tentativo
        char firstChar = tokens[0].charAt(0);
        if(firstChar != '/')
        {
            return Command.GUESS;
        } else { // CASO 3: altro comando valido
            String tokenCommand = tokens[0].substring(1);

            for(Command c: Command.values()) {
                if(tokenCommand.equalsIgnoreCase(c.toString()))
                    return c;
            }
            //salvo solo il comando, i valori di enum non contengono "/"
        }

        // CASO 4: comando invalido
        return Command.INVALID;

    }

    private String[] extractArgs(Command command)
    {

        if (tokens == null)
            return null;

        List<String> argsList = new LinkedList<>(Arrays.asList(tokens));

        if(argsList.size() == 0)
            return null;

        if(command != Command.GUESS) {
            argsList.remove(0);
        }

        int numArgsExpected = command.getNumArgs();

        if(argsList.size() > numArgsExpected) {
            argsList = argsList.subList(0, numArgsExpected);
        }

        String[] argsArray = new String[argsList.size()];
        for(int i = 0; i < argsList.size(); i++) {
            argsArray[i] = argsList.get(i);
        }

        return argsArray;
    }

    public ParserToken getParserToken() {
        return parserToken;
    }
}