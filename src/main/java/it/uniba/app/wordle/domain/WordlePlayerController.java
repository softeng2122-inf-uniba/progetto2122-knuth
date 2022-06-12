package it.uniba.app.wordle.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@literal <<Control>>}<br>
 * Implementazione di {@link PlayerController}, crea e gestisce
 * una sola sessione di gioco come giocatore.
 */
public final class WordlePlayerController implements PlayerController {

    /** Sessione di gioco su cui agire. */
    private final WordleSession session;

    /**
     * Crea un controller che permette di eseguire i compiti del giocatore.
     *
     * Viene creata una sessione di gioco associata al controller, la quale
     * dovra' essere utilizzata anche da un {@link WordleWordsmithController}
     * per permettere al paroliere e al giocatore di agire sulla stessa
     * sessione di gioco.
     *
     * @see WordleWordsmithController#WordleWordsmithController(
     *                                  WordlePlayerController)
     */
    public WordlePlayerController() {
        session = new WordleSession();
    }

    /**
     * Restituisce la sessione di gioco, permettendo a un
     * {@link WordleWordsmithController} di agire sulla stessa.
     *
     * @return la sessione associata a quest'oggetto
     */
    WordleSession getSession() {
        return session;
    }

    @Override
    public boolean isGameRunning() {
        return session.isGameRunning();
    }

    @Override
    public void startGame() {
        if (isGameRunning()) {
            throw new WordleGameException(
                      WordleGameException.EXISTS_GAME);
        }

        if (!session.hasSecretWord()) {
            throw new WordleSettingException(
                      WordleSettingException.ABSENT_SECRET_WORD);
        }

        session.setCurrentGame(new WordleGame(session.getSecretWord(),
                                              session.getNMaxGuesses(),
                                              session.getWordLength()));
    }

    @Override
    public int getMaxGuesses() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.NOT_EXISTS_GAME);
        }

        return session.getCurrentGame().getMaxGuesses();
    }

    @Override
    public int getNumRemainingGuesses() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.NOT_EXISTS_GAME);
        }

        return session.getCurrentGame().getNumRemainingGuesses();
    }

    @Override
    public int getWordLength() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.NOT_EXISTS_GAME);
        }
        return session.getCurrentGame().getWordLength();
    }

    @Override
    public void guess(final String guessWord) {
        Objects.requireNonNull(guessWord);

        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.NOT_EXISTS_GAME);
        }

        if (getNumRemainingGuesses() == 0) {
            throw new WordleGameException(
                    WordleGameException.NO_GUESSES_LEFT);
        }


        //Inizializzazione tentativo
        String guessAttempt = guessWord.toUpperCase();
        String secretWord = session.getCurrentGame().getSecretWord();
        Guess newGuess = new Guess(guessAttempt);
        //Conterra' le coppie (lettera, numOccorrenze) della secret
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

    @Override
    public boolean getGuessResult() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.NOT_EXISTS_GAME);
        }

        Board currentBoard = session.getCurrentGame().getGameBoard();
        int lastGuessIndex = currentBoard.getNumFilledRows() - 1;

        if (lastGuessIndex == -1) {
            return false;
        } else {
            String currentWord = currentBoard.getGuess(lastGuessIndex)
                    .getWord();
            return currentWord.equals(session.getCurrentGame()
                                      .getSecretWord());
        }
    }

    @Override
    public char getLetter(final int row, final int column) {
        if (!isGameRunning()) {
            throw new WordleGameException(
                      WordleGameException.NOT_EXISTS_GAME);
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

    @Override
    public Color getColor(final int row, final int column) {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.NOT_EXISTS_GAME);
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

    @Override
    public void endGame() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.NOT_EXISTS_GAME);
        }
        session.setCurrentGame(null);
    }

    /**
     * Controllo sulla parola inserita, che dev'essere
     * della lunghezza appropriata e deve contenere solo
     * caratteri alfabetici.
     *
     * @param word parola da controllare
     * @throws IllegalArgumentException se una delle condizioni
     * specificate non e' verificata
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

    @Override
    public String getGameSecretWord() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.NOT_EXISTS_GAME);
        }
        return session.getCurrentGame().getSecretWord();
    }
}
