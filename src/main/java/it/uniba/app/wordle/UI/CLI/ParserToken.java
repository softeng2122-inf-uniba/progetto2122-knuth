package it.uniba.app.wordle.UI.CLI;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * {@literal <<Boundary>>} <br>
 * Classe che rappresenta l'output di un Parser e incapsula il comando,
 * gli argomenti e l'eventuale insieme di comandi simili.
 */
public final class ParserToken {

    /** Comando contenuto. */
    private final App.Command command;
    /** Argomenti inseriti. */
    private final String[] args;
    /** Numero di argomenti mancanti rispetto a quelli previsti dal comando. */
    private int numMissingArgs;
    /** Insieme di comandi simili a quello digitato (se non è valido). */
    private Set<App.Command> closeCommands;

    /** Insieme vuoto di comandi, restituito all'occorrenza (immutabile). */
    private static final Set<App.Command> EMPTY_COMMAND_SET
                                          = Collections.emptySet();
    /** Array vuoto di stringhe, restituito all'occorrenza (immutabile). */
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Restituisce un array contenente le stringhe relative ai comandi
     * simili al comando invalido inserito.
     *
     * <p> Se il risultato di {@link ParserToken#getCommand()} è diverso da
     * {@link App.Command#INVALID} allora l'array sarà vuoto.</p>
     *
     * <p>Se l'array non è vuoto allora contiene le stringhe relative ai
     * comandi la cui edit distance (ossia il numero di caratteri da
     * modificare dalla stringa inserita per arrivare a quella prevista)
     * è minore o uguale a 2.</p>
     *
     * @return un array contenente le rappresentazioni sotto forma
     * di stringa dei comandi simili a quello inserito, eventualmente vuoto
     */
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

    /**
     * Crea un ParserToken che incapsula un comando, gli argomenti
     * e l'insieme dei comandi simili.
     *
     * @param extractedCommand comando
     * @param extractedArgs argomenti ricevuti
     * @param closeCommandsSet insieme di comandi simili (eventualmente vuoto)
     */
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

    /**
     * Restituisce il comando contenuto nel token.
     *
     * @return comando contenuto nel token
     */
    public App.Command getCommand() {
        return command;
    }

    /**
     * Restituisce gli argomenti inseriti.
     *
     * @return argomenti inseriti
     */
    public String[] getArgs() {
        if (args.length == 0) {
            //immutabile, non è un problema restituirlo
            return EMPTY_STRING_ARRAY;
        } else {
            return Arrays.copyOf(args, args.length);
        }
    }

    /**
     * Calcola il numero di argomenti mancanti rispetto
     * a quelli richiesti dal comando.
     */
    private void setNumMissingArgs() {

        int numArgsExpected = command.getNumArgs();

        if (numArgsExpected == 0) {
            numMissingArgs = 0;
        } else {
            int actualNum = args.length;
            numMissingArgs = numArgsExpected - actualNum;
        }
    }

    /**
     * Indica se ci sono argomenti mancanti rispetto a quelli
     * richiesti dal comando.
     *
     * @return true se ci sono argomenti mancanti, false altrimenti
     */
    public boolean hasMissingArgs() {
        return numMissingArgs > 0;
    }

    /**
     * Restituisce il numero di argomenti mancanti rispetto a quelli
     * richiesti dal comando.
     *
     * @return il numero di argomenti mancanti
     */
    public int getNumMissingArgs() {
        return numMissingArgs;
    }
}
