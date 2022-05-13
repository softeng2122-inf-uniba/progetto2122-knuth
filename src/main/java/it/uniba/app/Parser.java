package it.uniba.app;

/** Questa classe effettua il parsing dell'input inserito dall'utente 
 *
 */
public class Parser
{
    private String input;
    private Command command;
    private String[] args;
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
        //this.input = inputLine.trim();
        //this.command = setCommand();
        //this.args = setArgs();
    }

    /**
     * Metodo privato che viene utilizzato dai metodi della classe per tokenizzare l'input del parser
     *
     * @return
     */
    private String[] tokenize() {
        if (input.length()==0)
        {
            return null;
        }
        String[] tokens;
        tokens = input.split("\\s+");
        return tokens;
    }

    //deve avvalorare l'attributo command
    private Command setCommand() {
        Command command = null;
        String[] tokens = tokenize();
        //Pattern p = Pattern.compile("\\s+");
        //Matcher m = p.matcher(input);
        if (tokens == null)
        {
            command = Command.SPACE;
        }
        else
        {
            Character firstChar = tokens[0].charAt(0);
            if (firstChar.equals('/')) {
                //salvo solo il comando, i valori di enum non avranno il carattere /
                String tokenCommand = tokens[0].substring(1, tokens[0].length());
                if (tokenCommand.equalsIgnoreCase(Command.DUMMY.toString())) {
                    command = Command.DUMMY;
                } else {
                    command = Command.INVALID;
                }
            }
        }
        return command;
    }


    //parseConfirmation()

}