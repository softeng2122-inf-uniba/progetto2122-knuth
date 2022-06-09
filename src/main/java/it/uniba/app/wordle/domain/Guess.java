package it.uniba.app.wordle.domain;

import java.util.Objects;

/**
 * {@literal <<Entity>>} <br>
 * Classe che rappresenta un tentativo del giocatore.
 * Ogni lettera del tentativo viene memorizzata in un
 * oggetto di {@link LetterBox}.
 */
class Guess {
    /** Contiene in ordine le lettere colorate che costituiscono la parola. */
    private final LetterBox[] cellArray;

    /**
     * {@literal <<Entity>>} <br>
     * Classe che rappresenta una singola cella di {@link Board}
     * e incapsula lettera e colore.
     */
    private static class LetterBox {
        /** Lettera contenuta nella cella. */
        private final Character containedLetter;
        /** Colore della cella. */
        private Color boxColor;

        LetterBox(final char letter, final Color color) {
            Objects.requireNonNull(color);

            this.containedLetter = letter;
            this.boxColor = color;
        }

        Color getColor() {
            return boxColor;
        }

        void setColor(final Color color) {
            Objects.requireNonNull(color);

            this.boxColor = color;
        }

        Character getLetter() {
            return containedLetter;
        }
    }

    /**
     * Crea un tentativo contenente le lettere di {@code guessingWord}
     * e le colora con {@code Color.NO_COLOR}.
     *
     * @param guessingWord parola che costituisce il tentativo
     */
    Guess(final String guessingWord) {
        Objects.requireNonNull(guessingWord);

        cellArray = new LetterBox[guessingWord.length()];

        for (int i = 0; i < guessingWord.length(); i++) {
            cellArray[i] = new LetterBox(
                    guessingWord.charAt(i), Color.NO_COLOR);
        }
    }

    Color getColor(final int index) {

        return cellArray[index].getColor();
    }

    void setColor(final int index, final Color color) {
        Objects.requireNonNull(color);

        cellArray[index].setColor(color);
    }

    Character getLetter(final int index) {

        return cellArray[index].getLetter();
    }

    /**
     * Restituisce la stringa della parola che costituisce il tentativo.
     *
     * @return parola che costituisce il tentativo
     */
    String getWord() {
        StringBuilder s = new StringBuilder();

        for (LetterBox lb : cellArray) {
            s.append(lb.getLetter());
        }

        return s.toString();
    }
}

