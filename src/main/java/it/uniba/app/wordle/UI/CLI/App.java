package it.uniba.app.wordle.UI.CLI;

import it.uniba.app.wordle.domain.PlayerController;
import it.uniba.app.wordle.domain.WordlePlayerController;
import it.uniba.app.wordle.domain.WordleWordsmithController;
import it.uniba.app.wordle.domain.WordsmithController;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * {@literal <<Boundary>>} <br>
 * Classe principale dell'applicazione. <p></p>
 * Contiene il main e i metodi specifici per l'esecuzione dei comandi
 * inseriti dall'utente.
 * Utilizza {@link Parser} per processare l'input e {@link Printer} per
 * visualizzare l'output.
 */
public final class App {

    private static final PlayerController PLAYER_CONTROLLER =
            new WordlePlayerController();

    private static final WordsmithController WORDSMITH_CONTROLLER =
                 new WordleWordsmithController(
                    (WordlePlayerController) PLAYER_CONTROLLER);

    private static final Scanner KEYBOARD = new Scanner(
            new InputStreamReader(System.in, getSystemEncoding()));
    private static final Printer CONSOLE = new Printer(PLAYER_CONTROLLER);
    private static final Parser PARSER = new Parser();

    //costruttore privato
    private App() {
        //il costruttore privato fa si che questa
        // classe non possa essere istanziata
    }

    /**
     * Contiene il ciclo principale di gioco in cui a seconda del
     * comando riconosciuto vengono eseguite le corrispondenti
     * istruzioni. <p></p>
     * Successivamente all'invocazione viene visualizzata una breve
     * descrizione del gioco. Se l'app viene invocata con il flag
     * "--help" oppure "-h" viene stampata la lista dei comandi
     * disponibili.
     * @param args argomenti in input da linea di comando
     */
    public static void main(final String[] args) {
        //controlla codifica del terminale su cui l'app è eseguita
        checkEncoding();

        // setta le stream di input e output della classe command
        Command.setStreams(KEYBOARD, CONSOLE);

        Command.setControllers(WORDSMITH_CONTROLLER, PLAYER_CONTROLLER);

        // stampe iniziali
        CONSOLE.printDescription();
        if (args.length > 0 && (args[0].equals("--help")
                || args[0].equals("-h"))) {
            CONSOLE.printHelp();
        }

        System.out.print("Wordle> ");
        String inputLine = KEYBOARD.nextLine();

        // invia l'input al parser
        PARSER.feed(inputLine);
        ParserToken parserToken = PARSER.getParserToken();
        Command command = parserToken.getCommand();
        String[] arguments = parserToken.getArgs();

        while (true) {
            // esegui comando riconosciuto
            if (parserToken.areMissingArgs()) {
                System.out.println("Argomenti mancanti: "
                        + parserToken.getNumMissingArgs());
            } else {
                if (command == Command.INVALID) {
                    command.execute(parserToken.getCloseCommandsStrings());
                } else {
                    command.execute(arguments);
                }
            }

            System.out.print("Wordle> ");
            inputLine = KEYBOARD.nextLine();

            PARSER.feed(inputLine);
            parserToken = PARSER.getParserToken();
            command = parserToken.getCommand();
            arguments = parserToken.getArgs();
        }
    }

    public static void checkEncoding() {
        String encoding = System.getProperty("file.encoding");

        if (!encoding.equalsIgnoreCase("UTF-8")
                && !encoding.equalsIgnoreCase("UTF-16")) {
            CONSOLE.println(
                    "Codifica [" + encoding + "] non supportata");
            CONSOLE.println(
                    "Alcuni caratteri potrebbero non essere "
                            + "visualizzati correttamente");
        }
    }

    public static Charset getSystemEncoding() {
        String encoding = System.getProperty("file.encoding");
        if (encoding.equalsIgnoreCase("UTF-8")) {
            return StandardCharsets.UTF_8;
        }
        else if (encoding.equalsIgnoreCase("UTF-16")) {
            return StandardCharsets.UTF_16;
        }
        else {
            throw new IllegalStateException("Codifica non supportata");
        }
    }
}
