package it.uniba.app;

import java.io.PrintWriter;

/** Questa classe si occupa delle stampe sul terminale
 *
 */
public class Printer
{
    private static final PrintWriter printer = new PrintWriter(System.out,true);
    // angoli (L = left, R = right, U = up, D = down)
    private static final char L_U_ANGLE = '\u2554';
    private static final char R_U_ANGLE = '\u2557';
    private static final char L_D_ANGLE = '\u255A';
    private static final char R_D_ANGLE = '\u255D';

    // separatori
    private static final char L_SEPARATOR = '\u2560';
    private static final char R_SEPARATOR = '\u2563';
    private static final char U_SEPARATOR= '\u2566';
    private static final char D_SEPARATOR= '\u2569';
    private static final char CROSS = '\u256c';

    // spigoli
    private static final char VERTICAL_EDGE = '\u2551';
    private static final char HORIZONTAL_EDGE = '\u2550';

    // unioni
    private static final String HORIZONTAL_EDGE_X3 = HORIZONTAL_EDGE + "" + HORIZONTAL_EDGE + HORIZONTAL_EDGE;

    //backgrounds
    public static final String GREY_BACKGROUND = "\033[100m"; //GREY
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW

    public static void printBoard()
    {
        int rows = Wordle.getMaxGuesses();
        int columns = Wordle.getWordLength();

        //stampa parte superiore della Board
        printer.println(upperPart(columns));

        // stampa le righe (dalla prima alla penultima)
        for(int i = 0; i < rows - 1; i++)
        {
            printer.println(guessSlice(i));
            printer.println(separatorSlice(columns));
        }

        // stampa ultima riga
        printer.println(guessSlice(rows-1));
        printer.println(lowerPart(columns));
    }

    private static String upperPart(int wordLength)
    {
        //nota: effettuare il cambio in stringBuilder

        StringBuilder upperPart = new StringBuilder(L_U_ANGLE + "" + HORIZONTAL_EDGE_X3);

        for(int i = 1; i <= wordLength - 1; i++)
        {
            upperPart.append(U_SEPARATOR + "" + HORIZONTAL_EDGE_X3);
        }
        upperPart.append(R_U_ANGLE);

        return upperPart.toString();
    }

    private static String guessSlice(int row)
    {
        StringBuilder guessSlice = new StringBuilder(VERTICAL_EDGE + "");
        Color c;
        char l;
        String background = "";

        for(int i = 0; i < Wordle.getWordLength(); i++)
        {
            l = Wordle.getLetter(row, i);
            c = Wordle.getColor(row, i);

            switch (c)
            {
                case GREEN:
                    background = GREEN_BACKGROUND;
                    break;
                case YELLOW:
                    background = YELLOW_BACKGROUND;
                    break;
                case GREY:
                    background = GREY_BACKGROUND;
                    break;
                case NO_COLOR:
                    background = "";
                    break;
            }
            guessSlice.append(background +" "+l+" ").append(VERTICAL_EDGE);
        }

        return guessSlice.toString();
    }

    private static String separatorSlice(int wordLength)
    {
        //nota: effettuare il cambio in stringBuilder
        StringBuilder separatorSlice = new StringBuilder(L_SEPARATOR + "" + HORIZONTAL_EDGE_X3);

        for(int i = 1; i <= wordLength - 1; i++)
        {
            separatorSlice.append(CROSS + HORIZONTAL_EDGE_X3);
        }
        separatorSlice.append(R_SEPARATOR);

        return separatorSlice.toString();
    }

    private static String lowerPart(int wordLength)
    {
        //nota: effettuare il cambio in stringBuilder

        StringBuilder lowerPart = new StringBuilder(L_D_ANGLE + "" + HORIZONTAL_EDGE_X3);

        for(int i = 1; i <= wordLength - 1; i++)
        {
            lowerPart.append(D_SEPARATOR +  HORIZONTAL_EDGE_X3);
        }
        lowerPart.append(R_D_ANGLE);

        return lowerPart.toString();
    }

    public static void printStartGame()
    {
        System.out.println("Hai iniziato la partita.");

        printBoard(6, 5, null);
    }
    //static void printSecretWord()
    //static void printHelp()

    public static void printSetSecretWord()
    {
        System.out.println("Hai impostato la parola segreta");
    }

    public static void printDummy()
    {
        System.out.println("Hai inserito il comando Dummy.");
    }

    public static void printInvalid ()
    {
        System.out.println("Hai inserito un comando invalido.");
    }

    public static void printMissingArgs ()
    {
        printer.println("Argomento/i mancante/i");
    }
}