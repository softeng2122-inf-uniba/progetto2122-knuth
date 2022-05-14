package it.uniba.app;

/** Questa classe effettua il parsing dell'input inserito dall'utente
 *
 */
public class Parser
{
    private String input;
    private Command command;
    private String[] args;
    private String[] tokens;

    public Parser() {
        this.input = null;
        this.command = null;
        this.args = null;
    }
    /*
    public Parser(String inputLine)
    {
        feed(inputLine);
    }
    */
    public void feed(String inputLine) {
        this.input = inputLine.trim();
        tokens = tokenizeInput();
        this.command = setCommand();
        this.args = setArgs();
    }

    /**
     * Metodo privato che viene utilizzato dai metodi della classe per tokenizzare l'input del parser
     *
     * @return
     */
    private String[] tokenizeInput() {
        if (input.length()==0)
        {
            return null;
        }
        String[] tokens;
        tokens = input.split("\\s+");
        return tokens;
    }

    //deve avvalorare l'attributo command
    private Command setCommand()
    {
        Command command = Command.INVALID;
        if (tokens == null)
        {
            command = Command.SPACE;
        }
        else
        {
            Character firstChar = tokens[0].charAt(0);
            if (firstChar.equals('/'))
            {
                //salvo solo il comando, i valori di enum non avranno il carattere /
                String tokenCommand = tokens[0].substring(1, tokens[0].length());
                if (tokenCommand.equalsIgnoreCase(Command.DUMMY.toString()))
                {
                    command = Command.DUMMY;
                }
                if (tokenCommand.equalsIgnoreCase(Command.GIOCA.toString()))
                {
                    command = Command.GIOCA;
                }
            }
            else //il primo carattere non è '/', quindi è un tentativo
            {
                command = Command.GUESS;
            }
        }
        return command;
    }

    private String[] setArgs()
    {
        if (tokens==null)
        {
            args = null;
        }
        else
        {
            //se il primo token è un comando lo salti
            if (tokens[0].charAt(0) == '/')
            {
                int numberArgsExpected;
                //Questo switch deve prendere il giusto numero di argomenti per il comando riconosciuto
                switch (command)
                {
                    case DUMMY:
                    case INVALID:
                    case GIOCA:
                        numberArgsExpected = 0;
                        break;
                    default:
                        numberArgsExpected = 0;
                }
                if (numberArgsExpected == 0)    //warning risolto con almeno un case >=1
                {
                    args = null;
                }
                else
                {
                    args = new String[numberArgsExpected];
                    int countArg = 0;

                    //Inserisce gli argomenti contenuti in tokens partendo dalla posizione 1,
                    //così da saltare il token contenente il comando
                    for (int nToken = 1; nToken <= numberArgsExpected; nToken++)
                    {
                        args[countArg] = tokens[nToken];
                        countArg++;
                    }
                }
            } //se il primo token non è un comando allora è un tentativo e considero solo il primo argomento
            else
            {
                args = new String[1];
                args[0] = tokens[0];
            }
        }
        return args;
    }

    //deve restituire l'attributo command
    public Command getCommand()
    {
        return this.command;
    }

    public String[] getArgs()
    {
        return this.args;
    }
    //parseConfirmation()
}