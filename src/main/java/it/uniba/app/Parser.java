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
    public static Command parseInput(String inputLine)
    {
        Command outParser;
        if (inputLine.compareToIgnoreCase("dummy")==0)
        {
            outParser = Command.DUMMY;
        } else outParser = Command.INVALID;
        return outParser;
    }

    //parseConfirmation()

}