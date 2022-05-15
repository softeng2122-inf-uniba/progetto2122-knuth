package it.uniba.app;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Main class of the application.
 */
public final class App {

    /**
     * Get a greeting sentence.
     *
     * @return the "Hello World!" string.
     */
    public String getGreeting() {
        return "Hello World!";
    }

    /**
     * Entrypoint of the application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args)
    {
        Scanner keyboardInput = new Scanner (new InputStreamReader(System.in));
        System.out.println("Inserisci un comando: ");
        String inputLine = keyboardInput.nextLine();
        Parser parser = new Parser();

        // invia l'input al parser
        parser.feed(inputLine);
        Command command = parser.getCommand();
        String[] arguments = parser.getArgs();
        while (!inputLine.equals("exit"))
        {
            switch (command)
            {
                case DUMMY:
                    Printer.printDummy();
                    break;
                case GIOCA:
                    executeStart();
                    break;
                case NUOVA:
                    executeSetSecretWord(arguments[0]);
                    break;
                case GUESS:
                    executeGuess(arguments[0]);
                    break;
                case INVALID:
                    Printer.printInvalid();
                    break;
            }
            System.out.println("Inserisci un comando: ");
            inputLine = keyboardInput.nextLine();
            parser.feed(inputLine);
            command = parser.getCommand();
            arguments = parser.getArgs();
        }

        System.out.println("ciao");
    }

    public static void executeStart()
    {
        try
        {
            Wordle.startGame();
            Printer.printStartGame();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void executeSetSecretWord(String secretWord)
    {
        if (secretWord == null)
        {
            Printer.printMissingArgs();
            return;
        }
        try
        {
            Wordle.setSecretWord(secretWord);
            Printer.printSetSecretWord();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void executeGuess(String guessWord)
    {
        if (guessWord == null)
        {
            Printer.printMissingArgs();
            return;
        }
        try
        {
            Wordle.guess(guessWord);
            Printer.printBoard();
            Printer.printGuessResult();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}