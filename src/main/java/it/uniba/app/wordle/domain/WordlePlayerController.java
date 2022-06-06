package it.uniba.app.wordle.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class WordlePlayerController implements PlayerController {

    private final WordleSession session;

    public WordlePlayerController() {
        session = new WordleSession();
    }

    /**
     * Utilizzato per la comunicazione tra controller, non fa parte dell'API
     * ma serve al WordleWordsmithController
     *
     * @return la sessione creata da this
     */
    WordleSession getSession() {
        return session;
    }

    public boolean isGameRunning() {
        return session.isGameRunning();
    }

    public void startGame() {
        if (isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.EXISTS_GAME);
        }

        if (!session.hasSecretWord()) {
            throw new WordleSettingException(
                    WordleSettingException.Motivation.ABSENT_SECRET_WORD);
        }

        session.setCurrentGame(new WordleGame(session.getSecretWord(),
                session.getNMaxGuesses(),
                session.getWordLength()));
    }

    public int getMaxGuesses() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }

        return session.getCurrentGame().getMaxGuesses();
    }

    public int getNumRemainingGuesses() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }

        return session.getCurrentGame().getNumRemainingGuesses();
    }

    public int getWordLength() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }
        return session.getCurrentGame().getWordLength();
    }

    public void guess(final String guessWord) {
        Objects.requireNonNull(guessWord);

        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }

        if (getNumRemainingGuesses() == 0) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NO_GUESSES_LEFT);
        }
        guessWordCheck(guessWord);


        //Inizializzazione tentativo
        String guessAttempt = guessWord.toUpperCase();
        String secretWord = session.getCurrentGame().getSecretWord();
        Guess newGuess = new Guess(guessAttempt);
        //Conterrà le coppie (lettera, numOccorrenze) della secret
        Map<Character, Integer> letterMap = new HashMap<>();

        //Inserimento delle coppie nel dizionario
        for (char letter : secretWord.toCharArray()) {
            if (!letterMap.containsKey(letter)) {
                letterMap.put(letter, 1);
            } else {
                letterMap.put(letter, letterMap.get(letter) + 1);
            }
        }

        //Primo step: setting delle lettere verdi
        for (int i = 0; i < guessAttempt.length(); i++) {
            char l = guessAttempt.charAt(i);
            if (l == secretWord.charAt(i)) {
                newGuess.setColor(i, Color.GREEN);

                //decrementa dizionario
                letterMap.put(l, letterMap.get(l) - 1);
            }
        }

        for (int i = 0; i < guessAttempt.length(); i++) {
            if (newGuess.getColor(i) == Color.GREEN) {
                continue;
            }

            char l = guessAttempt.charAt(i);
            //test YELLOW
            if (letterMap.containsKey(l) && letterMap.get(l) > 0) {
                newGuess.setColor(i, Color.YELLOW);
                letterMap.put(l, letterMap.get(l) - 1);
            } else {
                newGuess.setColor(i, Color.GREY);
            }
        }

        // aggiungi il tentativo alla matrice dei tentativi
        Board gameBoard = session.getCurrentGame().getGameBoard();
        gameBoard.acceptNewGuess(newGuess);
    }

    public boolean getGuessResult() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }

        Board currentBoard = session.getCurrentGame().getGameBoard();
        int lastGuessIndex = currentBoard.getNumFilledRows() - 1;

        if (lastGuessIndex == -1) {
            return false;
        } else {
            String currentWord = currentBoard.getGuess(lastGuessIndex)
                    .getWord();
            return currentWord.equals(session.getCurrentGame().getSecretWord());
        }
    }

    public char getLetter(final int row, final int column) {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }

        Board currentBoard = session.getCurrentGame().getGameBoard();
        if (row < 0 || column < 0 || row >= currentBoard.getRowsNumber()
                || column >= currentBoard.getWordLength()) {
            throw new IllegalArgumentException();
        }

        if (row >= currentBoard.getNumFilledRows()) {
            return ' ';
        } else {
            return currentBoard.getGuess(row).getLetter(column);
        }
    }

    public Color getColor(final int row, final int column) {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }

        Board currentBoard = session.getCurrentGame().getGameBoard();
        if (row < 0 || column < 0 || row >= currentBoard.getRowsNumber()
                || column >= currentBoard.getWordLength()) {
            throw new IllegalArgumentException();
        }

        if (row >= currentBoard.getNumFilledRows()) {
            return Color.NO_COLOR;
        } else {
            return currentBoard.getGuess(row).getColor(column);
        }
    }

    public void endGame() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }
        session.setCurrentGame(null);
    }

    /**
     * Controllo sulla parola inserita, che dev'essere
     * della lunghezza appropriata e deve contenere solo
     * lettere (non altri tipi di caratteri)
     *
     * @param word parola da controllare
     * @throws IllegalArgumentException se una delle condizioni
     * specificate non è verificata
     */
    private void guessWordCheck(final String word) {

        if (!word.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Tentativo non valido");
        }

        if (word.length() < session.getWordLength()) {
            throw new IllegalArgumentException("Tentativo incompleto");
        }

        if (word.length() > session.getWordLength()) {
            throw new IllegalArgumentException("Tentativo eccessivo");
        }
    }

    public String getGameSecretWord() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }
        return session.getCurrentGame().getSecretWord();
    }

}
