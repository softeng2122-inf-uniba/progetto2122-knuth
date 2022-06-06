package it.uniba.app.wordle.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class WordlePlayerController implements PlayerController {

    private final WordleSession session;

    public WordlePlayerController() {
        session = new WordleSession();
    }

    WordleSession getSession() {
        return session;
    }

    /**
     * Inizia una nuova partita a Wordle.
     *
     * @throws WordleGameException  se una partita è già in corso
     * @throws WordleSettingException se la parola segreta non è impostata
     */
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

    /**
     * Effettua un tentativo e lo inserisce nella matrice dei tentativi
     * della partita corrente.<br>
     * Per ogni lettera della parola inserita colora lo sfondo
     * (della casella corrispondente) di verde se la lettera è nella
     * parola segreta e nel posto giusto, di giallo se la lettera è
     * nella parola segreta ma nel posto sbagliato e di grigio se la
     * lettera non è nella parola segreta.
     *
     * @param guessWord stringa contenente la parola inserita dal giocatore
     * @throws WordleGameException  se nessuna partita è in corso
     * o se è stato già raggiunto il numero massimo di tentativi
     * @throws IllegalArgumentException se {@code guessWord}
     * non soddisfa la lunghezza prevista o contiene caratteri non validi
     */
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

    /**
     * Restituisce il risultato dell'ultimo tentativo effettuato.
     * @throws WordleGameException  se nessuna partita è in corso
     * @return true se il tentativo è vincente, false se non è vincente
     * oppure se non sono stati effettuati tentativi nella partita corrente
     */
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

    /**
     * Restituisce il numero di tentativi rimanenti per la partita corrente.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    public int getNumRemainingGuesses() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }

        return session.getCurrentGame().getNumRemainingGuesses();
    }

    /**
     * Restituisce il numero totale di tentativi per la partita corrente.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    public int getMaxGuesses() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }

        return session.getCurrentGame().getMaxGuesses();
    }

    /**
     * Restituisce la lunghezza della parola segreta.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    public int getWordLength() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }
        return session.getCurrentGame().getWordLength();
    }

    /**
     * Restituisce la lettera contenuta in una casella della matrice
     * dei tentativi della partita corrente.
     * @param row   il valore 0 indica la prima riga, il valore
     *              i-esimo indica la riga i+1, il valore massimo
     *              corrisponde al numero di righe della matrice
     *              dei tentativi - 1
     * @param column    il valore 0 indica la prima colonna, il valore
     *                 i-esimo indica la colonna i+1, il valore massimo
     *                 corrisponde al numero di colonne della matrice
     *                 dei tentativi - 1
     * @return  la lettera nella casella alla riga {@code row}
     * e alla colonna {@code column} se il tentativo corrispondente
     * alla riga {@code row} è stato effettuato, ' ' altrimenti
     * @throws WordleGameException   se nessuna partita è in corso
     * @throws  IllegalArgumentException se {@code row} o {@code column}
     * eccedono le dimensioni della matrice dei tentativi
     * oppure sono numeri negativi
     */
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

    /**
     * Restituisce il colore di una casella della matrice dei tentativi
     * della partita corrente.
     * @param row   il valore 0 indica la prima riga, il valore i-esimo
     *             indica la riga i+1, il valore massimo corrisponde al
     *             numero di righe della matrice dei tentativi - 1
     * @param column    il valore 0 indica la prima colonna, il valore
     *                 i-esimo indica la colonna i+1, il valore massimo
     *                 corrisponde al numero di colonne della matrice
     *                 dei tentativi - 1
     * @return  il colore della casella alla riga {@code row}
     * e alla colonna {@code column} se il tentativo
     * corrispondente alla riga {@code row} è stato effettuato,
     * {@code NO_COLOR} altrimenti
     * @throws WordleGameException   se nessuna partita è in corso
     * @throws  IllegalArgumentException se {@code row} o {@code column}
     * eccedono le dimensioni della matrice dei tentativi
     * oppure sono numeri negativi
     */
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

    /**
     * Termina la partita in corso.
     * @throws WordleGameException se nessuna partita è in corso
     */
    public void endGame() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }
        session.setCurrentGame(null);
    }

    /**
     * @return true se è in corso una partita, false altrimenti
     */
    public boolean isGameRunning() {
        return session.isGameRunning();
    }

    private void guessWordCheck(final String word) {

        if (!word.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Tentativo non valido");
        }

        if (word.length() < session.getWordLength()) {
            throw new IllegalArgumentException("Tentantivo incompleto");
        }

        if (word.length() > session.getWordLength()) {
            throw new IllegalArgumentException("Tentativo eccessivo");
        }
    }

    /**
     * Restituisce la parola segreta per la partita corrente.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    public String getGameSecretWord() {
        if (!isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.NOT_EXISTS_GAME);
        }
        return session.getCurrentGame().getSecretWord();
    }

}
