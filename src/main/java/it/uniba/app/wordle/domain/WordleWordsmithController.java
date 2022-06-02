package it.uniba.app.wordle.domain;

public class WordleWordsmithController implements WordsmithController {

    private final WordleSession session;

    public WordleWordsmithController(WordlePlayerController playerController) {
        session = playerController.getSession();
    }

    /**
     * Restituisce la parola segreta dalle impostazioni di gioco.<p></p>
     * Nota: questo metodo, molto simile a {link Wordle#getGameSecretWord()},
     * si differenzia da esso per l'origine del dato restituito
     * (la differenza risiede nel recuperare l'impostazione attuale sulla
     * parola segreta piuttosto che recuperare la parola segreta della
     * partita già iniziata).<br>
     * La scelta di tenere separati i due metodi è stata presa in quanto il
     * nostro team ritiene che favorisca la modificabilità del codice.
     * @throws WordleSettingException  se la parola segreta non è stata
     * impostata
     */
    public String getSecretWord() {
        if (!session.hasSecretWord()) {
            throw new WordleSettingException(
                    WordleSettingException.Motivation.ABSENT_SECRET_WORD);
        }
        return session.getSecretWord();
    }

    /**
     * Imposta la parola segreta.
     * @param newWord   parola segreta da impostare
     * @throws IllegalArgumentException se {@code newWord} non soddisfa
     * la lunghezza prevista o contiene caratteri non validi
     * @throws WordleGameException  se una partita è già in corso
     */
    public void setSecretWord(final String newWord) {

        if (session.isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.Motivation.EXISTS_GAME);
        }

        if (!newWord.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Parola segreta non valida");
        }

        if (newWord.length() < session.getWordLength()) {
            throw new IllegalArgumentException("Parola segreta troppo corta");
        }

        if (newWord.length() > session.getWordLength()) {
            throw new IllegalArgumentException("Parola segreta troppo lunga");
        }
        session.setSecretWord(newWord.toUpperCase());
    }
}
