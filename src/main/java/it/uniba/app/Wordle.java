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
    public static void guess(String guessWord) throws Exception
    {
        if (!isGameRunning())
        {
            throw new Exception("Partita inesistente");
        }

        wordCheck(guessWord);

        //Inizializzazione tentativo
        String secretWord = currentGame.getSecretWord();
        Guess newGuess = new Guess(guessWord);
        Map<Character, Integer> letterMap = new HashMap<>(); //Conterrà le coppie (lettera, numOccorrenze) della secret

        //Inserimento delle coppie nel dizionario
        for(char letter : secretWord.toCharArray())
        {
            if(!letterMap.containsKey(letter))
                letterMap.put(letter, 1);
            else
                letterMap.put(letter, letterMap.get(letter) + 1);
        }

        //Primo step: setting delle lettere verdi
        for(int i = 0; i < guessWord.length(); i++)
        {
            char l = guessWord.charAt(i);
            if(l == secretWord.charAt(i))
            {
                newGuess.getLetterBox(i).setColor(Color.GREEN);

                //decrementa dizionario
                letterMap.put(l, letterMap.get(l) - 1);
            }
        }

        //Secondo step: setting delle lettere gialle e grigie
        for(int i = 0; i < guessWord.length(); i++)
        {
            char l = guessWord.charAt(i);
            if(letterMap.containsKey(l) && letterMap.get(l) > 0) //test YELLOW
            {
                newGuess.getLetterBox(i).setColor(Color.YELLOW);
                letterMap.put(l, letterMap.get(l) - 1);
            }
            else
                newGuess.getLetterBox(i).setColor(Color.GREY);
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
        Board currentBoard = currentGame.getGameBoard();
        if (row < 0 || column < 0 || row >= currentBoard.getRowsNumber() || column >= currentBoard.getWordLength())
            throw new IllegalArgumentException();

        if (row >= currentBoard.getNumFilledRows())
            return ' ';
        else
            return currentBoard.getGuess(row).getLetterBox(column).getLetter();
    }

    public static Color getColor(int row, int column)
    {
        Board currentBoard = currentGame.getGameBoard();
        if (row < 0 || column < 0 || row >= currentBoard.getRowsNumber() || column >= currentBoard.getWordLength())
            throw new IllegalArgumentException();

        if (row >= currentBoard.getNumFilledRows())
            return Color.NO_COLOR;
        else
            return currentBoard.getGuess(row).getLetterBox(column).getColor();
    }


    //endGame()
    //setSecretWord()
    public static void setSecretWord(String newWord) throws Exception
    {
        wordCheck(newWord);

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

    private static void wordCheck(String word) throws Exception
    {
        if (word.length() < wordLength)
            throw new Exception("Parola troppo corta");

        if (word.length() > wordLength)
            throw new Exception("Parola troppo lunga");

        if (!word.matches("[a-zA-Z]+"))
            throw new Exception("Parola contenente caratteri diversi da lettere");
    }
}
