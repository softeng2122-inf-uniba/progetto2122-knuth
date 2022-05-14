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
