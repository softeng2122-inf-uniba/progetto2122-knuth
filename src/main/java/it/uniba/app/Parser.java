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
        Command command = Command.INVALID;

        // CASO 1: solo spazi
        if (tokens == null)
        {
            command = Command.SPACE;
            return command;
        }

        // CASO 2: comando effettivo
        char firstChar = tokens[0].charAt(0);
        if (firstChar == '/')
        {
            //salvo solo il comando, i valori di enum non contengono "/"
            String tokenCommand = tokens[0].substring(1);

            if (tokenCommand.equalsIgnoreCase(Command.GIOCA.toString()))
                command = Command.GIOCA;

            if (tokenCommand.equalsIgnoreCase(Command.NUOVA.toString()))
                command = Command.NUOVA;

            if (tokenCommand.equalsIgnoreCase(Command.ABBANDONA.toString()))
                command = Command.ABBANDONA;

            if (tokenCommand.equalsIgnoreCase(Command.ESCI.toString()))
                command = Command.ESCI;

            if (tokenCommand.equalsIgnoreCase(Command.MOSTRA.toString()))
                command = Command.MOSTRA;

            if (tokenCommand.equalsIgnoreCase(Command.HELP.toString()))
                command = Command.HELP;
        }
        else //CASO 3: tentativo
            command = Command.GUESS;

        return command;
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

        tokensList = tokensList.subList(0, command.getNumArgs());

        String[] argsArray = new String[command.getNumArgs()];
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