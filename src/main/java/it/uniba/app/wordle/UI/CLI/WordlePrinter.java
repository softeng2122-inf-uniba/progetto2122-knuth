package it.uniba.app.wordle.UI.CLI;

import it.uniba.app.wordle.domain.Color;
import it.uniba.app.wordle.domain.PlayerController;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * {@literal <<Boundary>>} <br>
 * Classe per effettuare la stampa dell'output da visualizzare
 * sulla console.
 *
 * <p>Utilizza codici ANSI per la visualizzazione dei colori
 * e costanti UNICODE nelle stampe di gioco.</p>
 */
public final class WordlePrinter extends PrintWriter {
    // angoli (L = left, R = right, U = up, D = down)
    /** Angolo in alto a sinistra. */
    private static final char L_U_ANGLE = '\u2554';
    /** Angolo in alto a destra. */
    private static final char R_U_ANGLE = '\u2557';
    /** Angolo in basso a sinistra. */
    private static final char L_D_ANGLE = '\u255A';
    /** Angolo in basso a destra. */
    private static final char R_D_ANGLE = '\u255D';

    // separatori
    /** Separatore sinistro. */
    private static final char L_SEPARATOR = '\u2560';
    /** Separatore destro. */
    private static final char R_SEPARATOR = '\u2563';
    /** Separatore superiore. */
    private static final char U_SEPARATOR = '\u2566';
    /** Separatore inferiore. */
    private static final char D_SEPARATOR = '\u2569';
    /** Separatore a croce. */
    private static final char CROSS = '\u256c';

    // spigoli
    /** Spigolo verticale. */
    private static final char VERTICAL_EDGE = '\u2551';
    /** Spigolo orizzontale. */
    private static final char HORIZONTAL_EDGE = '\u2550';

    // unioni
    /** Stringa formata da tre spigoli orizzontali. */
    private static final String HORIZONTAL_EDGE_X3 = HORIZONTAL_EDGE + ""
            + HORIZONTAL_EDGE + HORIZONTAL_EDGE;

    //backgrounds e grassetto
    /** Sfondo grigio. */
    private static final String GREY_BACKGROUND = "\u001b[30;47m"; //GREY
    /** Sfondo verde. */
    private static final String GREEN_BACKGROUND = "\u001b[30;42m";  // GREEN
    /** Sfondo yellow. */
    private static final String YELLOW_BACKGROUND = "\u001b[30;43m"; // YELLOW
    /** Grassetto. */
    private static final String BOLD = "\u001b[1m";
    /** Reset (sfondo e grassetto). */
    private static final String RESET = "\u001b[0m";  // Text Reset

    /** Controller per recuperare le informazioni sulla partita. */
    private final PlayerController playerController;

    WordlePrinter(final OutputStreamWriter out,
                  final PlayerController controller) {
        super(out, true);
        this.playerController = controller;
    }

    /**
     * Stampa la matrice dei tentativi per la partita in corso,
     * impostando l'opportuno colore di sfondo delle caselle.
     */
    public void printBoard() {
        int rows = playerController.getMaxGuesses();
        int columns = playerController.getWordLength();

        //stampa parte superiore della Board
        println(upperPart(columns));

        // stampa le righe (dalla prima alla penultima)
        for (int i = 0; i < rows - 1; i++) {
            println(guessSlice(i));
            println(separatorSlice(columns));
        }

        // stampa ultima riga
        println(guessSlice(rows - 1));
        println(lowerPart(columns));
    }

    // riga di bordo superiore della board
    private String upperPart(final int wordLength) {
        StringBuilder upperPart = new StringBuilder(L_U_ANGLE + ""
                + HORIZONTAL_EDGE_X3);

        for (int i = 1; i <= wordLength - 1; i++) {
            upperPart.append(U_SEPARATOR + "" + HORIZONTAL_EDGE_X3);
        }

        upperPart.append(R_U_ANGLE);

        return upperPart.toString();
    }

    // riga intermedia della board contenente le celle colorate con le lettere
    private String guessSlice(final int row) {
        StringBuilder guessSlice = new StringBuilder(VERTICAL_EDGE + "");
        Color c;
        char l;
        String background;

        for (int i = 0; i < playerController.getWordLength(); i++) {
            l = playerController.getLetter(row, i);
            c = playerController.getColor(row, i);

            switch (c) {
                case GREEN:
                    background = GREEN_BACKGROUND;
                    break;
                case YELLOW:
                    background = YELLOW_BACKGROUND;
                    break;
                case GREY:
                    background = GREY_BACKGROUND;
                    break;
                default:  // NO_COLOR
                    background = "";
            }
            guessSlice.append(background + " " + l + " " + RESET)
                    .append(VERTICAL_EDGE);
        }
        return guessSlice.toString();
    }

