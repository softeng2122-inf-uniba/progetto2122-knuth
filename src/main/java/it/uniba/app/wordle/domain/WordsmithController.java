package it.uniba.app.wordle.domain;

/**
 * {@literal <<Control>>}<br>
 * Controllore che permette di realizzare le funzionalita' adibite al paroliere.
 *
 * <p>Col complemento di {@link PlayerController}, fornisce
 * un'<i>Application Programming Interface</i> per gestire interamente
 * una sessione di gioco senza esporre le classi che costituiscono
 * il modello dei dati del gioco Wordle.</p>
 */
public interface WordsmithController {

    /**
     * Restituisce la parola segreta della sessione di gioco
     * su cui agisce questo WordsmithController.
     *
     * @return la parola segreta impostata
     * @throws WordleSettingException se la parola segreta non è stata
     * impostata
     */
    String getSecretWord();

    /**
     * Imposta la parola segreta per la sessione di gioco
     * su cui agisce il controller.
     *
     * @param newWord parola segreta da impostare
     * @throws NullPointerException se {@code newWord} è {@code null}
     * @throws IllegalArgumentException se {@code newWord} non soddisfa
     * la lunghezza prevista o contiene caratteri non alfabetici
     * @throws WordleGameException se una partita è gia' in corso
     */
    void setSecretWord(String newWord);
}
