package it.uniba.app;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * {@literal <<Boundary>>} <br>
 * Classe principale dell'applicazione. <p></p>
 * Contiene il main e i metodi specifici per l'esecuzione dei comandi inseriti dall'utente.
 * Utilizza {@link Parser} per processare l'input e {@link Printer} per visualizzare l'output.
 */
public final class App
{

    static Scanner keyboardInput = new Scanner (new InputStreamReader(System.in));
    static Printer consoleOutput = new Printer();
    static Parser parser = new Parser();

    /**
     * Contiene il ciclo principale di gioco in cui a seconda del comando riconosciuto
     * vengono eseguite le corrispondenti istruzioni. <p></p>
     * Successivamente all'invocazione viene visualizzata una breve descrizione del gioco.
     * Se l'app viene invocata con il flag "--help" oppure "-h" viene stampata la lista dei comandi disponibili.
     * @param args argomenti in input da linea di comando
     */
    public static void main(final String[] args)
    {
        //controlla codifica del terminale su cui l'app è eseguita
        checkEncoding();

        // stampe iniziali
        consoleOutput.printDescription();
        if (args.length > 0 && (args[0].equals("--help") || args[0].equals("-h")))
            consoleOutput.printHelp();

        System.out.print("Wordle> ");
        String inputLine = keyboardInput.nextLine();

        // invia l'input al parser
        parser.feed(inputLine);
        Command command = parser.getCommand();
        String[] arguments = parser.getArgs();
        while (true)
        {
            switch (command)
            {
                case GIOCA:
                    executeStart();
                    break;
                case NUOVA:
                    executeSetSecretWord(arguments[0]);
                    break;
                case ABBANDONA:
                    executeQuitGame();
                    break;
                case GUESS:
                    executeGuess(arguments[0]);
                    break;
                case INVALID:
                    consoleOutput.println("Comando invalido");
                    break;
                case ESCI:
                    executeExitGame();
                    break;
                case MOSTRA:
                    executePrintSecretWord();
                    break;
                case HELP:
                    consoleOutput.printDescription();
                    consoleOutput.printHelp();
                    break;
            }

            System.out.print("Wordle> ");
            inputLine = keyboardInput.nextLine();

            parser.feed(inputLine);
            command = parser.getCommand();
            arguments = parser.getArgs();
        }
    }

    public static void executeStart()
    {
        try
        {
            Wordle.startGame();
            consoleOutput.println("Hai iniziato la partita");
            consoleOutput.printBoard();
        }
        catch (WordleGameException | WordleSettingException e)
        {
            consoleOutput.println(e.getMessage());
        }
    }

    public static void executeSetSecretWord(String secretWord)
    {
        if (secretWord == null)
        {
            consoleOutput.printMissingArgs();
            return;
        }

        try
        {
            Wordle.setSecretWord(secretWord);
            consoleOutput.println("OK");
        }
        catch (IllegalArgumentException | WordleGameException e)
        {
            consoleOutput.println(e.getMessage());
        }
    }

    public static void executeQuitGame()
    {
        if(!Wordle.isGameRunning())
        {
            consoleOutput.println("Nessuna partita in corso");
            return;
        }

        String answer;
        do
        {
            consoleOutput.println("Sei sicuro di voler abbandonare la partita in corso? [si | no]");
            answer = keyboardInput.nextLine();
        } while (!answer.equalsIgnoreCase("si") && !answer.equalsIgnoreCase("no"));

        if (answer.equalsIgnoreCase("si"))
        {
            try
            {
                consoleOutput.println("Hai abbandonato la partita");
                Wordle.endGame();
            }
            catch (WordleGameException e)
            {
                consoleOutput.println(e.getMessage());
            }
        }
    }

    public static void executeGuess(String guessWord)
    {
        try
        {
            Wordle.guess(guessWord);
            consoleOutput.printBoard();
            consoleOutput.printGuessResult();
            if(Wordle.getNumRemainingGuesses() == 0 || Wordle.getGuessResult())
                Wordle.endGame();
        }
        catch (WordleGameException | IllegalArgumentException e)
        {
            consoleOutput.println(e.getMessage());
        }
    }

    public static void executeExitGame()
    {
        String answer;
        do
        {
            consoleOutput.println("Sei sicuro di voler uscire da Wordle? [si | no]");
            answer = keyboardInput.nextLine();
        } while (!answer.equalsIgnoreCase("si") && !answer.equalsIgnoreCase("no"));

        if (answer.equalsIgnoreCase("si"))
            System.exit(0);
    }

    public static void executePrintSecretWord()
    {
        try
        {
            consoleOutput.format("Parola segreta: %s\n", Wordle.getSecretWord());
        }
        catch (WordleSettingException e)
        {
            consoleOutput.println(e.getMessage());
        }
    }

    public static void checkEncoding()
    {
        String encoding = System.getProperty("file.encoding");
        if(!encoding.equalsIgnoreCase("UTF-8") && !encoding.equalsIgnoreCase("UTF-16"))
        {
            consoleOutput.println("Codifica [" + encoding + "] non supportata");
            System.exit(1);
        }
    }
}