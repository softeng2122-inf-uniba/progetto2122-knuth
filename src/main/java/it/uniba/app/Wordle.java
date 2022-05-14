package it.uniba.app;

/** Gestisce la logica del gioco
 *
 */
public class Wordle
{
    private static String secretWord = null;
    private static WordleGame currentGame = null;

    public static void startGame() throws Exception
    {
        if (isGameRunning())
        {
            throw new Exception("Partita in corso.");
        }

        currentGame = new WordleGame("dummy");
    }
    //guess()
    //startGame()
    static void guess(String guessWord)
    {
        // controllo sulla partita che deve esistere
        // controllo sulla parola
        // cioè controllo su lunghezza e validità caratteri

        // modifica della matrice dei tentativi

        // se hai indovinato dillo, se era l'ultimo tentativo possibile dillo
        // in questi casi chiudi la partita
    }

    static int getGuessResult()
    {
        // ritorna 1 se il tentativo è vincente
        // ritorna 0 se il tentativo non è vincente
    }


    //endGame()
    //setSecretWord()
    //showSecretWord()

    public static boolean isGameRunning()
    {
        return currentGame != null;
    }
}
