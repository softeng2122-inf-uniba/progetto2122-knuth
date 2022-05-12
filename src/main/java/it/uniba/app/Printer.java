package it.uniba.app;

import java.util.List;
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

        if (words.length > rows)
        {
            throw new IllegalArgumentException("Il numero di elementi dell'array supera il numero di righe");
        }

        for (String word : words)
        {
            if (word.length() != columns)
            {
                throw new IllegalArgumentException("Parola di dimensioni non valide");
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

        String upperPart = L_U_ANGLE + "" + HORIZONTAL_EDGE_X3;

        for(int i = 1; i <= wordLength - 1; i++)
        {
            upperPart = upperPart + U_SEPARATOR + HORIZONTAL_EDGE_X3;
        }
        upperPart = upperPart + R_U_ANGLE;

        return upperPart;
    }

    private static String guessSlice(int wordLength, String word)
    {
        // nota: non effettuare controllo su parametri, già fatto dal metodo printBoard
        char[] chars = word.toCharArray();
        String guessSlice = VERTICAL_EDGE + "";

        for(int i = 0; i < wordLength; i++)
        {
            guessSlice = guessSlice + " " + chars[i] + " " + VERTICAL_EDGE;
        }

        return guessSlice;
    }

    private static String separatorSlice(int wordLength)
    {
        //nota: effettuare il cambio in stringBuilder
        String separatorSlice = L_SEPARATOR + "" + HORIZONTAL_EDGE_X3;

        for(int i = 1; i <= wordLength - 1; i++)
        {
            separatorSlice = separatorSlice + CROSS + HORIZONTAL_EDGE_X3;
        }
        separatorSlice = separatorSlice + R_SEPARATOR;

        return separatorSlice;
    }

    private static String lowerPart(int wordLength)
    {
        //nota: effettuare il cambio in stringBuilder

        String lowerPart = L_D_ANGLE + "" + HORIZONTAL_EDGE_X3;

        for(int i = 1; i <= wordLength - 1; i++)
        {
            lowerPart = lowerPart +  D_SEPARATOR +  HORIZONTAL_EDGE_X3;
        }
        lowerPart = lowerPart + R_D_ANGLE;

        return lowerPart;
    }


    public static String wordToPrint(String[] words, int wordNumber, int wordLength)
    {
        int length = words.length;
        if (wordNumber < length)
        {
            return words[wordNumber];
        }
        else
        {
            String hollow = "";
            for (int i = 0; i < wordLength; i++)
            {
                hollow = hollow + " ";
            }
            return hollow;
        }
    }


    //static void printSecretWord()
    //static void printHelp()
}