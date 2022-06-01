package it.uniba.app;

public class ParserToken
{
    private Command command;
    private String[] args;
    private int numMissingArgs;

    public ParserToken(Command command, String[] args) {
        this.command = command;
        this.args = args;

        setNumMissingArgs();
    }
    public Command getCommand()
    {
        return command;
    }

    public String[] getArgs()
    {
        return args;
    }

    private void setNumMissingArgs() {
        int numArgsExpected = command.getNumArgs();
        if(numArgsExpected == 0) {
            numMissingArgs = 0;
        }
        else {
            numMissingArgs = numArgsExpected - args.length;
        }
    }

    public boolean areMissingArgs() {
        return numMissingArgs > 0;
    }
    public int getNumMissingArgs()
    {
        return numMissingArgs;
    }
}
