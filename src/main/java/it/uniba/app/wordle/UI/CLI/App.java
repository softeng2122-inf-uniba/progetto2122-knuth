package it.uniba.app.wordle.UI.CLI;

import it.uniba.app.wordle.domain.WordlePlayerController;
import it.uniba.app.wordle.domain.PlayerController;
import it.uniba.app.wordle.domain.WordleWordsmithController;
import it.uniba.app.wordle.domain.WordsmithController;
import it.uniba.app.wordle.domain.WordleGameException;
import it.uniba.app.wordle.domain.WordleSettingException;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * {@literal <<Boundary>>} <br>
 * Classe principale dell'applicazione.
 *
 * <p>Contiene il main e i comandi che realizzano le funzionalita' descritte
 * nelle user story.</p>
 *
 * <p>Utilizza un oggetto {@link Parser} per processare l'input e
 * un oggetto {@link WordlePrinter} per visualizzare l'output.</p>
 */
public final class App {

    /** Controller per il giocatore. */
    private static final PlayerController PLAYER_CONTROLLER =
                         new WordlePlayerController();
    /** Controller per il paroliere. */
    private static final WordsmithController WORDSMITH_CONTROLLER =
                         new WordleWordsmithController(
                         (WordlePlayerController) PLAYER_CONTROLLER);
    /** Scanner per ricevere l'input dell'utente da tastiera. */
    private static Scanner keyboard;
    /** WordlePrinter stampare l'output dell'applicazione sulla console. */
    private static WordlePrinter console;
    /** Parser per elaborare i comandi inseriti dall'utente. */
    private static final Parser PARSER = new Parser();
    /** Flag che indica se l'applicazione deve continuare. */
    private static boolean running = true;

    /**
     * {@literal <<Boundary>>} <br>
     * L'enumerazione Command contiene le costanti che rappresentano
     * i comandi riconosciuti dal {@link Parser}.
     *
     * <p>Se vengono forniti più argomenti rispetto a quelli che il
     * comando si aspetta allora vengono semplicemente ignorati.
     * La gestione sugli argomenti mancanti viene invece effettuata
     * all'interno del main, per non avverra' alcuna chiamata a
     * {@link Command#execute(String[])} in questo caso.</p>
     *
     * <p>Le costanti {@link Command#INVALID} e {@link Command#SPACE}
     * sono comandi fittizi per gestire eventuali errori di input
     * e caratteri di spaziatura.</p>
     */
    public enum Command {
        /**
         * Comando invalido, ovvero un qualsiasi comando inesistente
         * che l'utente ha provato a inserire.
         *
         * <p>Utilizza zero argomenti</p>
         */
        INVALID(0) {
            /**
             * Effettua una stampa per notificare l'invalidita' del comando,
             * segnalando eventualmente l'esistenza di comandi simili.
             *
             * @param args array contenente le rappresentazioni sotto forma
             *             di stringa di eventuali comandi simili a quello
             *             provato a inserire
             */
            public void execute(final String[] args) {
                console.println("Comando invalido");
                if (args.length != 0) {
                    if (args.length == 1) {
                        console.println("Il comando più simile è: ");
                    } else {
                        console.println("I comandi più simili sono: ");
                    }
                    for (String closeCommandString : args) {
                        console.println("\t\t" + closeCommandString);
                    }
                }
            }
        },

        /**
         * Comando associato all'inserimento di soli spazi da parte
         * dell'utente.
         *
         * <p>Utilizza zero argomenti</p>
         */
        SPACE(0) {
            /**
             * Non effettua alcun'azione.
             *
             * @param args array di argomenti forniti al comando
             */
            public void execute(final String[] args) {
            }
        },

        /**
         * Comando {@literal /gioca}.
         *
         * <p>Non si aspetta nessun argomento</p>
         */
        GIOCA(0) {
            /**
             * Prova a iniziare una nuova partita e stampa
             * l'esito dell'esecuzione.
             *
             * @param args array di argomenti forniti al comando
             */
            public void execute(final String[] args) {
                try {
                    PLAYER_CONTROLLER.startGame();
                    console.println("Hai iniziato la partita");
                    console.printBoard();
                } catch (WordleGameException | WordleSettingException e) {
                    console.println(e.getMessage());
                }
            }
        },

