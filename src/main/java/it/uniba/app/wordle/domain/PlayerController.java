package it.uniba.app.wordle.domain;

public interface PlayerController {

    /**
     * Inizia una nuova partita a Wordle.
     *
     * @throws WordleGameException  se una partita è già in corso
     * @throws WordleSettingException se la parola segreta non è impostata
     */
    void startGame();

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
    void guess(String guessWord);

    /**
     * Restituisce il risultato dell'ultimo tentativo effettuato.
     * @throws WordleGameException  se nessuna partita è in corso
     * @return true se il tentativo è vincente, false se non è vincente
     * oppure se non sono stati effettuati tentativi nella partita corrente
     */
    boolean getGuessResult();

    /**
     * Restituisce il numero di tentativi rimanenti per la partita corrente.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    int getNumRemainingGuesses();

    /**
     * Restituisce il numero totale di tentativi per la partita corrente.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    int getMaxGuesses();

    /**
     * Restituisce la lunghezza della parola segreta.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    int getWordLength();

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
    char getLetter(int row, int column);

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
    Color getColor(int row, int column);

    /**
     * Termina la partita in corso.
     * @throws WordleGameException se nessuna partita è in corso
     */
    void endGame();

    /**
     * @return true se è in corso una partita, false altrimenti
     */
    boolean isGameRunning();

    /**
     * Restituisce la parola segreta per la partita corrente.
     * @throws WordleGameException  se nessuna partita è in corso
     */
    String getGameSecretWord();
}
