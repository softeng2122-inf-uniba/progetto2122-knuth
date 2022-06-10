package it.uniba.app.wordle.UI.CLI;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import java.util.Set;

/**
 * {@literal <<Boundary>>} <br>
 * Classe per il parsing dell'input dell'utente.
 *
 * <p>Estrae {@link App.Command} e relativi argomenti
 * a partire dalle linee digitate.</p>
 */
public final class Parser {

    /** Input inserito dall'utente. */
    private String input;
    /** Array contenente i token in cui viene diviso l'input
     * in base agli spazi inseriti. */
    private String[] tokens;
    /** Comando estratto dall'input. */
    private App.Command command;
    /** Argomenti estratti dall'input. */
    private String[] args;
    /** Insieme di comandi simili a quello inserito (eventualmente vuoto). */
    private Set<App.Command> closeCommands;

    /** Insieme vuoto di comandi (immutabile). */
    private static final Set<App.Command> EMPTY_COMMAND_SET
            = Collections.emptySet();
    /** Array vuoto di stringhe, restituito all'occorrenza (immutabile). */
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Crea Parser inizialmente vuoto.
     */
    public Parser() {
        input = null;
        tokens = EMPTY_STRING_ARRAY;
        command = null;
        args = EMPTY_STRING_ARRAY;
        closeCommands = EMPTY_COMMAND_SET;
    }

    /**
     * Elabora l'input fornito per riconoscere il comando inserito e
     * suddividere eventuali argomenti.
     *
     * @param inputLine linea di testo inserita da elaborare
     */
    public void feed(final String inputLine) {
        Objects.requireNonNull(inputLine);

        this.input = inputLine.trim();
        tokens = tokenizeInput();
        command = extractCommand();
        args = extractArgs(command);

        //Riconoscimento comando simile
        if (command == App.Command.INVALID) {
            String wrongCommandToken = tokens[0].substring(1);
            closeCommands = getCloseCommands(wrongCommandToken);
        }
    }

    /**
     * Restituisce un array di token (eventualmente vuoto),
     * ossia le sottostringhe di {@link Parser#input} separate da spazi.
     *
     * @return array di token
     */
    private String[] tokenizeInput() {
        if (input.length() == 0) {
            return EMPTY_STRING_ARRAY;
        }

        String[] tempTokens;
        tempTokens = input.split("\\s+");
        return tempTokens;
    }

    /**
     * Estrae il comando contenuto in {@link Parser#tokens}.
     *
     * <p>Se {@link Parser#tokens} è vuoto,
     * il comando estratto è {@link App.Command#SPACE}</p>
     *
     * <p>Se {@link Parser#tokens} contiene un comando non riconosciuto,
     * il comando estratto è {@link App.Command#INVALID}</p>
     *
     * <p>Se il primo token non inizia con '/',
     * il comando estratto è {@link App.Command#GUESS}</p>
     *
     * @return il comando estratto
     */
    private App.Command extractCommand() {

        // CASO 1: solo spazi
        if (tokens.length == 0) {
            return App.Command.SPACE;
        }

        // CASO 2: tentativo
        char firstChar = tokens[0].charAt(0);
        if (firstChar != '/') {
            return App.Command.GUESS;
        } else { // CASO 3: altro comando
            String tokenCommand = tokens[0].substring(1);

            for (App.Command c: App.Command.values()) {
                if (tokenCommand.equalsIgnoreCase(c.toString())) {
                    return c;
                }
            }
            // CASO 4: comando invalido
            return App.Command.INVALID;
        }
    }

    /**
     * Estrae gli argomenti inseriti dall'utente presenti
     * in {@link Parser#tokens}.
     *
     * <p>Se il numero di argomenti inseriti è maggiore rispetto al
     * numero di argomenti attesi, quelli in eccesso vengono ignorati.</p>
     *
     * @param extractedCommand comando estratto da {@link Parser#extractCommand()}
     * @return un array di argomenti (eventualmente vuoto)
     */
    private String[] extractArgs(final App.Command extractedCommand) {
        Objects.requireNonNull(extractedCommand);

        if (tokens.length == 0) {
            return EMPTY_STRING_ARRAY;
        }

        List<String> argsList = new LinkedList<>(Arrays.asList(tokens));

        if (extractedCommand != App.Command.GUESS) {
            argsList.remove(0);
        }

        int numArgsExpected = extractedCommand.getNumArgs();

        if (argsList.size() > numArgsExpected) {
            argsList = argsList.subList(0, numArgsExpected);
        }
        //passo EMPTY_STRING_ARRAY per comunicarne il tipo
        return argsList.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Calcola la distanza di edit tra le stringhe passate in argomento.
     *
     * <p> La distanza di edit è uguale al minimo numero di caratteri che
     * devono essere cancellati, inseriti o sostituiti per passare da una
     * stringa ad un'altra.</p>
     *
     * <p>Questa operazione è commutativa</p>
     *
     * <p>Per approfondire il funzionamento dell'algoritmo
     * consultare <a href="https://rb.gy/hncbga">questa pagina</a>
     *
     * @param first prima stringa da confrontare
     * @param second seconda stringa da confrontare
     * @return la distanza di edit tra {@code first} e {@code second}
     */
    private int editDistance(final String first,
                             final String second) {

        String upperFirst = first.toUpperCase();
        String upperSecond = second.toUpperCase();

        int[][]l = new int[upperFirst.length() + 1][upperSecond.length() + 1];

        for (int i = 0; i < (upperFirst.length() + 1); i++) {
            for (int j = 0; j < (upperSecond.length() + 1); j++) {

                if (i == 0 || j == 0) {
                    l[i][j] = Math.max(i, j);
                } else {
                    l[i][j] = min3(
                            l[i - 1][j] + 1,
                            l[i][j - 1] + 1,
                            l[i - 1][j - 1] + (upperFirst.charAt(i - 1)
                                    != upperSecond.charAt(j - 1) ? 1 : 0));
                }
            }
        }
        return l[upperFirst.length()][upperSecond.length()];
    }

    private int min3(final  int i, final int j, final int k) {

        return Math.min(Math.min(i, j), k);
    }

    /**
     * Crea l'insieme dei comandi simili alla stringa inserita come parametro
     *
     * <p>Per comando simile si intende un comando la cui rappresentazione
     * sotto forma di stringa abbia una edit distance minore o uguale a 2
     * se confrontata al {@code wrongCommandString}.</p>
     * @param wrongCommandString stringa da confrontare
     * @return un insieme di comandi simili a {@code wrongCommandString}
     */
    private Set<App.Command> getCloseCommands(
            final String wrongCommandString) {
        Objects.requireNonNull(wrongCommandString);

        Set<App.Command> tempCloseCommands = EMPTY_COMMAND_SET;
        int distance = 2;
        int editDist;

        for (App.Command comparedCommand : App.Command.values()) {
            if (comparedCommand == App.Command.INVALID
                    || comparedCommand == App.Command.SPACE) {
                continue;
            }
            String comparedString = comparedCommand.toString();
            editDist = editDistance(comparedString, wrongCommandString);

            if (editDist <= distance) {
                if (tempCloseCommands.isEmpty()) {
                    tempCloseCommands = new HashSet<>();
                }
                tempCloseCommands.add(comparedCommand);
            }
        }
        return tempCloseCommands;
    }

    /**
     * Restituisce il risultato dell'elaborazione dell'input
     * sotto forma di {@link ParserToken}.
     *
     * @return ParserToken risultato dell'elaborazione
     */
    public ParserToken getParserToken() {

        return new ParserToken(command, args, closeCommands);
    }
}
