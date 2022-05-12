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

        Command command = Parser.parseInput(inputLine);
        //inputParse comando per parsificare l'input

        while (!inputLine.equals("exit"))
        {
            switch (command)
            {
                case DUMMY:
                    Printer.printDummy();
                    break;
                case INVALID:
                    Printer.printInvalid();
                    break;
            }
            System.out.println("Inserisci un comando: ");
            inputLine = keyboardInput.nextLine();
            command = Parser.parseInput(inputLine);
        }

        System.out.println("ciao");
    }
}
