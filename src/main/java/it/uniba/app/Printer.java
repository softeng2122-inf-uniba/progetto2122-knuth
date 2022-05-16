package it.uniba.app;

import java.io.PrintWriter;

/** Questa classe si occupa delle stampe sul terminale
 *
 */
public class Printer extends PrintWriter
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

    //backgrounds
    public static final String GREY_BACKGROUND = "\033[100m"; //GREY
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW

    // bold
    public static final String BOLD = "\u001B[1m";

    Printer()
    {
        super(System.out,true);
    }

    // reset background
    public static final String RESET = "\033[0m";  // Text Reset

    public void printBoard()
    {
        int rows = Wordle.getMaxGuesses();
        int columns = Wordle.getWordLength();

        //stampa parte superiore della Board
        println(upperPart(columns));

        // stampa le righe (dalla prima alla penultima)
        for(int i = 0; i < rows - 1; i++)
        {
            println(guessSlice(i));
            println(separatorSlice(columns));
        }

        // stampa ultima riga
        println(guessSlice(rows-1));
        println(lowerPart(columns));
    }

    private String upperPart(int wordLength)
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

    private String guessSlice(int row)
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
            guessSlice.append(background +" "+l+" "+RESET).append(VERTICAL_EDGE);
        }

        return guessSlice.toString();
    }

    private String separatorSlice(int wordLength)
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

    private String lowerPart(int wordLength)
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

    public void printStartGame()
    {
        System.out.println("Hai iniziato la partita.");
        printBoard();
    }
    //static void printSecretWord()
    //static void printHelp()

    public void printSetSecretWord()
    {
        println("Hai impostato la parola segreta");
    }

    public void printDummy()
    {
        println("Hai inserito il comando Dummy.");
    }

    public void printInvalid ()
    {
        println("Hai inserito un comando invalido.");
    }

    public void printMissingArgs ()
    {
        println("Argomento/i mancante/i");
    }

    public void printGuessResult()
    {
        int maxGuesses = Wordle.getMaxGuesses();
        int remainingGuesses = Wordle.getNumRemainingGuesses();
        if(Wordle.getGuessResult())
        {
            println("Parola segreta indovinata");
            println("Numero di tentativi: "+ (maxGuesses - remainingGuesses));
        }
        else
        {
            if(remainingGuesses == 0)
            {
                println("Hai raggiunto il numero massimo di tentativi");
                println("La parola segreta è: " + Wordle.getGameSecretWord());
            }
        }
    }

    public void printDescription()
    {

        printWordleLogo();

        println("Wordle è un videogioco in cui il giocatore deve indovinare una parola segreta");
        println("avendo a disposizione 6 tentativi che dovranno essere parole di una lunghezza");
        println("prestabilita (nel nostro caso 5 lettere).");
        println("Ad ogni tentativo, ogni lettera della parola inserita viene evidenziata in:");

        print(GREEN_BACKGROUND + "VERDE" + RESET);
        println(" se è contenuta nella parola segreta ed è nella giusta posizione");

        print(YELLOW_BACKGROUND + "GIALLO" + RESET);
        println(" se è contenuta nella parola segreta ma in una posizione diversa");

        print(GREY_BACKGROUND + "GRIGIO" + RESET);
        println(" se non è contenuta nella parola segreta");
        println();
    }

    private void printWordleLogo()
    {

        println(upperPart(6));
        print(VERTICAL_EDGE);
        print(coloredLetterSpace('W', Color.GREEN) + VERTICAL_EDGE);
        print(coloredLetterSpace('O', Color.GREY) + VERTICAL_EDGE);
        print(coloredLetterSpace('R', Color.YELLOW) + VERTICAL_EDGE);
        print(coloredLetterSpace('D', Color.GREEN) + VERTICAL_EDGE);
        print(coloredLetterSpace('L', Color.YELLOW) + VERTICAL_EDGE);
        println(coloredLetterSpace('E', Color.GREY) + VERTICAL_EDGE);
        println(lowerPart(6));
    }

    private String coloredLetterSpace(char c, Color color)
    {
        String background = "";
        switch (color)
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
        }
        return(background + " " + c + " " + RESET);
    }

    public void printHelp()
    {
        println("Il gioco accetta i seguenti comandi:");
        println(BOLD + "\t/gioca" + RESET + "\t\t\tinizia una nuova partita (la parola segreta deve essere impostata)");
        println(BOLD + "\t/nuova <parola>" + RESET + "\timposta <parola> come parola segreta");
        println(BOLD + "\t/mostra" + RESET + "\t\t\tvisualizza la parola segreta impostata");
        println(BOLD +  "\t/abbandona" + RESET + "\t\tabbandona la partita in corso");
        println(BOLD + "\t/esci" + RESET + "\t\t\tchiude il gioco");
        println(BOLD + "\t<parola>" + RESET + "\t\tper effettuare un tentativo\n");
    }

}