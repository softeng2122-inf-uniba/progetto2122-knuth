package it.uniba.app;

/**
 * {@literal <<Boundary>>} <br>
 * Classe per il parsing dell'input dell'utente. <p></p>
 * Estrae {@link Command} e relativi argomenti a partire dalle linee digitate.
 */
public class Parser {
    private String input;
    private Command command;
    private String[] args;
    private String[] tokens;

    public Parser() {
        this.input = null;
        this.command = null;
        this.args = null;
    }

    public void feed(String inputLine) {
        this.input = inputLine.trim();
        tokens = tokenizeInput();
        this.command = extractCommand();
        this.args = extractArgs();
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
        Command command = Command.INVALID;

        // CASO 1: solo spazi
        if (tokens == null) {
            command = Command.SPACE;
            return command;
        }

        // CASO 2: comando effettivo
        char firstChar = tokens[0].charAt(0);
        if (firstChar == '/') {
            //salvo solo il comando, i valori di enum non contengono "/"
            String tokenCommand = tokens[0].substring(1);

            if (tokenCommand.equalsIgnoreCase(Command.GIOCA.toString())) {
                command = Command.GIOCA;
            }

            if (tokenCommand.equalsIgnoreCase(Command.NUOVA.toString())) {
                command = Command.NUOVA;
            }

            if (tokenCommand.equalsIgnoreCase(Command.ABBANDONA.toString())) {
                command = Command.ABBANDONA;
            }

            if (tokenCommand.equalsIgnoreCase(Command.ESCI.toString())) {
                command = Command.ESCI;
            }

            if (tokenCommand.equalsIgnoreCase(Command.MOSTRA.toString())) {
                command = Command.MOSTRA;
            }

            if (tokenCommand.equalsIgnoreCase(Command.HELP.toString())) {
                command = Command.HELP;
            }
        } else {//CASO 3: tentativo
            command = Command.GUESS;
        }
        return command;
    }

    private String[] extractArgs() {
        if (tokens == null) {
            return null;
        }

        //se il primo token è un comando lo salti
        if (tokens[0].charAt(0) == '/') {
            int numberArgsExpected;

            // imposta il numero di argomenti atteso
            switch (command) {
                case INVALID:
                case GIOCA:
                case ABBANDONA:
                case ESCI:
                case MOSTRA:
                case HELP:
                    numberArgsExpected = 0;
                    break;
                case NUOVA:
                    numberArgsExpected = 1;
                    break;
                default:
                    numberArgsExpected = 0;
            }

            if (numberArgsExpected == 0) {
                return null;
            } else {
                // array di grandezza pari al numero massimo di argomenti
                // del comando
                String[] tempArgs = new String[numberArgsExpected];
                int countArg = 0;

                try {
                    //partendo dal token 1 (cioè escludendo quello contenente
                    // il comando) inserisci in tempArgs
                    for (int nToken = 1; nToken <= numberArgsExpected; nToken++) {
                        tempArgs[countArg] = tokens[nToken];
                        countArg++;
                    }
                    return tempArgs;
                    // nel caso in cui leggi un numero minore di argomenti
                } catch (ArrayIndexOutOfBoundsException e) {
                    return tempArgs;
                }
            }
        } else { //se è un tentativo considero solo il primo argomento
            String[] tempArgs = new String[1];
            tempArgs[0] = tokens[0];
            return tempArgs;
        }
    }

    public Command getCommand() {
        return this.command;
    }

    public String[] getArgs() {
        return this.args;
    }
}