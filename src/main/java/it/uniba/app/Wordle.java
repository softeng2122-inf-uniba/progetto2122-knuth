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
    public static void setSecretWord(String newWord, int wordLength) throws Exception
    {
        if (newWord.length() < wordLength)
        {
            throw new Exception("Parola troppo corta");
        }
        if (newWord.length() > wordLength)
        {
            throw new Exception("Parola troppo lunga");
        }

        if (!newWord.matches("[a-zA-Z]+"))
        {
            throw new Exception("Parola contenente caratteri diversi da lettere");
        }

        secretWord = newWord;
    }

    //showSecretWord()

    public static boolean isGameRunning()
    {
        return currentGame != null;
    }
}
