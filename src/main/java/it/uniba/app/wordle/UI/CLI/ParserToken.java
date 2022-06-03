package it.uniba.app.wordle.UI.CLI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ParserToken {

    private final App.Command command;
    private final String[] args;
    private int numMissingArgs;
    private List<App.Command> closeCommands = null;


    public String[] getCloseCommandsStrings() {
        if (closeCommands == null) {
            return null;
        }

        String[] closeCommandsStrings = new String[closeCommands.size()];

        for (int i = 0; i < closeCommands.size(); i++) {
            closeCommandsStrings[i] = closeCommands.get(i).toString();
        }

        return closeCommandsStrings;
    }

    public void setCloseCommands(final List<App.Command> closeCommands) {
        if(closeCommands == null) {
            this.closeCommands = null;
        } else {
            this.closeCommands = Collections.unmodifiableList(closeCommands);
        }

    }

    public ParserToken(final App.Command command, final String[] args) {
        this.command = command;
        if(args == null || args.length == 0) {
            this.args = null;
        } else {
            this.args = Arrays.copyOf(args, args.length);
        }

        setNumMissingArgs();
    }

    public App.Command getCommand() {
        return command;
    }

    public String[] getArgs() {
        if(args == null) {
            return null;
        } else {
            return Arrays.copyOf(args, args.length);
        }
    }

    private void setNumMissingArgs() {

        int numArgsExpected = command.getNumArgs();

        if (numArgsExpected == 0) {
            numMissingArgs = 0;
        } else {
            int actualNum;
            if (args == null) {
                actualNum = 0;
            } else {
                actualNum = args.length;
            }
            numMissingArgs = numArgsExpected - actualNum;
        }
    }

    public boolean areMissingArgs() {
        return numMissingArgs > 0;
    }

    public int getNumMissingArgs() {
        return numMissingArgs;
    }
}
