package it.uniba.app;

import java.io.InputStreamReader;
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

    static Scanner keyboardInput = new Scanner(
            new InputStreamReader(System.in));
    static Printer consoleOutput = new Printer();
    static Parser parser = new Parser();

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
        Command.setStreams(keyboardInput, consoleOutput);

        // stampe iniziali
        consoleOutput.printDescription();
        if (args.length > 0 && (args[0].equals("--help")
                || args[0].equals("-h"))) {
            consoleOutput.printHelp();
        }

        System.out.print("Wordle> ");
        String inputLine = keyboardInput.nextLine();

        // invia l'input al parser
        parser.feed(inputLine);
        ParserToken parserToken = parser.getParserToken();
        Command command = parserToken.getCommand();
        String[] arguments = parserToken.getArgs();

        while (true)
        {
            // esegui comando riconosciuto
            if(parserToken.areMissingArgs()) {
                System.out.println("Argomenti mancanti: " + parserToken.getNumMissingArgs());
            } else {
                if(command == Command.INVALID){
                    command.execute(parserToken.getCloseCommandsStrings());
                } else {
                    command.execute(arguments);
                }
            }

            System.out.print("Wordle> ");
            inputLine = keyboardInput.nextLine();

            parser.feed(inputLine);
            parserToken = parser.getParserToken();
            command = parserToken.getCommand();
            arguments = parserToken.getArgs();
        }
    }

    public static void checkEncoding() {
        String encoding = System.getProperty("file.encoding");

        if (!encoding.equalsIgnoreCase("UTF-8")
                && !encoding.equalsIgnoreCase("UTF-16")) {
            consoleOutput.println(
                    "Codifica [" + encoding + "] non supportata");
            consoleOutput.println(
                    "Alcuni caratteri potrebbero non essere "
                            + "visualizzati correttamente");
        }
    }
}
