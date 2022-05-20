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

## 2. Modello di dominio

Si è considerata la griglia di gioco come una matrice di tentativi a dimensione fissa (6 righe, 5 colonne), che inizialmente viene raffigurata come una griglia vuota, le cui righe verranno popolate dall'alto verso il basso con le parole rappresentanti i tentativi validi. Questo giustifica la molteplicità scelta, in quanto il numero di legami della matrice dei tentativi con i tentativi aumenta nel corso della partita.

<p align="center">
  <img src="../drawings/modello di dominio.svg" alt="modello di dominio" width="450"/>
</p>


- L'attributo "vincente" di tentativo indica se la parola che compone il tentativo corrisponde alla *parolaSegreta* della partita.
- Il numero delle "righe" della matrice dei tentativi rappresenta il numero massimo di tentativi validi che possono essere effettuati. 
- Come si legge dai requisiti funzionali, è presente una tastiera all'interno del gioco: si è scelto di non rappresentarla nel modello di dominio in quanto non è trattata nell'iterazione corrente. 

## 3. Requisiti specifici

### 3.1 Requisiti funzionali

- **Impostazione manuale parola segreta**: *Come paroliere voglio impostare una parola segreta manualmente*  
   
    *Criteri di accettazione*:

    Al comando `/nuova<parola>`

    l’applicazione risponde:
    - Parola segreta troppo corta se i caratteri sono inferiori a quelli del gioco
    - Parola segreta troppo lunga se i caratteri sono superiori a quelli del gioco
    - Parola segreta non valida se ci sono caratteri che non corrispondono a lettere dell’alfabeto

    altrimenti

    - l’applicazione risponde con OK e memorizza la parola fino a chiusura applicazione. 

    È possibile cambiare la parola durante una sessione di gioco anche senza uscire dall’applicazione.

- **Mostra parola segreta**: *Come paroliere voglio mostrare la parola segreta*

    *Criteri di accettazione*: 

    Al comando `/mostra`

    l’applicazione risponde visualizzando la parola segreta.

- **Comando help**: *Come giocatore voglio mostrare l'help con elenco comandi*

    *Criteri di accettazione*:

    Al comando `/help` o invocando l'app con flag `--help` o `-h`

    il risultato è una descrizione concisa, che normalmente appare all'avvio del programma, seguita dalla lista di comandi disponibili, uno per riga, come da esempio successivo:
    - gioca
    - esci
    - ...

- **Inizio nuova partita**: *Come giocatore voglio iniziare una nuova partita*

    *Criteri di accettazione*:

    Al comando `/gioca`

    se nessuna partita è in corso l'app mostra la matrice dei tentativi vuota, ma senza mostrare la tastiera, e si predispone a ricevere il primo tentativo o altri comandi


- **Abbandono partita**: *Come giocatore voglio abbandonare la partita*

    *Criteri di accettazione*:

    Al comando `/abbandona`

    l'app chiede conferma 
    - se la conferma è positiva, l'app comunica l’abbandono
    - se la conferma è negativa, l'app si predispone a ricevere un altro tentativo o altri comandi

- **Chiusura gioco**: *Come giocatore voglio chiudere il gioco*

    *Criteri di accettazione*:

    Al comando `/esci`

    l'applicazione chiede conferma 

    - se la conferma è positiva, l'app si chiude restituendo un *zero exit code*
    - se la conferma è negativa, l'app si predispone a ricevere nuovi tentativi o comandi


- **Tentativo parola segreta**: *Come giocatore voglio effettuare un tentativo per indovinare la parola segreta*

    *Criteri di accettazione*:

    Digitando caratteri sulla tastiera e invio l’applicazione risponde:
    - *Tentativo incompleto* se i caratteri sono inferiori a quelli della parola segreta
    - *Tentativo eccessivo* se i caratteri sono superiori a quelli della parola segreta
    - *Tentativo non valido* se ci sono caratteri che non corrispondono a lettere dell’alfabeto

    altrimenti

    riempiendo la prima riga libera della matrice dei tentativi con i caratteri inseriti e colorando lo sfondo di:
    - *verde* se la lettera è nella parola segreta e nel posto giusto,
    - *giallo* se la lettera è nella parola segreta ma nel posto sbagliato
    - *grigio* se la lettera non è nella parola segreta.

    Se le lettere sono tutte verdi l’applicazione risponde: 
    ```
    Parola segreta indovinata
    Numero tentativi: <...>
    ```
    e si predispone a nuovi comandi.

    Se il tentativo fallito è l’ultimo possibile , l’applicazione risponde: 
    ```
    Hai raggiunto il numero massimo di tentativi. 
    La parola segreta è <...>
    ```
    e si predispone a nuovi comandi.


    Se la parola segreta non è stata impostata l’applicazione risponde
    ```
    Parola segreta mancante
    ```
    *nota: quest'ultima risposta è stata resa in modo leggermente diverso: se la parola segreta è mancante allora non è possibile iniziare la partita, per cui il controllo sulla parola segreta mancante è effettuato al momento di creazione della nuova partita. Risulta importante comunque controllare che una partita sia effettivamente in corso nel momento in cui si prova ad effettuare un tentativo.*


### 3.2 Requisiti non funzionali

- **RNF1**: il container docker dell’app deve essere eseguito da terminali che supportano Unicode con encoding UTF-8 o UTF-16.

    Elenco di terminali supportati :

    Linux:
    - terminal

    Mac OS
    - terminal

    Windows
    - Powershell
    - Git Bash (in questo caso il comando Docker ha come prefisso winpty; es: winpty docker -it ...)

    *Comando per l’esecuzione del container*

    Dopo aver eseguito il comando docker pull copiandolo da GitHub Packages, Il comando Docker da usare per eseguire il container contenente l’applicazione è:
    ```
    *docker run --rm -it ghcr.io/softeng2122-inf-uniba/wordle-knuth2122:latest*
    ```
    _**Importante**: il numero di colori supportato varia a seconda del terminale. Nello specifico, **git bash** supporta solamente 8 colori (codifica ANSI), come si può verificare dal seguente comando_

    <p align="center">
    <img src="./img/report img/git colors.png" alt="git colors" width="500"/>
    </p>

    _Tra questi 8 colori non vi è il grigio (https://en.wikipedia.org/wiki/ANSI_escape_code#3-bit_and_4-bit), per cui abbiamo cercato una soluzione che funzionasse per ognuno dei terminali citati: non avendo trovato un modo semplice per individuare a run-time il numero di colori supportati in base al terminale su cui si sta eseguendo l'applicazione, abbiamo scelto di utilizzare il colore **bianco** come sostituto del grigio._

    _Per una migliore visualizzazione dei colori è comunque consigliato utilizzare il tema di default in ogni terminale (temi diversi potrebbero portare a una visualizzazione incoerente ed effetti non prevedibili a priori)._
