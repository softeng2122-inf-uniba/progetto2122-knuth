# Report
## 1. Introduzione

Wordle è un videogioco in cui il giocatore deve indovinare una parola segreta, avendo a disposizione 6 tentativi che dovranno essere parole di una lunghezza
    prestabilita (nel nostro caso 5 lettere). Ad ogni tentativo, ogni lettera della parola inserita viene evidenziata in:

- **VERDE** se è contenuta nella parola segreta ed è nella giusta posizione.
- **GIALLO** se è contenuta nella parola segreta ma in una posizione diversa.
- **GRIGIO** se non è contenuta nella parola segreta.

La versione presentata in questo progetto è una variante del gioco originale che utilizza un'interfaccia a linea di comando. Si gioca inserendo i seguenti comandi:
    
- `/gioca` : inizia una nuova partita (la parola segreta deve essere impostata)
- `/nuova <parola>`: imposta `<parola>` come parola segreta
- `/mostra`: visualizza la parola segreta impostata
- `/abbandona`: abbandona la partita in corso
- `/esci`: chiude il gioco
- `<parola>`: per effettuare un tentativo

*Nota: tutto il testo inserito a seguito di un comando, e separato da esso con uno o più caratteri di spaziatura, viene ignorato.*
