package it.uniba.app;

/** Gestisce la logica del gioco
 *
 */
public class Wordle
{
    private static String secretWord = null;
    private static WordleGame currentGame = null;
    private static int nMaxGuesses = 6;
    private static int wordLength = 5;

    public static void startGame() throws Exception
    {
        if (isGameRunning())
        {
            throw new Exception("Partita in corso");
        }

        if (secretWord == null)
        {
            throw new Exception("Parola segreta non impostata");
        }

        currentGame = new WordleGame(secretWord);
    }
    //guess()
    //endGame()
    //setSecretWord()
    public static void setSecretWord(String newWord) throws Exception
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

        if (isGameRunning())
        {
            throw new Exception("Partita in corso");
        }

        secretWord = newWord;
    }

    //showSecretWord()

    public static boolean isGameRunning()
    {
        return currentGame != null;
    }
}
