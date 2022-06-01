package it.uniba.app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParserToken
{
    private final Command command;
    private final String[] args;
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
