package it.uniba.app;


import java.util.Map;
import java.util.HashMap;

/**
 * {@literal <<Control>>} <br>
 * Classe che implementa la logica del gioco: opera su oggetti delle
 * classi di tipo Entity, coerentemente con le regole di Wordle.
 * <p></p>
 * Fornisce una API (Application Programming Interface) indipendente
 * dallo strato di User Interface, il quale richiamerà i suoi servizi,
 * effettuando controlli sulla legalità delle chiamate.
 */
public class Wordle {
    private static String secretWord = null;
    private static WordleGame currentGame = null;
    private static int nMaxGuesses = 6;
    private static int wordLength = 5;

    /**
     * Inizia una nuova partita a Wordle.
     *
     * @throws WordleGameException  se una partita è già in corso
     * @throws WordleSettingException se la parola segreta non è impostata
     */
    public static void startGame() throws WordleGameException,
            WordleSettingException {
        if (isGameRunning()) {
            throw new WordleGameException("Partita in corso");
        }

        if (secretWord == null) {
            throw new WordleSettingException("Parola segreta non impostata");
        }

        currentGame = new WordleGame(secretWord, wordLength, nMaxGuesses);
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
    public static void guess(String guessWord) throws WordleGameException,
            IllegalArgumentException {
        if (!isGameRunning()) {
            throw new WordleGameException("Partita inesistente");
        }

        if (getNumRemainingGuesses() == 0) {
            throw new WordleGameException(
                    "Numero massimo di tentativi raggiunto");
        }

        try {
            guessWordCheck(guessWord);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        //Inizializzazione tentativo
        guessWord = guessWord.toUpperCase();
        String secretWord = currentGame.getSecretWord();
        Guess newGuess = new Guess(guessWord);
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
        for (int i = 0; i < guessWord.length(); i++) {
            char l = guessWord.charAt(i);
            if (l == secretWord.charAt(i)) {
                newGuess.setColor(i, Color.GREEN);

                //decrementa dizionario
                letterMap.put(l, letterMap.get(l) - 1);
            }
        }

        //Secondo step: setting delle lettere gialle e grigie
        Guess.LetterBox lb;

        for (int i = 0; i < guessWord.length(); i++) {
            if (newGuess.getColor(i) == Color.GREEN) {
                continue;
            }

            char l = guessWord.charAt(i);
            //test YELLOW
            if (letterMap.containsKey(l) && letterMap.get(l) > 0) {
                newGuess.setColor(i, Color.YELLOW);
                letterMap.put(l, letterMap.get(l) - 1);
            } else {
                newGuess.setColor(i, Color.GREY);
            }
        }

        // aggiungi il tentativo alla matrice dei tentativi
        Board gameBoard = currentGame.getGameBoard();
        gameBoard.acceptNewGuess(newGuess);
    }

    /**
     * Restituisce il risultato dell'ultimo tentativo effettuato.
     * @throws WordleGameException  se nessuna partita è in corso
     * @return true se il tentativo è vincente, false se non è vincente
     * oppure se non sono stati effettuati tentativi nella partita corrente
     */
    public static boolean getGuessResult() {
        if (!isGameRunning()) {
            throw new WordleGameException("Partita inesistente");
        }

        Board currentBoard = currentGame.getGameBoard();
        int lastGuessIndex = currentBoard.getNumFilledRows() - 1;

        if (lastGuessIndex == -1) {
            return false;
        } else {
            String currentWord = currentBoard.getGuess(lastGuessIndex)
                    .getWord();
            return currentWord.equals(currentGame.getSecretWord());
        }
    }

    /**
     * Restituisce il numero di tentativi rimanenti per la partita corrente.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    public static int getNumRemainingGuesses() {
        if (!isGameRunning()) {
            throw new WordleGameException("Partita inesistente");
        }

        return currentGame.getNumRemainingGuesses();
    }

    /**
     * Restituisce il numero totale di tentativi per la partita corrente.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    public static int getMaxGuesses() {
        if (!isGameRunning()) {
            throw new WordleGameException("Partita inesistente");
        }

        return currentGame.getMaxGuesses();
    }

    /**
     * Restituisce la lunghezza della parola segreta.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    public static int getWordLength() {
        if (!isGameRunning()) {
            throw new WordleGameException("Partita inesistente");
        }

        return currentGame.getWordLength();
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
    public static char getLetter(int row, int column) {
        if (!isGameRunning()) {
            throw new WordleGameException("Partita inesistente");
        }

        Board currentBoard = currentGame.getGameBoard();
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
    public static Color getColor(int row, int column) {
        if (!isGameRunning()) {
            throw new WordleGameException("Partita inesistente");
        }

        Board currentBoard = currentGame.getGameBoard();
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
    public static void endGame() throws WordleGameException {
        if (!isGameRunning()) {
            throw new WordleGameException("Nessuna partita in corso");
        }

        currentGame = null;
    }

    /**
     * Imposta la parola segreta.
     * @param newWord   parola segreta da impostare
     * @throws IllegalArgumentException se {@code newWord} non soddisfa
     * la lunghezza prevista o contiene caratteri non validi
     * @throws WordleGameException  se una partita è già in corso
     */
    public static void setSecretWord(String newWord) throws
            IllegalArgumentException, WordleGameException {
        if (!newWord.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Parola segreta non valida");
        }

        if (newWord.length() < wordLength) {
            throw new IllegalArgumentException("Parola segreta troppo corta");
        }

        if (newWord.length() > wordLength) {
            throw new IllegalArgumentException("Parola segreta troppo lunga");
        }

        if (isGameRunning()) {
            throw new WordleGameException("Partita in corso");
        }

        secretWord = newWord.toUpperCase();
    }

    /**
     * @return true se è in corso una partita, false altrimenti
     */
    public static boolean isGameRunning() {
        return currentGame != null;
    }

    private static void guessWordCheck(String word) throws
            IllegalArgumentException {
        if (!word.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Tentativo non valido");
        }

        if (word.length() < wordLength) {
            throw new IllegalArgumentException("Tentantivo incompleto");
        }

        if (word.length() > wordLength) {
            throw new IllegalArgumentException("Tentativo eccessivo");
        }
    }

    /**
     * Restituisce la parola segreta dalle impostazioni di gioco.<p></p>
     * Nota: questo metodo, molto simile a {@link Wordle#getGameSecretWord()},
     * si differenzia da esso per l'origine del dato restituito
     * (la differenza risiede nel recuperare l'impostazione attuale sulla
     * parola segreta piuttosto che recuperare la parola segreta della
     * partita già iniziata).<br>
     * La scelta di tenere separati i due metodi è stata presa in quanto il
     * nostro team ritiene che favorisca la modificabilità del codice.
     * @throws WordleSettingException  se la parola segreta non è stata
     * impostata
     */
    public static String getSecretWord() throws WordleSettingException {
        if (secretWord == null) {
            throw new WordleSettingException("Parola segreta non impostata");
        }

        return secretWord;
    }

    /**
     * Restituisce la parola segreta per la partita corrente.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    public static String getGameSecretWord() throws WordleGameException {
        if (!isGameRunning()) {
            throw new WordleGameException("Partita inesistente");
        }

        return currentGame.getSecretWord();
    }
}
