/**
 * Classi di test.
 *
 * <p>Per le classi <i>boundary</i> il testing si concentra su aspetti diversi:
 * la classe {@link it.uniba.app.wordle.UI.CLI.ParserTest} si occupa di
 * verificare la corretta elaborazione dell'input da parte del parser,
 * nonché la validità dell'output da esso restituito.
 * In questo modo vengono testate simultaneamente sia la classe
 * {@link it.uniba.app.wordle.UI.CLI.Parser} che la classe
 * {@link it.uniba.app.wordle.UI.CLI.ParserToken}, rendendo
 * non necessaria una classe di testing specifica per ParserToken.</p>
 *
 * <p>La classe {@link it.uniba.app.wordle.UI.CLI.AppTest} si concentra sul
 * verificare che le varie chiamate ai metodi (escluso
 * {@link it.uniba.app.wordle.UI.CLI.App#main(java.lang.String[])},
 * non testabile per via dell'istruzione{@code System.exit(0)},
 * che termina l'esecuzione della JVM)
 * non lancino mai eccezioni non gestite, che porterebbero a una chiusura
 * anomala del gioco.</p>
 *
 * <p>Non ci sembra proficuo controllare che tutte le stampe siano corrette,
 * in quanto ciò richiederebbe un eccessivo sforzo di programmazione non
 * ripagato e anche minimi cambiamenti in una qualsiasi stampa comporterebbero
 * la corrispondente modifica nei casi di test.
 * Inoltre le stampe possono essere facilmente controllate visivamente
 * per mezzo della semplice esecuzione manuale dell'applicazione, per cui
 * risulta più immediato accorgersi di eventuali errori e ripararli.</p>
 */
package it.uniba.app.wordle.UI.CLI;
