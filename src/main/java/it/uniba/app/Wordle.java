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
    public void setSecretWord(String secretWord, int wordLength) throws Exception
    {
        // controllo sulla dimensione
        if (secretWord.length() < wordLength)
        {
            throw new Exception("Parola troppo corta");
        }
        // controllo sui caratteri
        if (!secretWord.matches("[a-zA-Z]+"))
        {
            throw new Exception("Parola contenente caratteri diversi da lettere");
        }

        this.secretWord = secretWord;
    }

    //showSecretWord()

    public static boolean isGameRunning()
    {
        return currentGame != null;
    }
}
