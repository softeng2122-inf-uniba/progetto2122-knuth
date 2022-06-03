package it.uniba.app.wordle.domain;

public interface WordsmithController {

    String getSecretWord();
    void setSecretWord(String newWord);
}
