package it.uniba.app.wordle.domain;

import java.util.Objects;

public final class WordleWordsmithController implements WordsmithController {

    private final WordleSession session;

    public WordleWordsmithController(
            final WordlePlayerController playerController) {

        session = playerController.getSession();
    }

    public String getSecretWord() {
        if (!session.hasSecretWord()) {
            throw new WordleSettingException(
                    WordleSettingException.Motivation.ABSENT_SECRET_WORD);
        }
        return session.getSecretWord();
    }

    public void setSecretWord(final String newWord) {
        Objects.requireNonNull(newWord);

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
