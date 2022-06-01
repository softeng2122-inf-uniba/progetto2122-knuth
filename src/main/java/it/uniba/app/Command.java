package it.uniba.app;

import java.util.Scanner;

/**
 * {@literal <<Boundary>>} <br>
 * Il tipo enumerativo Command si ispira al design pattern omonimo.
 * A ogni istanza di Command è associato il numero di argomenti atteso e l'implementazione
 * corrispondente del metodo execute.
 */
public enum Command
{
    INVALID(0) {
        public void execute(String[] args) {
            consoleOutput.println("Comando invalido");
        }
    },

    SPACE(0) {
        public void execute(String[] args) {
        }

        public String toString() {
            return null;
        }
    },

    GIOCA(0) {
        public void execute(String[] args) {
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
    },

    NUOVA(1) {
        public void execute(String[] args) {

            String secretWord = args[0];
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
    },

    ABBANDONA(0) {
        public void execute(String[] args) {
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
                consoleOutput.println("Hai abbandonato la partita");
                Wordle.endGame();
            }
        }
    },

    GUESS(1) {
        public void execute(String[] args) {
            String guessWord = args[0];
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
    },

    ESCI(0) {
        public void execute(String[] args) {
            String answer;
            do
            {
                consoleOutput.println("Sei sicuro di voler uscire da Wordle? [si | no]");
                answer = keyboardInput.nextLine();
            } while (!answer.equalsIgnoreCase("si") && !answer.equalsIgnoreCase("no"));

            if (answer.equalsIgnoreCase("si"))
                System.exit(0);
        }
    },

    MOSTRA(0) {
        public void execute(String[] args) {
            try
            {
                consoleOutput.format("Parola segreta: %s\n", Wordle.getSecretWord());
            }
            catch (WordleSettingException e)
            {
                consoleOutput.println(e.getMessage());
            }
        }
    },

    HELP(0) {
        public void execute(String[] args) {
            consoleOutput.printDescription();
            consoleOutput.printHelp();
        }
    };

    private final int numArgs;
    private static Scanner keyboardInput;
    private static Printer consoleOutput;

    Command(int numArgs) {
        this.numArgs = numArgs;
    }

    public int getNumArgs() {
        return this.numArgs;
    }

    public static void setStreams(Scanner keyboardInput, Printer consoleOutput) {
        Command.keyboardInput = keyboardInput;
        Command.consoleOutput = consoleOutput;
    }
    public abstract void execute(String[] args);
}
