package it.uniba.app;

/** Questa classe effettua il parsing dell'input inserito dall'utente 
 *
 */
public class Parser
{
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
