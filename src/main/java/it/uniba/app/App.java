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

    static Scanner keyboardInput = new Scanner (new InputStreamReader(System.in));
    static Printer consoleOutPut = new Printer();
    /**
     * Entrypoint of the application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args)
    {
        System.out.println("Inserisci un comando: ");
        String inputLine = keyboardInput.nextLine();
        Parser parser = new Parser();

        // invia l'input al parser
        parser.feed(inputLine);
        Command command = parser.getCommand();
        String[] arguments = parser.getArgs();
        while (true)
        {
            switch (command)
            {
                case DUMMY:
                    consoleOutPut.printDummy();
                    break;
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
                    consoleOutPut.printInvalid();
                    break;
                case ESCI:
                    executeExitGame();
                    break;

            }
            System.out.println("Inserisci un comando: ");
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
            consoleOutPut.printStartGame();
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
            consoleOutPut.printMissingArgs();
            return;
        }
        try
        {
            Wordle.setSecretWord(secretWord);
            consoleOutPut.printSetSecretWord();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void executeQuitGame()
    {
        if(!Wordle.isGameRunning())
        {
            consoleOutPut.println("Nessuna partita in corso");
            return;
        }

        String answer = null;

        do
        {
            consoleOutPut.println("Sei sicuro di voler abbandonare la partita in corso? [si | no]");
            answer = keyboardInput.nextLine();
        } while (!answer.equalsIgnoreCase("si") && !answer.equalsIgnoreCase("no"));

        if (answer.equalsIgnoreCase("si"))
        {
            try
            {
                consoleOutPut.println("Hai abbandonato la partita");
                Wordle.endGame();
            }
            catch (Exception e)
            {
                consoleOutPut.println(e.getMessage());
            }
        }
    }

    public static void executeGuess(String guessWord)
    {
        try
        {
            Wordle.guess(guessWord);
            consoleOutPut.printBoard();
            consoleOutPut.printGuessResult();
            if(Wordle.getNumRemainingGuesses() == 0 || Wordle.getGuessResult() == true)
                Wordle.endGame();
        }
        catch (Exception e)
        {
            consoleOutPut.println(e.getMessage());
        }
    }

    public static void executeExitGame()
    {
        String answer = null;
        do {
            consoleOutPut.println("Sei sicuro di voler uscire da Wordle? [si | no]");
            answer = keyboardInput.nextLine();
        } while (!answer.equalsIgnoreCase("si") && !answer.equalsIgnoreCase("no"));

        if (answer.equalsIgnoreCase("si")) {
            System.exit(0);
        }
    }

}