        /**
         * Comando {@literal /nuova <parola>}.
         *
         * <p>Si aspetta un solo argomento</p>
         */
        NUOVA(1) {
            /**
             * Prova a impostare la parola segreta della sessione di gioco
             * e stampa l'esito dell'esecuzione.
             *
             * @param args array di argomenti forniti al comando
             */
            public void execute(final String[] args) {

                String secretWord = args[0];

                try {
                    WORDSMITH_CONTROLLER.setSecretWord(secretWord);
                    console.println("OK");
                } catch (IllegalArgumentException | WordleGameException e) {
                    console.println(e.getMessage());
                }
            }
        },

        /**
         * Comando {@literal /abbandona}.
         *
         * <p>Non si aspetta nessun argomento</p>
         */
        ABBANDONA(0) {
            /**
             * Prova ad abbandonare la partita in corso
             * e stampa l'esito dell'esecuzione.
             *
             * <p>Se una partita e' effettivamente in corso chiede
             * conferma dell'azione.</p>
             *
             * @param args array di argomenti forniti al comando
             */
            public void execute(final String[] args) {
                if (!PLAYER_CONTROLLER.isGameRunning()) {
                    console.println("Nessuna partita in corso");
                    return;
                }

                String answer;
                do {
                    console.println("Sei sicuro di voler abbandonare "
                            + "la partita in corso? [si | no]");
                    answer = keyboard.nextLine();
                } while (!answer.equalsIgnoreCase("si")
                        && !answer.equalsIgnoreCase("no"));

                if (answer.equalsIgnoreCase("si")) {
                    console.println("Hai abbandonato la partita");
                    PLAYER_CONTROLLER.endGame();
                }
            }
        },

        /**
         * Comando che rappresenta il tentativo effettuato dal giocatore.
         *
         * <p>Utilizza un solo argomento, ovvero il tentativo stesso</p>
         */
        GUESS(1) {
            /**
             * Prova ad effettuare un tentativo.
             *
             * <p>Se va a buon fine stampa la board di gioco aggiornata,
             * altrimenti stampa l'errore occorso.</p>
             *
             * @param args array di argomenti forniti al comando
             */
            public void execute(final String[] args) {
                String guessWord = args[0];

                try {
                    PLAYER_CONTROLLER.guess(guessWord);
                    console.printBoard();
                    console.printGuessResult();
                    if (PLAYER_CONTROLLER.getNumRemainingGuesses() == 0
                            || PLAYER_CONTROLLER.getGuessResult()) {
                        PLAYER_CONTROLLER.endGame();
                    }
                } catch (WordleGameException | IllegalArgumentException e) {
                    console.println(e.getMessage());
                }
            }
        },

        /**
         * Comando {@literal /esci}.
         *
         * <p>Non si aspetta nessun argomento</p>
         */
        ESCI(0) {
            /**
             * Chiude il gioco.
             *
             * <p>Chiede conferma dell'azione, in seguito imposta
             * il flag {@link App#running} a false.</p>
             *
             * @param args array di argomenti forniti al comando
             */
            public void execute(final String[] args) {
                String answer;

                do {
                    console.println("Sei sicuro di voler uscire da Wordle? "
                            + "[si | no]");
                    answer = keyboard.nextLine();
                } while (!answer.equalsIgnoreCase("si")
                        && !answer.equalsIgnoreCase("no"));

                if (answer.equalsIgnoreCase("si")) {
                    running = false;
                }
            }
        },

