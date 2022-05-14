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


    public static void printBoard(int rows, int columns, String[] words)
    {
        if (rows <= 0)
        {
            throw new IllegalArgumentException(Integer.toString(rows));
        }

        if (columns <= 0)
        {
            throw new IllegalArgumentException(Integer.toString(columns));
        }

        if (words != null && words.length > rows)
        {
            throw new IllegalArgumentException("Il numero di elementi dell'array supera il numero di righe");
        }

        if (words != null)
        {
            for (String word : words)
            {
                if (word.length() != columns)
                {
                    throw new IllegalArgumentException("Parola di dimensioni non valide");
                }

            }
        }

        //stampa parte superiore della Board
        printer.println(upperPart(columns));

        // stampa le righe (dalla prima alla penultima)
        for(int i = 0; i < rows - 1; i++)
        {
            String rowContent = wordToPrint(words, i, columns); // creare metodo per estrapolare parola da stampare
            printer.println(guessSlice(columns, rowContent));
            printer.println(separatorSlice(columns));
        }

        // stampa ultima riga
        String rowContent = wordToPrint(words, rows-1, columns);
        printer.println(guessSlice(columns, rowContent));
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

    private static String guessSlice(int wordLength, String word)
    {
        // nota: non effettuare controllo su parametri, già fatto dal metodo printBoard
        char[] chars = word.toCharArray();
        StringBuilder guessSlice = new StringBuilder(VERTICAL_EDGE + "");

        for(int i = 0; i < wordLength; i++)
        {
            guessSlice.append(" ").append(chars[i]).append(" ").append(VERTICAL_EDGE);
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


    public static String wordToPrint(String[] words, int wordNumber, int wordLength)
    {
        int length;
        if (words == null)
        {
            length = 0;
        }
        else
        {
            length = words.length;
        }

        if (wordNumber < length)
        {
            return words[wordNumber];
        }
        else
        {
            StringBuilder hollow = new StringBuilder();
            for (int i = 0; i < wordLength; i++)
            {
                hollow.append(" ");
            }
            return hollow.toString();
        }
    }

    public static void printStartGame()
    {
        System.out.println("Hai iniziato la partita.");

        printBoard(6, 5, null);
    }
    //static void printSecretWord()
    //static void printHelp()

    public static void printDummy()
    {
        System.out.println("Hai inserito il comando Dummy.");
    }

    public static void printInvalid ()
    {
        System.out.println("Hai inserito un comando invalido.");
    }
}