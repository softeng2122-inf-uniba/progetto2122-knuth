package it.uniba.app;

import java.util.List;

/** Questa classe si occupa delle stampe sul terminale
 *
 */
public class Printer
{

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


    public static void printBoard(int rows, int columns, int filledRows, List<String> words)
    {
        // aggiungere controlli sui parametri

        // stampa upperPart
        // for per stampare le varie righe (composte da guessSlice e separator slice)
        //stampa downPart
    }

    private static String upperPart(int wordLength)
    {
        return null;
    }

    private static String guessSlice(int wordLength, String word)
    {
        return null;
    }

    private static String separatorSlice(int wordLength)
    {
        return null;
    }

    private static String lowerPart(int wordLength)
    {
        return null;
    }



    //static void printSecretWord()
    //static void printHelp()
}