        /**
         * Comando {@literal /mostra}.
         *
         * <p>Non si aspetta nessun argomento</p>
         */
        MOSTRA(0) {
            /**
             * Mostra la parola segreta impostata.
             *
             * <p>Se la parola segreta non e' stata ancora
             * impostata mostra invece un messaggio di errore.</p>
             *
             * @param args array di argomenti forniti al comando
             */
            public void execute(final String[] args) {

                try {
                    console.format("Parola segreta: %s\n",
                            WORDSMITH_CONTROLLER.getSecretWord());
                } catch (WordleSettingException e) {
                    console.println(e.getMessage());
                }
            }
        },

        /**
         * Comando {@literal /help}.
         *
         * <p>Non si aspetta nessun argomento</p>
         */
        HELP(0) {
            /**
             * Mostra una breve descrizione del gioco, seguita dall'elenco
             * dei comandi disponibili.
             *
             * @param args array di argomenti forniti al comando
             */
            public void execute(final String[] args) {
                console.printDescription();
                console.printHelp();
            }
        };

        /** Numero di argomenti atteso per il comando. */
        private final int numArguments;

        Command(final int numArgs) {
            this.numArguments = numArgs;
        }

        /**
         * Restituisce il numero di argomenti attesi per il comando.
         *
         * @return numero di argomenti attesi
         */
        public int getNumArgs() {
            return this.numArguments;
        }

        /**
         * Esecuzione del comando individuato.
         *
         * @param args array di argomenti forniti al comando
         */
        public abstract void execute(String[] args);
    }


    //costruttore privato
    private App() {
        //il costruttore privato fa si che questa
        // classe non possa essere istanziata
    }

    /**
     * Contiene il ciclo principale di gioco in cui a seconda del
     * comando riconosciuto vengono eseguite le corrispondenti
     * istruzioni.
     *
     * <p>Successivamente all'invocazione viene visualizzata una breve
     * descrizione del gioco. Se l'app viene invocata con il flag
     * "--help" oppure "-h" viene stampata la lista dei comandi
     * disponibili.</p>
     *
     * @param args argomenti in input da linea di comando
     */
    public static void main(final String[] args) {
        //controlla codifica del terminale su cui l'app e' eseguita
        // e imposta keyboard e console
        try {
            Charset encoding = getSystemEncoding();
            keyboard = new Scanner(
                    new InputStreamReader(System.in, encoding));
            console = new WordlePrinter(
                    new OutputStreamWriter(System.out, encoding),
                    PLAYER_CONTROLLER);

        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }


        // stampe iniziali
        console.printDescription();
        if (args.length > 0 && (args[0].equals("--help")
                || args[0].equals("-h"))) {
            console.printHelp();
        }

        while (running) {
            console.printf("Wordle> ");
            String inputLine = keyboard.nextLine();

            // invia l'input al parser
            PARSER.feed(inputLine);
            ParserToken parserToken = PARSER.getParserToken();
            Command command = parserToken.getCommand();
            String[] arguments = parserToken.getArgs();

            // esegui comando riconosciuto
            if (parserToken.hasMissingArgs()) {
                console.println("Argomenti mancanti: "
                        + parserToken.getNumMissingArgs());
            } else {
                if (command == Command.INVALID) {
                    arguments = parserToken.getCloseCommandsStrings();
                }
                command.execute(arguments);
            }
        }

        System.exit(0);
    }

    /**
     * Restituisce l'encoding del sistema su cui e' eseguito il programma.
     *
     * <p>Gli unici encoding supportati sono UTF-8 e UTF-16.</p>
     *
     * @return il charset corrispondente all'encoding valido
     * @throws UnsupportedEncodingException se l'encoding del sistema e' diverso
     * da UTF-8 e da UTF-16
     */
    public static Charset getSystemEncoding()
            throws UnsupportedEncodingException {
        String encoding = System.getProperty("file.encoding");
        if (encoding.equalsIgnoreCase("UTF-8")) {
            return StandardCharsets.UTF_8;
        } else if (encoding.equalsIgnoreCase("UTF-16")) {
            return StandardCharsets.UTF_16;
        } else {
            throw new UnsupportedEncodingException("Codifica "
                            + "[" + encoding + "] non supportata");
        }
    }
}
