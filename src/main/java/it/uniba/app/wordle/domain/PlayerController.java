package it.uniba.app.wordle.domain;

public interface PlayerController {

    /**
     * Controlla se nella sessione collegata a this è in corso una partita.
     *
     * @return true se è in corso una partita, false altrimenti
     */
    boolean isGameRunning();

    /**
     * Inizia una nuova partita a Wordle.
     *
     * @throws WordleGameException se una partita è già in corso
     * @throws WordleSettingException se la parola segreta non è impostata
     */
    void startGame();

    /**
     * Restituisce il numero totale di tentativi per la partita corrente.
     *
     * @throws WordleGameException se nessuna partita è in corso
     * @return il numero totale di tentativi della partita
     */
    int getMaxGuesses();

    /**
     * Restituisce il numero di tentativi rimanenti per la partita corrente.
     *
     * @throws WordleGameException se nessuna partita è in corso
     * @return il numero di tentativi rimanenti della partita
     */
    int getNumRemainingGuesses();

    /**
     * Restituisce la lunghezza della parola segreta.
     *
     * @throws WordleGameException se nessuna partita è in corso
     * @return la lunghezza della parola segreta della partita
     */
    int getWordLength();

    /**
     * Effettua un tentativo e lo inserisce nella matrice dei tentativi
     * della partita corrente.
     *
     * <p> Per ogni lettera della parola inserita colora lo sfondo
     * (della casella corrispondente) di verde se la lettera è nella
     * parola segreta e nel posto giusto, di giallo se la lettera è
     * nella parola segreta ma nel posto sbagliato e di grigio se la
     * lettera non è nella parola segreta. </p>
     *
     *
     * @param guessWord stringa contenente la parola inserita dal giocatore
     * @throws WordleGameException se nessuna partita è in corso
     * o se è stato già raggiunto il numero massimo di tentativi
     * @throws IllegalArgumentException se {@code guessWord}
     * non soddisfa la lunghezza prevista o contiene caratteri non validi
     * @throws NullPointerException se {@code guessWord} è null
     */
    void guess(String guessWord);

    /**
     * Restituisce il risultato dell'ultimo tentativo effettuato.
     *
     * @throws WordleGameException se nessuna partita è in corso
     * @return true se il tentativo è vincente, false se non è vincente
     * oppure se non sono stati effettuati tentativi nella partita corrente
     */
    boolean getGuessResult();

    /**
     * Restituisce la lettera contenuta in una casella della matrice
     * dei tentativi della partita corrente.
     *
     * @param row   il valore zero indica la prima riga, il valore
     *              i-esimo indica la riga i+1, il valore massimo
     *              corrisponde al numero di righe della matrice
     *              dei tentativi - 1
     * @param column   il valore zero indica la prima colonna, il valore
     *                 i-esimo indica la colonna i+1, il valore massimo
     *                 corrisponde al numero di colonne della matrice
     *                 dei tentativi - 1
     * @return  la lettera nella casella alla riga {@code row}
     * e alla colonna {@code column} se il tentativo corrispondente
     * alla riga {@code row} è stato effettuato, ' ' altrimenti
     * @throws WordleGameException se nessuna partita è in corso
     * @throws  IllegalArgumentException se {@code row} o {@code column}
     * eccedono le dimensioni della matrice dei tentativi
     * oppure sono numeri negativi
     */
    char getLetter(int row, int column);

    /**
     * Restituisce il colore di una casella della matrice dei tentativi
     * della partita corrente.
     *
     * @param row   il valore zero indica la prima riga, il valore i-esimo
     *             indica la riga i+1, il valore massimo corrisponde al
     *             numero di righe della matrice dei tentativi - 1
     * @param column    il valore zero indica la prima colonna, il valore
     *                 i-esimo indica la colonna i+1, il valore massimo
     *                 corrisponde al numero di colonne della matrice
     *                 dei tentativi - 1
     * @return  il colore della casella alla riga {@code row}
     * e alla colonna {@code column} se il tentativo
     * corrispondente alla riga {@code row} è stato effettuato,
     * {@code NO_COLOR} altrimenti
     * @throws WordleGameException se nessuna partita è in corso
     * @throws  IllegalArgumentException se {@code row} o {@code column}
     * eccedono le dimensioni della matrice dei tentativi
     * oppure sono numeri negativi
     */
    Color getColor(int row, int column);

    /**
     * Termina la partita corrente.
     *
     * <p> nota: la partita non è automaticamente terminata nel momento
     * in cui viene indovinata la parola segreta o in cui terminano
     * i tentativi disponibili. Questo consente al programmatore di
     * effettuare ulteriori azioni (es. salvataggio dati partita)
     * prima di perdere definitivamente le informazioni associate
     * alla partita conclusa. </p>
     *
     * @throws WordleGameException se nessuna partita è in corso
     */
    void endGame();

    /**
     * Restituisce la parola segreta della partita corrente.
     *
     * @throws WordleGameException se nessuna partita è in corso
     * @return la parola segreta della partita corrente
     */
    String getGameSecretWord();
}