    // riga intermedia di separazione tra tentativi
    private String separatorSlice(final int wordLength) {
        StringBuilder separatorSlice = new StringBuilder(L_SEPARATOR + ""
                + HORIZONTAL_EDGE_X3);

        for (int i = 1; i <= wordLength - 1; i++) {
            separatorSlice.append(CROSS + HORIZONTAL_EDGE_X3);
        }

        separatorSlice.append(R_SEPARATOR);

        return separatorSlice.toString();
    }

    // riga di bordo inferiore della board
    private String lowerPart(final int wordLength) {
        StringBuilder lowerPart = new StringBuilder(L_D_ANGLE + ""
                + HORIZONTAL_EDGE_X3);

        for (int i = 1; i <= wordLength - 1; i++) {
            lowerPart.append(D_SEPARATOR + HORIZONTAL_EDGE_X3);
        }

        lowerPart.append(R_D_ANGLE);

        return lowerPart.toString();
    }

    /**
     * Stampa un avviso in caso di parola segreta indovinata
     * o esaurimento di tentativi disponibili.
     */
    public void printGuessResult() {
        int maxGuesses = playerController.getMaxGuesses();
        int remainingGuesses = playerController.getNumRemainingGuesses();

        if (playerController.getGuessResult()) {
            println("Parola segreta indovinata");
            println("Numero di tentativi: " + (maxGuesses - remainingGuesses));
        } else {
            if (remainingGuesses == 0) {
                println("Hai raggiunto il numero massimo di tentativi");
                println("La parola segreta è: "
                        + playerController.getGameSecretWord());
            }
        }
    }

    /**
     * Stampa una breve descrizione del gioco Wordle.
     */
    public void printDescription() {
        printWordleLogo();

        println("Wordle è un videogioco in cui il giocatore "
                + "deve indovinare una parola segreta");
        println("avendo a disposizione 6 tentativi che dovranno "
                + "essere parole di una lunghezza");
        println("prestabilita (nel nostro caso 5 lettere).");
        println("Ad ogni tentativo, ogni lettera della parola inserita "
                + "viene evidenziata in:");

        print(GREEN_BACKGROUND + "VERDE" + RESET);
        println(" se è contenuta nella parola segreta ed è nella"
                + " giusta posizione");

        print(YELLOW_BACKGROUND + "GIALLO" + RESET);
        println(" se è contenuta nella parola segreta ma in una"
                + " posizione diversa");

        print(GREY_BACKGROUND + "GRIGIO" + RESET);
        println(" se non è contenuta nella parola segreta\n");
    }

    private void printWordleLogo() {
        final int wordleLength = 6;
        println(upperPart(wordleLength));
        print(VERTICAL_EDGE);

        print(coloredLetterSpace('W', Color.GREEN) + VERTICAL_EDGE);
        print(coloredLetterSpace('O', Color.GREY) + VERTICAL_EDGE);
        print(coloredLetterSpace('R', Color.YELLOW) + VERTICAL_EDGE);
        print(coloredLetterSpace('D', Color.GREEN) + VERTICAL_EDGE);
        print(coloredLetterSpace('L', Color.YELLOW) + VERTICAL_EDGE);
        println(coloredLetterSpace('E', Color.GREY) + VERTICAL_EDGE);

        println(lowerPart(wordleLength));
    }

    private String coloredLetterSpace(final char c, final Color color) {
        String background;

        switch (color) {
            case GREEN:
                background = GREEN_BACKGROUND;
                break;
            case YELLOW:
                background = YELLOW_BACKGROUND;
                break;
            case GREY:
                background = GREY_BACKGROUND;
                break;
            default:
                background = "";
        }
        return (background + " " + c + " " + RESET);
    }

    /**
     * Stampa l'elenco dei comandi disponibili seguiti
     * da una loro breve descrizione.
     */
    public void printHelp() {
        println("Il gioco accetta i seguenti comandi:");
        println(BOLD + "\t/gioca" + RESET + "\t\t\tinizia una nuova partita "
                + "(la parola segreta deve essere impostata)");
        println(BOLD + "\t/nuova <par>" + RESET + "\timposta <par> "
                + "come parola segreta");
        println(BOLD + "\t/mostra" + RESET + "\t\t\tvisualizza la parola "
                + "segreta impostata");
        println(BOLD +  "\t/abbandona" + RESET + "\t\tabbandona la "
                + "partita in corso");
        println(BOLD + "\t/esci" + RESET + "\t\t\tchiude il gioco");
        println(BOLD + "\t<parola>" + RESET + "\t\tper effettuare "
                + "un tentativo\n");
    }
}
