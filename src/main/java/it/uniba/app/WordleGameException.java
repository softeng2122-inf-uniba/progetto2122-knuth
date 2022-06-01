package it.uniba.app;

/**
 * {@literal <<NoECB>>} <br>
 *  Eccezione lanciata in situazioni che richiedono come precondizione l'esistenza o la non esistenza della partita,
 *  nelle quali essa non è rispettata.
 *
 *  Esempio: Precondizione per effettuare un tentativo è l'aver iniziato una partita.
 */
public class WordleGameException extends RuntimeException
{
    private final Motivation motivation;
    public enum Motivation {
        EXISTS_GAME() {
            public String getMessage() {
                return "Partita in corso";
            }
        },
        NOT_EXISTS_GAME() {
            public String getMessage() {
                return "Partita inesistente";
            }
        },
        NO_GUESSES_LEFT() {
            public String getMessage() {
                return "Massimo numero di tentativi raggiunto";
            }
        };

        public abstract String getMessage();

    }

    WordleGameException(String message)
    {
        super(message);
        this.motivation = null;
    }

    WordleGameException(Motivation motivation) {
        super(motivation.getMessage());
        this.motivation = motivation;
    }
}
