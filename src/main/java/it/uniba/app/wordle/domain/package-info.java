/**
 * Package contenente le classi per giocare a Wordle, indipendenti
 * dall'interfaccia utente scelta.
 *
 *
 * <p>Il package realizza le componenti <i>Model</i> e <i>Presenter</i>
 * in riferimento allo stile architetturale <i>Model-View-Presenter</i>:
 * le classi che rappresentano le entita' di dominio costituiscono
 * il modello di dati, il quale non viene esposto ai clienti del package
 * ma è manipolato unicamente attraverso le classi di controllo.</p>
 *
 * <p>I clienti del package utilizzeranno i metodi forniti dalle interfacce
 * {@link it.uniba.app.wordle.domain.PlayerController}
 * e {@link it.uniba.app.wordle.domain.WordsmithController}, che definiscono
 * rispettivamente il controller per svolgere il ruolo di giocatore
 * e quello per il ruolo di paroliere.</p>
 *
 * <p>L'interazione con il gioco Wordle è organizzata in sessioni:
 * una sessione è una sequenza di partite giocate dallo stesso giocatore
 * e monitorate da un paroliere.</p>
 *
 * <p>In questa versione del package le uniche implementazioni presenti per
 * le interfacce di controllo sono
 * {@link it.uniba.app.wordle.domain.WordlePlayerController} e
 * {@link it.uniba.app.wordle.domain.WordleWordsmithController},
 * che realizzano controller capaci di agire su una sola
 * sessione.</p>
 *
 * @author Gruppo Knuth
 * @version 1.0
 */
package it.uniba.app.wordle.domain;
