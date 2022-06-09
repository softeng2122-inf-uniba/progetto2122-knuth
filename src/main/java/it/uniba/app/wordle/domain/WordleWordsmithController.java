package it.uniba.app.wordle.domain;

import java.util.Objects;

/**
 * {@literal <<Control>>}<br>
 * Implementazione di {@link WordsmithController}, gestisce
 * una sola sessione di gioco come paroliere (la quale dev'essere
 * stata creata da un {@link WordlePlayerController}).
 */
public final class WordleWordsmithController implements WordsmithController {

    /** Sessione di gioco su cui agire. */
    private final WordleSession session;

    /**
     * Crea un controller che permette di eseguire i compiti del paroliere.
     *
     * @param playerController controller del giocatore a cui dev'essere
     *                         collegato per poter agire sulla stessa
     *                         sessione di gioco
     */
    public WordleWordsmithController(
            final WordlePlayerController playerController) {

        session = playerController.getSession();
    }

    @Override
    public String getSecretWord() {
        if (!session.hasSecretWord()) {
            throw new WordleSettingException(
                    WordleSettingException.ABSENT_SECRET_WORD);
        }

        return session.getSecretWord();
    }

    @Override
    public void setSecretWord(final String newWord) {
        Objects.requireNonNull(newWord);

        if (session.isGameRunning()) {
            throw new WordleGameException(
                    WordleGameException.EXISTS_GAME);
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
