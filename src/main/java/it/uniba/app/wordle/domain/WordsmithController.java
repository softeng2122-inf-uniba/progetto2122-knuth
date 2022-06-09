package it.uniba.app.wordle.domain;

public interface WordsmithController {

    /**
     * Restituisce la parola segreta della sessione di gioco
     * su cui agisce il controller.
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
     * @throws WordleGameException se una partita è già in corso
     */
    void setSecretWord(String newWord);
}
