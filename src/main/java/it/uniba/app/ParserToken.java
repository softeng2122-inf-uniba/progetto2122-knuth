package it.uniba.app;

import java.util.List;

public class ParserToken
{
    private final Command command;
    private final String[] args;
    private int numMissingArgs;
    private List<Command> closeCommands = null;


    public String[] getCloseCommandsStrings(){
        if (closeCommands == null){
            return null;
        }
        String[] closeCommandsStrings = new String[closeCommands.size()];

        for (int i=0; i<closeCommands.size(); i++){
            closeCommandsStrings[i] = closeCommands.get(i).toString();
        }
        return closeCommandsStrings;
    }

    public void setCloseCommands(List<Command> closeCommands) {
        this.closeCommands = closeCommands;
    }

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
            int actualNum;
            if(args == null) {
                actualNum = 0;
            }
            else {
                actualNum = args.length;
            }
            numMissingArgs = numArgsExpected - actualNum;
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
