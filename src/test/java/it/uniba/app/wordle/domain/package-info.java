/**
 * Classi di test.
 *
 * <p>Per le classi <i>entity</i> il testing si concentra sul garantire che i
 * metodi funzionino per i casi di utilizzo e non concentrano eccessivi
 * controlli su casi non validi. Questo perché tali classi non sono pubbliche
 * e quindi l'accesso e la modifica degli oggetti vengono monitorati
 * interamente dalle classi control, che si occuperanno di filtrare chiamate
 * non valide con opportuni lanci di eccezioni.</p>
 *
 * <p>Per le classi <i>control</i> è stata posta la massima attenzione nel
 * controllare che ogni chiamata possibile ai metodi sia correttamente
 * controllata e che venga sempre mantenuto uno stato di consistenza:
 * ad esempio, si è verificato che è possibile terminare una partita
 * quando essa è effettivamente presente ma che se si prova a farlo
 * quando non ci sono partite in corso allora viene lanciata un'opportuna
 * eccezione e non cambia in alcun modo lo stato degli oggetti sottostanti.</p>
 */
package it.uniba.app.wordle.domain;