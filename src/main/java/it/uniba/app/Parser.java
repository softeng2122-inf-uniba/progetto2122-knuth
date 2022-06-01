package it.uniba.app;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

/**
 * {@literal <<Boundary>>} <br>
 * Classe per il parsing dell'input dell'utente. <p></p>
 * Estrae {@link Command} e relativi argomenti a partire dalle linee digitate.
 */
public class Parser
{
    private String input;
    private String[] tokens;
    private Command command;
    private String[] args;

    public Parser()
    {
        this.input = null;
        this.command = null;
        this.args = null;
    }

    public void feed(String inputLine)
    {
        this.input = inputLine.trim();
        tokens = tokenizeInput();
        this.command = extractCommand();

        try {
            this.args = extractArgs();
        } catch (InputMismatchException e) {

        }

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

    private String[] extractArgs()
    {
        if (tokens == null)
            return null;

        List<String> tokensList = new LinkedList<>(Arrays.asList(tokens));

        if(tokensList.size() == 0)
            return null;

        if(command != Command.GUESS) {
            tokensList.remove(0);
        }

        int numArgsExpected = command.getNumArgs();
        tokensList = tokensList.subList(0, numArgsExpected);

        String[] argsArray = new String[numArgsExpected];
        tokensList.toArray(argsArray);

        return argsArray;
    }

    public Command getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

}