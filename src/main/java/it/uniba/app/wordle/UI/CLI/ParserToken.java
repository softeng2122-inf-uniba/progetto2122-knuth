package it.uniba.app.wordle.UI.CLI;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class ParserToken {

    private final App.Command command;
    private final String[] args;
    private int numMissingArgs;
    private Set<App.Command> closeCommands;

    private static final Set<App.Command> EMPTY_COMMAND_SET
                                          = Collections.emptySet();
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    public String[] getCloseCommandsStrings() {
        if (closeCommands.isEmpty()) {
            return EMPTY_STRING_ARRAY;
        } else {
            return closeCommands.stream()
                    .map(App.Command::toString)
                    .toArray(String[]::new);
        }
    }

    private void setCloseCommands(final Set<App.Command> closeCommandsSet) {
        Objects.requireNonNull(closeCommandsSet);

        if (closeCommandsSet.isEmpty()) {
            this.closeCommands = EMPTY_COMMAND_SET;
        } else {
            this.closeCommands
                    = Collections.unmodifiableSet(closeCommandsSet);
        }
    }

    public ParserToken(final App.Command extractedCommand,
                       final String[] extractedArgs,
                       final Set<App.Command> closeCommandsSet) {
        Objects.requireNonNull(extractedCommand);
        Objects.requireNonNull(extractedArgs);

        this.command = extractedCommand;
        if (extractedArgs.length == 0) {
            this.args = EMPTY_STRING_ARRAY;
        } else {
            this.args = Arrays.copyOf(extractedArgs, extractedArgs.length);
        }

        setCloseCommands(closeCommandsSet);
        setNumMissingArgs();
    }

    public App.Command getCommand() {
        return command;
    }

    public String[] getArgs() {
        if (args.length == 0) {
            //immutabile, non è un problema restituirlo
            return EMPTY_STRING_ARRAY;
        } else {
            return Arrays.copyOf(args, args.length);
        }
    }

    private void setNumMissingArgs() {

        int numArgsExpected = command.getNumArgs();

        if (numArgsExpected == 0) {
            numMissingArgs = 0;
        } else {
            int actualNum = args.length;
            numMissingArgs = numArgsExpected - actualNum;
        }
    }

    public boolean hasMissingArgs() {
        return numMissingArgs > 0;
    }

    public int getNumMissingArgs() {
        return numMissingArgs;
    }
}
