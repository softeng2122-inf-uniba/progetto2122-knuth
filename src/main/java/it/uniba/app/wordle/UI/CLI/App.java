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
 * Classe principale dell'applicazione. <p></p>
 * Contiene il main e i metodi specifici per l'esecuzione dei comandi
 * inseriti dall'utente.
 * Utilizza {@link Parser} per processare l'input e {@link WordlePrinter} per
 * visualizzare l'output.
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
     * {@literal <<NoECB>>} <br>
     * L'enumerazione Command contiene le costanti che rappresentano
     * i comandi riconosciuti dal gioco.
     * Le costanti INVALID e SPACE sono comandi fittizi per gestire
     * eventuali errori di input e caratteri di spaziatura.
     */
    public enum Command {
        /**
         * Comando invalido, ovvero un qualsiasi comando inesistente
         * che l'utente ha provato a inserire.
         */
        INVALID(0) {
            /**
             * Effettua una stampa per notificare l'invalidità del comando,
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
                        System.out.println("Il comando più simile è: ");
                    } else {
                        System.out.println("I comandi più simili sono: ");
                    }
                    for (String closeCommandString : args) {
                        System.out.println("\t\t" + closeCommandString);
                    }
                }
            }
        },

        /**
         * Comando associato all'inserimento di soli spazi da parte
         * dell'utente.
         */
        SPACE(0) {
            /**
             * Non effettua alcun'azione.
             *
             * @param args risulterà in un array sempre vuoto
             */
            public void execute(final String[] args) {
            }
        },

        /**
         * Comando {@literal /gioca}.
         */
        GIOCA(0) {
            /**
             * Prova a iniziare una nuova partita.
             *
             * @param args argomenti forniti al comando
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
         */
        NUOVA(1) {
            /**
             * Prova a impostare la parola segreta della sessione di gioco.
             *
             * @param args argomenti forniti al comando
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
         */
        ABBANDONA(0) {
            /**
             * Prova ad abbandonare la partita in corso.
             *
             * <p>Se una partita è effettivamente in corso chiede
             * conferma dell'azione.</p>
             *
             * @param args argomenti forniti al comando
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
         */
        GUESS(1) {
            /**
             * Prova ad effettuare un tentativo.
             *
             * <p>Se va a buon fine stampa la board di gioco,
             * altrimenti stampa l'errore occorso.</p>
             *
             * @param args argomenti forniti al comando
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
         */
        ESCI(0) {
            /**
             * Chiude il gioco.
             *
             * <p>Chiede conferma dell'azione, in seguito imposta
             * il flag {@link App#running} a false.</p>
             *
             * @param args argomenti forniti al comando
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
         */
        MOSTRA(0) {
            /**
             * Mostra la parola segreta impostata.
             *
             * <p>Se la parola segreta non è stata ancora
             * impostata mostra invece un messaggio di errore.</p>
             *
             * @param args argomenti forniti al comando
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
         */
        HELP(0) {
            /**
             * Mostra una breve descrizione del gioco, seguita dall'elenco
             * dei comandi disponibili.
             *
             * @param args argomenti forniti al comando
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
         * @param args argomenti forniti al comando, per i comandi che
         *             non richiedono argomenti esso sarà vuoto
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
        //controlla codifica del terminale su cui l'app è eseguita
        // e imposta keyboard e console
        try {
            Charset encoding = getSystemEncoding();
            keyboard = new Scanner(
                    new InputStreamReader(System.in, encoding));
            console = new WordlePrinter(
                    new OutputStreamWriter(System.out, encoding),
                    PLAYER_CONTROLLER);

        } catch(UnsupportedEncodingException e) {
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
            System.out.print("Wordle> ");
            String inputLine = keyboard.nextLine();

            // invia l'input al parser
            PARSER.feed(inputLine);
            ParserToken parserToken = PARSER.getParserToken();
            Command command = parserToken.getCommand();
            String[] arguments = parserToken.getArgs();

            // esegui comando riconosciuto
            if (parserToken.hasMissingArgs()) {
                System.out.println("Argomenti mancanti: "
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
     * Restituisce l'encoding del sistema su cui è eseguito il programma.
     *
     * <p>Gli unici encoding supportati sono UTF-8 e UTF-16.</p>
     *
     * @return il charset corrispondente all'encoding valido
     * @throws UnsupportedEncodingException se l'encoding del sistema è diverso
     * da UTF-8 e da UTF-16
     */
    public static Charset getSystemEncoding() throws UnsupportedEncodingException {
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
