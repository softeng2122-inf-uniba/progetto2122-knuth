package it.uniba.app.wordle.domain;

/**
 * {@literal <<Control>>}<br>
 * Controllore che permette di realizzare le funzionalita' adibite al giocatore.
 *
 * <p>Col complemento di {@link WordsmithController}, fornisce
 * un'<i>Application Programming Interface</i> per gestire interamente
 * una sessione di gioco senza esporre le classi che costituiscono
 * il modello dei dati del gioco Wordle.</p>
 */
public interface PlayerController {

    /**
     * Controlla se nella sessione collegata a questo PlayerController
     * e' in corso una partita.
     *
     * @return true se e' in corso una partita, false altrimenti
     */
    boolean isGameRunning();

    /**
     * Inizia una nuova partita a Wordle.
     *
     * @throws WordleGameException se una partita e' gia' in corso
     * @throws WordleSettingException se la parola segreta non e' stata
     * impostata
     */
    void startGame();

    /**
     * Restituisce il numero totale di tentativi per la partita corrente.
     *
     * @throws WordleGameException se nessuna partita e' in corso
     * @return il numero totale di tentativi della partita
     */
    int getMaxGuesses();

    /**
     * Restituisce il numero di tentativi rimanenti per la partita corrente.
     *
     * @throws WordleGameException se nessuna partita e' in corso
     * @return il numero di tentativi rimanenti della partita
     */
    int getNumRemainingGuesses();

    /**
     * Restituisce la lunghezza della parola segreta.
     *
     * @throws WordleGameException se nessuna partita e' in corso
     * @return la lunghezza della parola segreta della partita
     */
    int getWordLength();

    /**
     * Effettua un tentativo e lo inserisce nella matrice dei tentativi
     * della partita corrente.
     *
     * <p> Per ogni lettera della parola inserita colora lo sfondo
     * (della casella corrispondente) di verde se la lettera e' nella
     * parola segreta e nel posto giusto, di giallo se la lettera e'
     * nella parola segreta ma nel posto sbagliato e di grigio se la
     * lettera non e' nella parola segreta. </p>
     *
     *
     * @param guessWord stringa contenente la parola inserita dal giocatore
     * @throws WordleGameException se nessuna partita e' in corso
     * o se e' stato gia' raggiunto il numero massimo di tentativi
     * @throws IllegalArgumentException se {@code guessWord}
     * non soddisfa la lunghezza prevista o contiene caratteri non alfabetici
     * @throws NullPointerException se {@code guessWord} e' null
     */
    void guess(String guessWord);

    /**
     * Restituisce il risultato dell'ultimo tentativo effettuato nella
     * partita corrente.
     *
     * @throws WordleGameException se nessuna partita e' in corso
     * @return true se il tentativo e' vincente, false se non e' vincente
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
     * alla riga {@code row} e' stato effettuato, ' ' (spazio) altrimenti
     * @throws WordleGameException se nessuna partita e' in corso
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
     * corrispondente alla riga {@code row} e' stato effettuato,
     * {@code NO_COLOR} altrimenti
     * @throws WordleGameException se nessuna partita e' in corso
     * @throws  IllegalArgumentException se {@code row} o {@code column}
     * eccedono le dimensioni della matrice dei tentativi
     * oppure sono numeri negativi
     */
    Color getColor(int row, int column);

    /**
     * Termina la partita corrente.
     *
     * <p> nota: la partita non e' automaticamente terminata nel momento
     * in cui viene indovinata la parola segreta o in cui terminano
     * i tentativi disponibili. Questo consente al programmatore di
     * effettuare ulteriori azioni (es. salvataggio dati partita)
     * prima di perdere definitivamente le informazioni associate
     * alla partita conclusa.</p>
     *
     * @throws WordleGameException se nessuna partita e' in corso
     */
    void endGame();

    /**
     * Restituisce la parola segreta della partita corrente.
     *
     * @throws WordleGameException se nessuna partita e' in corso
     * @return la parola segreta della partita corrente
     */
    String getGameSecretWord();
}
