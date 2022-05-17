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

    public static void startGame() throws WordleGameException, WordleSettingException
    {
        if (isGameRunning())
        {
            throw new WordleGameException("Partita in corso");
        }

        if (secretWord == null)
        {
            throw new WordleSettingException("Parola segreta non impostata");
        }

        currentGame = new WordleGame(secretWord);
    }

    public static void guess(String guessWord) throws WordleGameException, IllegalArgumentException
    {
        if (!isGameRunning())
            throw new WordleGameException("Partita inesistente");

        if (getNumRemainingGuesses() == 0)
            throw new WordleGameException("Numero massimo di tentativi raggiunto");

        try
        {
            guessWordCheck(guessWord);
        }
        catch (IllegalArgumentException e)
        {
            throw e;
        }
        //Inizializzazione tentativo
        guessWord = guessWord.toUpperCase();
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
        Guess.LetterBox lb;
        for(int i = 0; i < guessWord.length(); i++)
        {
            lb = newGuess.getLetterBox(i);
            if (lb.getColor() == Color.GREEN)
                continue;

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

    public static boolean getGuessResult()
    {
        Board currentBoard = currentGame.getGameBoard();
        int lastGuessIndex = currentBoard.getNumFilledRows()-1;
        String currentWord = currentBoard.getGuess(lastGuessIndex).getWord();

        return currentWord.equals(currentGame.getSecretWord());
    }

    // restituisci il numero di tentativi che rimangono
    public static int getNumRemainingGuesses()
    {
        return currentGame.getNumRemainingGuesses();
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

    public static void endGame() throws WordleGameException
    {
        if(!isGameRunning())
        {
            throw new WordleGameException("Nessuna partita in corso");
        }
        currentGame = null;
    }

    //setSecretWord()
    public static void setSecretWord(String newWord) throws IllegalArgumentException, WordleGameException
    {
        if (!newWord.matches("[a-zA-Z]+"))
        {
            throw new IllegalArgumentException("Parola segreta non valida");
        }

        if (newWord.length() < wordLength)
        {
            throw new IllegalArgumentException("Parola segreta troppo corta");
        }
        if (newWord.length() > wordLength)
        {
            throw new IllegalArgumentException("Parola segreta troppo lunga");
        }



        if (isGameRunning())
        {
            throw new WordleGameException("Partita in corso");
        }

        secretWord = newWord.toUpperCase();
    }

    //showSecretWord()

    public static boolean isGameRunning()
    {
        return currentGame != null;
    }

    private static void guessWordCheck(String word) throws IllegalArgumentException
    {
        if (!word.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Tentativo non valido");

        if (word.length() < wordLength)
            throw new IllegalArgumentException("Tentantivo incompleto");

        if (word.length() > wordLength)
            throw new IllegalArgumentException("Tentativo eccessivo");
    }

    public static String getSecretWord() throws WordleSettingException
    {
        if (secretWord == null)
        {
            throw new WordleSettingException("Parola segreta non impostata");
        }

        return secretWord;
    }

    // nota: ci sarà (forse) un'altra funzione getSecretWord separata
    public static String getGameSecretWord()
    {
        return currentGame.getSecretWord();
    }
}
