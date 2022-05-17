package it.uniba.app;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Main class of the application.
 */
public final class App
{

    static Scanner keyboardInput = new Scanner (new InputStreamReader(System.in));
    static Printer consoleOutput = new Printer();
    static Parser parser = new Parser();

    /**
     * Entrypoint of the application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args)
    {
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
}