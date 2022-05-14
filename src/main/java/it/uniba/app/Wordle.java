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
    //endGame()
    //setSecretWord()
    //showSecretWord()

    public static boolean isGameRunning()
    {
        return currentGame != null;
    }
}
