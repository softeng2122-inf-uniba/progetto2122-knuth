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
    private String[] tokenizer() {
        if (input.length()==0)
        {
            return null;
        }
        String[] tokens;
        tokens = input.split("\\s+");
        return tokens;
    }


    //parseConfirmation()

}