package it.uniba.app.wordle.domain;

import java.util.Objects;

/**
 * {@literal <<Entity>>} <br>
 * Classe che rappresenta un tentativo del giocatore.
 * Ogni lettera del tentativo viene memorizzata in un
 * oggetto di {@link LetterBox}.
 */
class Guess {
    private final LetterBox[] cellArray;

    /**
     * {@literal <<Entity>>} <br>
     * Classe che rappresenta una singola cella di {@link Board}
     * e incapsula lettera e colore.
     */
    private static class LetterBox {
        private final Character letter;
        private Color color;

        LetterBox(final char letter, final Color color) {
            Objects.requireNonNull(color);

            this.letter = letter;
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(final Color color) {
            Objects.requireNonNull(color);

            this.color = color;
        }

        public Character getLetter() {
            return letter;
        }
    }

    //costruttore di Guess con argomento il tentativo effettuato
    Guess(final String guessingWord) {
        Objects.requireNonNull(guessingWord);

        cellArray = new LetterBox[guessingWord.length()];

        for (int i = 0; i < guessingWord.length(); i++) {
            cellArray[i] = new LetterBox(
                    guessingWord.charAt(i), Color.NO_COLOR);
        }
    }

    public Color getColor(final int index) {

        return cellArray[index].getColor();
    }

    public void setColor(final int index, final Color color) {
        Objects.requireNonNull(color);

        cellArray[index].setColor(color);
    }

    public Character getLetter(final int index) {
        return cellArray[index].letter;
    }

    public String getWord() {
        StringBuilder s = new StringBuilder("");

        for (LetterBox lb : cellArray) {
            s.append(lb.getLetter());
        }

        return s.toString();
    }
}

