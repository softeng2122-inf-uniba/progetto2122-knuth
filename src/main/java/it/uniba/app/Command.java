package it.uniba.app;

/**
 * {@literal <<NoECB>>} <br>
 * Il tipo enumerativo Command contiene le costanti che rappresentano i comandi riconosciuti dal gioco.
 * Le costanti INVALID e SPACE sono comandi fittizi per gestire eventuali errori di input e caratteri di spaziatura.
 */
public enum Command
{
    INVALID(),
    SPACE(),
    GIOCA(),
    NUOVA(1),
    ABBANDONA(),
    GUESS(1),
    ESCI(),
    MOSTRA(),
    HELP();

    private final int numArgs;

    Command() {
        this(0);
    }

    Command(int numArgs) {
        this.numArgs = numArgs;
    }

    public int getNumArgs() {
        return this.numArgs;
    }

    // public abstract void execute();
}
