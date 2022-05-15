package it.uniba.app;

import java.util.Map;
import java.util.HashMap;

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
    //startGame()
    public static void guess(String guessWord)
    {
        // controllo sulla partita che deve esistere
        // controllo sulla parola
        // cioè controllo su lunghezza e validità caratteri

        String secretWord = currentGame.getSecretWord();
        Guess newGuess = new Guess(guessWord);

        // dizionario per contenere coppie (lettera, numOccorrenze) della secret
        Map<Character, Integer> letterMap = new HashMap<>();

        // metti nel dizionario le coppie
        for(char letter : secretWord.toCharArray())
        {
            if(!letterMap.containsKey(letter))
                letterMap.put(letter, 1);
            else
                letterMap.put(letter, letterMap.get(letter) + 1);
        }

        //primo ciclo: controlla se è verde
        for(int i = 0; i < guessWord.length(); i++)
        {
            char l = guessWord.charAt(i);
            if(l == secretWord.charAt(i))
            {
                newGuess.setColor(i, Color.GREEN);

                //decrementa dizionario
                letterMap.put(l, letterMap.get(l) - 1);
            }
        }

        // secondo ciclo: controlla se è giallo
        for(int i = 0; i < guessWord.length(); i++)
        {
            char l = guessWord.charAt(i);
            if(letterMap.containsKey(l) && letterMap.get(l) > 0) //test YELLOW
            {
                newGuess.setColor(i, Color.YELLOW);
                letterMap.put(l, letterMap.get(l) - 1);
            }
            else
                newGuess.setColor(i, Color.GREY);
        }

        // aggiungi il tentativo alla matrice dei tentativi
        Board gameBoard = currentGame.getGameBoard();
        gameBoard.acceptNewGuess(newGuess);
        // se hai indovinato dillo, se era l'ultimo tentativo possibile dillo
        // in questi casi chiudi la partita
    }

    public static int getGuessResult()
    {
        // ritorna 1 se il tentativo è vincente
        // ritorna 0 se il tentativo non è vincente
    }

    public static int getMaxGuesses()
    {
        return currentGame.getMaxGuesses();
    }

    public static int getWordLength()
    {
        return currentGame.getWordLength();
    }

    public static char getLetter(int row, int column)
    {
        return currentGame.getLetter(row, column);
    }

    public static Color getColor(int row, int column)
    {
        return currentGame.getColor(row, column);
    }


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

        secretWord = newWord.toUpperCase();
    }

    //showSecretWord()

    public static boolean isGameRunning()
    {
        return currentGame != null;
    }
}
