package it.uniba.app;

/** Gestisce la logica del gioco
 *
 */
public class Wordle
{
    static String secretWord = null;
    static WordleGame currentGame = null;

    public static void startGame()
    {
        currentGame = new WordleGame("dummy");
    }
    //guess()
    //endGame()
    //setSecretWord()
    //showSecretWord()
}
