package it.uniba.app.wordle.domain;

public interface WordsmithController {

    /**
     * Restituisce la parola segreta dalle impostazioni di gioco.<p></p>
     * Nota: questo metodo, molto simile a {link Wordle#getGameSecretWord()},
     * si differenzia da esso per l'origine del dato restituito
     * (la differenza risiede nel recuperare l'impostazione attuale sulla
     * parola segreta piuttosto che recuperare la parola segreta della
     * partita già iniziata).<br>
     * La scelta di tenere separati i due metodi è stata presa in quanto il
     * nostro team ritiene che favorisca la modificabilità del codice.
     * @throws WordleSettingException se la parola segreta non è stata
     * impostata
     */
    String getSecretWord();

    /**
     * Imposta la parola segreta.
     * @param newWord   parola segreta da impostare
     * @throws IllegalArgumentException se {@code newWord} non soddisfa
     * la lunghezza prevista o contiene caratteri non validi
     * @throws WordleGameException se una partita è già in corso
     */
    void setSecretWord(String newWord);
}
