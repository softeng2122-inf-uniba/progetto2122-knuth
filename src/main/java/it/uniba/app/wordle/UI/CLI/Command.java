package it.uniba.app.wordle.UI.CLI;

import it.uniba.app.wordle.domain.Wordle;
import it.uniba.app.wordle.domain.WordleGameException;
import it.uniba.app.wordle.domain.WordleSettingException;

import java.util.Scanner;

/**
 * {@literal <<NoECB>>} <br>
 * Il tipo enumerativo Command contiene le costanti che rappresentano
 * i comandi riconosciuti dal gioco.
 * Le costanti INVALID e SPACE sono comandi fittizi per gestire
 * eventuali errori di input e caratteri di spaziatura.
 */
public enum Command {
    INVALID(0) {
        public void execute(final String[] args) {
            consoleOutput.println("Comando invalido");
            if (args != null) {
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

        public String toString() {
            return null;
        }
    },

    SPACE(0) {
        public void execute(final String[] args) {
        }

        public String toString() {
            return null;
        }
    },

    GIOCA(0) {
        public void execute(final String[] args) {
            try {
                Wordle.startGame();
                consoleOutput.println("Hai iniziato la partita");
                consoleOutput.printBoard();
            } catch (WordleGameException | WordleSettingException e) {
                consoleOutput.println(e.getMessage());
            }
        }
    },

    NUOVA(1) {
        public void execute(final String[] args) {

            String secretWord = args[0];
            if (secretWord == null) {
                consoleOutput.printMissingArgs();
                return;
            }

            try {
                Wordle.setSecretWord(secretWord);
                consoleOutput.println("OK");
            } catch (IllegalArgumentException | WordleGameException e) {
                consoleOutput.println(e.getMessage());
            }
        }
    },

    ABBANDONA(0) {
        public void execute(final String[] args) {
            if (!Wordle.isGameRunning()) {
                consoleOutput.println("Nessuna partita in corso");
                return;
            }

            String answer;
            do {
                consoleOutput.println("Sei sicuro di voler abbandonare"
                                      + "la partita in corso? [si | no]");
                answer = keyboardInput.nextLine();
            } while (!answer.equalsIgnoreCase("si")
                    && !answer.equalsIgnoreCase("no"));

            if (answer.equalsIgnoreCase("si")) {
                consoleOutput.println("Hai abbandonato la partita");
                Wordle.endGame();
            }
        }
    },

    GUESS(1) {
        public void execute(final String[] args) {
            String guessWord = args[0];

            try {
                Wordle.guess(guessWord);
                consoleOutput.printBoard();
                consoleOutput.printGuessResult();
                if (Wordle.getNumRemainingGuesses() == 0
                        || Wordle.getGuessResult()) {
                    Wordle.endGame();
                }
            } catch (WordleGameException | IllegalArgumentException e) {
                consoleOutput.println(e.getMessage());
            }
        }
    },

    ESCI(0) {
        public void execute(final String[] args) {
            String answer;

            do {
                consoleOutput.println("Sei sicuro di voler uscire da Wordle?"
                                      + "[si | no]");
                answer = keyboardInput.nextLine();
            } while (!answer.equalsIgnoreCase("si")
                    && !answer.equalsIgnoreCase("no"));

            if (answer.equalsIgnoreCase("si")) {
                System.exit(0);
            }
        }
    },

    MOSTRA(0) {
        public void execute(final String[] args) {

            try {
                consoleOutput.format("Parola segreta: %s\n",
                        Wordle.getSecretWord());
            } catch (WordleSettingException e) {
                consoleOutput.println(e.getMessage());
            }
        }
    },

    HELP(0) {
        public void execute(final String[] args) {
            consoleOutput.printDescription();
            consoleOutput.printHelp();
        }
    };

    private final int numArgs;
    private static Scanner keyboardInput;
    private static Printer consoleOutput;

    Command(final int numArgs) {
        this.numArgs = numArgs;
    }

    public int getNumArgs() {
        return this.numArgs;
    }

    public static void setStreams(final Scanner keyboardInput,
                                  final Printer consoleOutput) {

        Command.keyboardInput = keyboardInput;
        Command.consoleOutput = consoleOutput;
    }

    public abstract void execute(String[] args);
}