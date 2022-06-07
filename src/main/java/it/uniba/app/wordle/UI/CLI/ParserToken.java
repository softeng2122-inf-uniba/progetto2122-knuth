package it.uniba.app.wordle.UI.CLI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ParserToken {

    private final App.Command command;
    private final String[] args;
    private int numMissingArgs;
    private List<App.Command> closeCommands;

    private static final List<App.Command> EMPTY_COMMAND_LIST
                                          = Collections.emptyList();
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

    private void setCloseCommands(final List<App.Command> closeCommandsList) {
        Objects.requireNonNull(closeCommandsList);

        if (closeCommandsList.isEmpty()) {
            this.closeCommands = EMPTY_COMMAND_LIST;
        } else {
            this.closeCommands
                    = Collections.unmodifiableList(closeCommandsList);
        }
    }

    public ParserToken(final App.Command extractedCommand,
                       final String[] extractedArgs,
                       final List<App.Command> closeCommandsList) {
        Objects.requireNonNull(extractedCommand);
        Objects.requireNonNull(extractedArgs);

        this.command = extractedCommand;
        if (extractedArgs.length == 0) {
            this.args = EMPTY_STRING_ARRAY;
        } else {
            this.args = Arrays.copyOf(extractedArgs, extractedArgs.length);
        }

        setCloseCommands(closeCommandsList);
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
