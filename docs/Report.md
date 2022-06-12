# Report
## 1. Introduzione

Wordle è un videogioco in cui il giocatore deve indovinare una parola segreta, avendo a disposizione 6 tentativi che dovranno essere parole di una lunghezza
    prestabilita (nel nostro caso 5 lettere). Ad ogni tentativo, ogni lettera della parola inserita viene evidenziata in:

- **VERDE** se è contenuta nella parola segreta ed è nella giusta posizione.
- **GIALLO** se è contenuta nella parola segreta ma in una posizione diversa.
- **GRIGIO** se non è contenuta nella parola segreta.

La versione presentata in questo progetto è una variante del gioco originale che utilizza un'interfaccia a linea di comando. 

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

## 4. System design

### 4.1 Stile architetturale adottato
Lo stile architetturale scelto per il progetto è il **Model-View-Presenter**: offre una corrispondenza con la tassonomia *entity-control-boundary*, già sfruttata per l'implementazione delle user story. Entrambi infatti prevedono l'utilizzo di controllori come unica porta di accesso al modello dei dati da parte dell'interfaccia utente.

_nota: per brevità si ometterà il prefisso **it.uniba.app** dei package._

**Package _wordle.logic_**: si compone dei sottosistemi *model* e *presenter*
- Le interfacce *PlayerController* e *WordsmithController*, assieme alle loro implementazioni *WordlePlayerController* e *WordleWordsmithController*, costituiscono il sottosistema *presenter* e forniscono un'*Application Programming Interface* completa che permette di giocare a Wordle. L'API:
  - Può essere utilizzata per qualsiasi tipo di *User Interface* si voglia creare.
  - Controlla tutte le condizioni di integrità per la corretta esecuzione delle partite.
  - Gestisce internamente tutte le interazioni con le classi di tipo entity senza esporle all'esterno del package.

- Le classi *entity* ricalcano le classi concettuali individuate nel modello di dominio (con l'aggiunta di *WordleSession*) e costituiscono il sottosistema *model*.

**Package _wordle.UI.CLI_**: si compone del sottosistema *view* che comprende le classi *boundary*

- Le classi contenute realizzano la *Command Line User Interface* per Wordle e utilizzano i servizi delle classi di controllo importando il package _wordle.logic_.

### 4.2 Diagramma dei package
<p align="center">
  <img src="../drawings/diagramma dei package.svg" alt="Diagramma dei package" width="300"/>
</p>

_nota: si è scelto di non riportare il diagramma delle componenti in quanto rappresenterebbe una ridondanza e non aggiungerebbe ulteriori informazioni._

## 5. OO design
Nella modellazione delle classi abbiamo mantenuto una costante attenzione verso la suddivisione dei ruoli **entity**, **control** e **boundary**. Due aspetti da notare sono l'introduzione della classe WordleSession e la modalità di comunicazione tra i due tipi diversi di controller.

**WordleSession**: classe entity non presente nel modello di dominio, rappresenta una *sessione di gioco*, ovvero una sequenza di partite effettuate dallo stesso giocatore e monitorate da un paroliere.

Questa classe semplifica la gestione delle **impostazioni di gioco**: è necessario memorizzare i valori che caratterizzeranno la partita che si vuole iniziare, ma che non devono essere persi una volta terminata.
- Ad esempio, la parola impostata dal paroliere viene memorizzata nella sessione e copiata nel momento in cui viene iniziata una nuova partita.
- Altri valori memorizzati riguardano il numero di lettere della parola da indovinare e il numero massimo di tentativi. Nella versione attuale del gioco, però, non sono modificabili.

**Comunicazione controller**: i controller realizzati memorizzano un riferimento alla sessione su cui possono operare:
- alla creazione di un nuovo *WordlePlayerController* esso inizia una sessione di gioco
- alla creazione di un nuovo *WordleWordsmithController* si passa come parametro il controller del giocatore già creato e questi opereranno sulla stessa sessione.

Questa scelta permette di realizzare facilmente modifiche future riguardanti la presenza di più giocatori (istanziando controller diversi).

### 5.1 Diagrammi user story
Nei seguenti diagrammi vengono omessi alcuni dettagli facilmente comprensibili dal codice in quanto appesantirebbero inutilmente la lettura; inoltre i metodi che costituiscono l'interfaccia fornita dalla classe controllo vengono sempre riportati in quanto riteniamo importante evidenziare tutti i metodi per la comunicazione tra la _User Interface_ e l'_API PlayerController_ o l'_API WordSmithController_.

_Nota: gli attributi e i metodi della classe parser vengono tutti utilizzati in ogni User story, per cui si presenta tale classe nella sua interezza in modo tale da non ripeterne i membri negli altri diagrammi delle classi._

<p align="center">
<img src="../drawings/Parser.svg" alt="Parser" width="500" />
</p>

- **Impostazione manuale parola segreta**: *Come paroliere voglio impostare una parola segreta manualmente*

<p align="center">
  <img src="../drawings/CLS - Nuova parola.svg" alt="CLS - Nuova parola" width="800"/>
</p>

<p align="center">
  <img src="../drawings/SEQ - Nuova parola.svg" alt="SEQ - Nuova parola" width="1000"/>
</p>


- **Mostra parola segreta**: *Come paroliere voglio mostrare la parola segreta*

  I diagrammi delle classi e di sequenza di questa user story sono molto simili a quelli della precedente, con variazioni opportune del metodo get e del metodo di stampa

- **Comando help**: *Come giocatore voglio mostrare l'help con elenco comandi*

  Anche in questo caso i diagrammi delle classi e di sequenza sono molto simili a quelli di **Impostazione manuale parola segreta**, con variazioni opportune dei metodi:
  - _printDescription_ e _printHelp_ della classe **WordlePrinter** al posto di _executeSetSecretWord_
  - non vengono effettuate chiamate alla classe **WordlePlayerController** o **WordleWordsmithController**


- **Inizio nuova partita**: *Come giocatore voglio iniziare una nuova partita*

  La molteplicità 0..1 nel legame tra **WordlePlayerController** e **WordleGame** è giustificata dal fatto che all'inizio la partita non esiste ma viene creata nel corso dell'interazione

<p align="center">
  <img src="../drawings/CLS - Inizio nuova partita.svg" alt="CLS - Inizio nuova partita" width="800"/>
</p>

<p align="center">
  <img src="../drawings/SEQ - inizio nuova partita.svg" alt="SEQ - inizio nuova partita" width="1000"/>
</p>

Diagramma di sequenza **printBoard**:

<p align="center">
  <img src="../drawings/SEQ - printBoard.svg" alt="SEQ - printBoard" width="800"/>
</p>


- **Abbandono partita**: *Come giocatore voglio abbandonare la partita*

<p align="center">
  <img src="../drawings/CLS - abbandona partita.svg" alt="CLS - abbandona partita" width="800"/>
</p>

<p align="center">
  <img src="../drawings/SEQ - abbandona partita.svg" alt="SEQ - abbandona partita" width="1000"/>
</p>


- **Chiusura gioco**: *Come giocatore voglio chiudere il gioco*

  I diagrammi delle classi e di sequenza di questa user story sono molto simili a quelli della precedente, con variazioni opportune dei metodi:
  - impostazione attributo running a false al posto del metodo _endGame_
  - anche in questo caso ci sarà richiesta di conferma

- **Tentativo parola segreta**: *Come giocatore voglio effettuare un tentativo per indovinare la parola segreta*

<p align="center">
  <img src="../drawings/CLS - Tentativo.svg" alt="CLS tentativo" width="800"/>
</p>

<p align="center">
  <img src="../drawings/SEQ - Tentativo.svg" alt="SEQ tentativo" width="1000"/>
</p>

Diagramma di sequenza **guess**: Alla creazione delle **LetterBox** vengono inserite le lettere che compongono la stringa _w_ e viene impostato il valore *NO_COLOR*

<p align="center">
  <img src="../drawings/SEQ - guess.svg" alt="SEQ - guess" width="1000"/>
</p>

La realizzazione dell'algoritmo per l'impostazione dei colori prevede l'utilizzo di un dizionario in quanto è necessario memorizzare delle coppie in cui la chiave sia una lettera presente nella parola segreta e il valore associato sia il numero di occorrenze in cui è presente.

In questo modo, a seguito del controllo posizionale che determina quali lettere saranno verdi, sarà stato decrementato il numero di occorrenze corrispondenti a queste.

Adesso risulta semplice, scandendo le lettere rimanenti (non verdi) da sinistra verso destra, determinare se una lettera del tentativo dev'essere colorata in giallo. Entrambe le condizioni seguenti devono essere verificate affinché ciò succeda:
- tale lettera dev'essere presente nel dizionario (ossia presente nella parola segreta)
- il numero di occorrenze rimanenti dev'essere maggiore di 0 (altrimenti tutte le altre lettere uguali sono state già segnalate)

Le lettere restanti sono, ovviamente, da colorare in grigio.

_nota: se nel tentativo sono presenti più lettere uguali che dovrebbero essere colorate di giallo perché rispettano le condizioni viste, verrà data precedenza a quelle più a sinistra (da notare che a ogni passo verrà decrementato il numero di occorrenze contenuto nel dizionario)._


### 5.2 Design Pattern
A seguito dello studio dei Design Pattern si è discusso in merito alla possibilità di utilizzarli e si è posta particolare attenzione ai vantaggi e svantaggi che avrebbero comportato.
L'unico pattern di cui è stata riconosciuta l'applicabilità è **Command**, che consiste nell'isolare la porzione di codice che effettua una determinata azione dal codice che ne richiede l'esecuzione, incapsulando l'azione in un oggetto Command.

La **realizzazione canonica** del pattern prevede la definizione di un'interfaccia Command, la quale deve dichiarare il metodo astratto _execute_, e la realizzazione di una classe per ogni comando che implementi tale metodo.
- Vantaggi:
  - Sfrutta il polimorfismo per l'esecuzione del metodo
  - Facilita l'estensione e la manutenibilità
- Svantaggi:
  - Difficile iterare su tutti i comandi esistenti
  - Serve definire una classe diversa per ogni comando

La **nostra iniziale realizzazione** dei comandi prevedeva l'utilizzo di un enum Command e la definizione dei metodi *executeNomeComando* all'interno della classe app. Si noti che i vantaggi e gli svantaggi di quest'approccio sono duali rispetto al precedente.
- Vantaggi:
  - Semplice iterare su tutti i comandi esistenti
  - È sufficiente definire unicamente l'enum Command e definirne i valori nel codice
- Svantaggi:
  - Difficile manutenibilità e modificabilità del codice: per differenziare il funzionamento di un metodo a seconda del comando in input serve utilizzare istruzioni switch. Queste devono essere eventualmente aggiornate con un nuovo case nel momento in cui viene introdotto un nuovo comando.
  - L'introduzione di un nuovo comando comporta quindi modifiche ai vari punti del programma in cui vengono utilizzati i comandi.

**Soluzione**: si è deciso di applicare il pattern in **forma non canonica**, ovvero è stata trovata una soluzione intermedia per mantenere solo i vantaggi di entrambi gli approcci.
Sfruttando a pieno le funzionalità delle Enum in Java, sono stati aggiunti:
1. _numArguments_: attributo d'istanza dell'Enum (ogni oggetto Command avrà un valore associato) per incapsulare il numero di argomenti attesi dal comando
2. _getNumArgs_: metodo getter per l'attributo
3. _execute_: **metodo astratto**, viene implementato per ogni istanza di Command creata

**Conclusioni**: questo utilizzo dell'enum permette di creare un'interfaccia e diverse implementazioni in un unico file. È anche possibile **sfruttare il polimorfismo** per scegliere dinamicamente l'implementazione di _execute_ da eseguire o per ottenere informazioni sul comando.

Inoltre aver mantenuto una classe enumerativa permette l'iterazione *for-each* sui suoi valori.

Infine, ogni volta che servirà aggiungere un nuovo comando, tutte le modifiche da fare risiederanno all'interno della sua definizione, aspetto che facilita le modifiche future e riduce la possibilità di introdurre bug.

_Esempio: nel main è possibile eseguire il comando ricevuto in output dal parser (senza alcuna istruzione switch) direttamente invocando il metodo execute, la cui implementazione viene scelta dinamicamente a seconda del comando effettivo._

### 5.3 Analisi delle scelte effettuate in relazione ai principi dell'OO design

Durante la progettazione e la scrittura del codice del progetto si è mantenuta una costante attenzione ai principi dell'OO design: di seguito si riportano solo i principali aspetti rilevanti.

**Information hiding**: tutti gli attributi delle classi sono stati resi privati ed accessibili solo attraverso opportune operazioni di get e set, incapsulando correttamente i dati.

**Alta coesione**: ogni classe presenta una resposabilità ben definita e si occupa solo delle operazioni ad essa competenti.

_Esempio: tutte le stampe a schermo vengono delegate alla classe **WordlePrinter**: essa eredita da **PrintWriter** i metodi comuni per la stampa (printf, println) e definisce dei metodi specifici per le stampe di gioco (versione CLI), come quella della matrice dei tentativi (printBoard)._

**Presentazione separata**: la separazione tra la logica e la presentazione è garantita dallo stile architetturale utilizzato, come evidenziato dalla separazione dei package nel punto 4.

Con particolare riferimento ai principi **SOLID**:

- **Interface Segregation**: la progettazione delle due interfacce del package *wordle.logic*, ovvero PlayerController e WordsmithController, ha permesso una divisione concettuale dei compiti concorde ai diversi ruoli di giocatore e paroliere. In questo modo si è evitato l'utilizzo di un'unica interfaccia poco coesa, che sarebbe stata destinata in futuro a raccogliere sempre più metodi non correlati.

- **Dependency inversion**: le astrazioni, ovvero le classi entity, non dipendono da altre classi; i moduli di alto livello (quali le classi di controllo) non dipendono dalla particolare UI utilizzata.

## 6. Riepilogo del test

<p align="center">
  <img src="./img/Jacoco general.png" alt="Jacoco general" width="1000"/>
</p>

<p align="center">
  <img src="./img/Jacoco domain.png" alt="Jacoco domain" width="1000"/>
</p>

<p align="center">
  <img src="./img/Jacoco CLI.png" alt="Jacoco CLI" width="1000"/>
</p>


## 7. Manuale utente

### Scopo dell'applicazione

L'applicazione consente di giocare a *Wordle* da linea di comando.

### Descrizione funzionale dell'applicazione

Questa versione del gioco consente di
- Ricoprire il ruolo di **paroliere**: come paroliere, si può inserire una nuova parola segreta e mostrarla a video durante il gioco.
- Ricoprire il ruolo di **giocatore**: come giocatore, si può iniziare una nuova partita, effettuare i tentativi allo scopo di indovinare la parola segreta e abbandonare una partita in corso.

### Guida ai comandi

Per interagire con l'applicazione si utilizzano i seguenti comandi:
    
- `/gioca` : inizia una nuova partita (la parola segreta deve essere impostata)
- `/nuova <parola>`: imposta `<parola>` come parola segreta
- `/mostra`: visualizza la parola segreta impostata
- `/abbandona`: abbandona la partita in corso
- `/esci`: chiude il gioco
- `<parola>`: per effettuare un tentativo

*Nota: tutto il testo inserito a seguito di un comando, e separato da esso con uno o più caratteri di spaziatura, viene ignorato.*

#### **1. Help**

Tramite il comando `/help`, l'applicazione stampa la descrizione del gioco e la lista dei comandi disponibili.

Nel caso di lancio dell'applicazione con flag `--help` o `-h`, il comando `/help` viene richiamato contestualmente all'avvio.

<p align="center">
  <img src="./img/report img/guida utente img/comandHelp.png" alt="comandHelp" width="1000"/>
</p>

### *Nel ruolo di paroliere*

#### **2. Inserimento della parola segreta**

Tramite il comando `/nuova <parola>`, dove `<parola>` è una parola composta da 5 lettere, si imposta la parola segreta in modo tale che alla creazione di una nuova partita questa sarà la parola da indovinare.

<p align="center">
  <img src="./img/report img/guida utente img/comandoNuovaTrenoOk.png" alt="comandoNuovaTrenoOk" width="350"/>
</p>

La parola segreta impostata rimane la stessa fin quando non viene rieseguito il comando `/nuova` da parte del paroliere.

Nel caso di inserimento di una parola segreta contenente caratteri non alfabetici o di lunghezza diversa da 5 caratteri, l'applicazione notifica l'errore.

<p align="center">
  <img src="./img/report img/guida utente img/NuovaNonValida.png" alt="NuovaNonValida" width="350"/>
</p>

<p align="center">
  <img src="./img/report img/guida utente img/NuovaLunghezzaInvalida.png" alt="NuovaLunghezzaInvalida" width="350"/>
</p>

#### **3. Stampa parola segreta**

Tramite il comando `/mostra`, in qualsiasi momento del gioco è possibile visualizzare la parola segreta attualmente impostata.

<p align="center">
  <img src="./img/report img/guida utente img/comandoMostra.png" alt="comandoMostra" width="350"/>
</p>

Nel caso in cui si esegue il comando `/mostra` in assenza di una parola segreta impostata, l'applicazione ne notifica l'assenza.

<p align="center">
  <img src="./img/report img/guida utente img/MostraSenzaParolaImpostata.png" alt="MostraSenzaParolaImpostata" width="350"/>
</p>

### *Nel ruolo di giocatore*

#### **4. Inizio partita**

Tramite il comando `/gioca` è possibile iniziare una nuova partita, mostrando la griglia delle lettere vuota.

Nel caso in cui si esegue il comando `/gioca` in assenza di una parola segreta impostata, l'applicazione informa dell'assenza della parola segreta, per cuinon sarà possibile iniziare una nuova partita e di conseguenza non viene stampata la griglia delle lettere.

<p align="center">
  <img src="./img/report img/guida utente img/comandoGioca.png" alt="comandoGioca" width="300"/>
</p>

#### **5. Svolgimento di una partita**

Per inserire un nuovo tentativo è sufficiente digitare la parola desiderata.

Ogni partita prevede un limite di tentativi possibili pari a 6.
A seguito dell'inserimento di un tentativo valido, viene aggiornata e stampata la griglia delle lettere. Ad ogni lettera del tentativo viene associato un colore:
- **verde**, nel caso in cui la parola segreta della partita contiene tale lettera e in tale posizione
- **giallo**, nel caso in cui la parola segreta della partita contiene tale lettera ma non in tale posizione
- **grigio**, nel caso in cui la parola segreta non contiene tale lettera

Nel caso in cui il tentativo inserito non corrisponde alla parola segreta e non è terminato il numero di tentativi disponibili, l'applicazione stampa la matrice dei tentativi aggiornata e rimane in attesa di un nuovo comando o tentativo.

<p align="center">
  <img src="./img/report img/guida utente img/tentativi.png" alt="tentativi" width="200"/>
</p>

Nel caso in cui si esauriscono i tentativi disponibile non indovinando la parola segreta, l'applicazione avvisa che la partita è finita e stampa la parola segreta.

<p align="center">
  <img src="./img/report img/guida utente img/parolaNonTrovata.png" alt="parolaNonTrovata" width="300"/>
</p>

Nel caso in cui si indovina la parola segreta l'applicazione notifica la vittoria e riporta il numero di tentativi effettuati.

<p align="center">
  <img src="./img/report img/guida utente img/parolaSegretaTrovata.png" alt="parolaSegretaTrovata" width="200"/>
</p>

Nel caso di inserimento di un tentativo contenente caratteri non alfabetici o di lunghezza non appropriata, l'applicazione notifica l'errore, non accettando il tentativo.

<p align="center">
  <img src="./img/report img/guida utente img/tentativiNonValidi.png" alt="tentativiNonValidi" width="200"/>
</p>

#### **6. Chiusura di una partita in corso**

Tramite il comando `/abbandona` si può abbandonare una partita in corso.

Se una partita è in corso, l'applicazione stampa un messaggio di richiesta di conferma.

<p align="center">
  <img src="./img/report img/guida utente img/comandoAbbandona.png" alt="comandoAbbandona" width="500"/>
</p>

Nel caso in cui si inserisce il comando quando non c'è nessuna partita in corso, l'applicazione ne notifica l'assenza.

<p align="center">
  <img src="./img/report img/guida utente img/AbbandonaInvalido.png" alt="AbbandonaInvalido" width="300"/>
</p>

#### **7. Chiusura dell'applicazione**

Tramite il comando `/esci` si può chiudere l'applicazione in ogni momento della partita.

In caso di inserimento di tale comando, l'applicazione stampa un messaggio di richiesta di conferma della chiusura.

<p align="center">
  <img src="./img/report img/guida utente img/comandoEsci.png" alt="comandoEsci" width="400"/>
</p>

#### **8. Comandi invalidi**

L'applicazione è capace di riconoscere comandi che differiscono per qualche lettera da un comando invalido inserito. Nell'eventualità, l'applicazione fornisce una lista dei comandi più simili.

<p align="center">
  <img src="./img/report img/guida utente img/comandiSimili.png" alt="comandiSimili" width="350"/>
</p><khy>

## 8. Processo di sviluppo e di organizzazione del lavoro

In generale il processo di sviluppo del software, nel team Knuth, ha seguito, personalizzandolo, il workflow stabilito dal framework Scrum affine ai principi dello sviluppo Agile.

- In ogni Sprint il team ha utilizzato una **Scrum board digitale** per organizzare il lavoro e per tener traccia dello stato di completamento degli issue che costituiscono lo Sprint Goal (*Sprint Backlog*).
- Il team, grazie ad una **leadership condivisa**, ha suddiviso il lavoro sulla base delle preferenze e competenze personali cercando di mantenere una distribuzione equa del lavoro. 
- Tutti i membri del team hanno mantenuto lo **stesso potere decisionale**, con assoluta libertà di contestare una decisione presa. In seguito ad ogni discussione, si è sempre raggiunto un accordo unanime.
- È sempre stato applicato correttamente il **GitHub flow** per lo sviluppo del progetto.

#### **Daily Scrum Meeting**

Fondamentali, durante il processo di sviluppo, sono stati i Daily Scrum Meeting, organizzati a inizio giornata, o eccezionalmente spostati in orari compatibili con gli impegni universitari. Hanno permesso al team di ottimizzare la collaborazione tra i membri e l'organizzazione del lavoro nel tempo a disposizione. 

#### **Microsoft Teams**
Negli Sprint 1 e 2 si è cercato, per quanto possibile, di lavorare in modo sincrono su Microsoft Teams 
- Ha attenuato il disagio derivante dalla mancaza di una sede fisica in cui lavorare
- Ha facilitato la comunicazione nello svolgimento degli issue assegnati a più persone
- Ha permesso di applicare, da remoto, la pratica del *pair programming*
- Ha permesso di risolvere agilmente le difficoltà emerse nei daily meeting
- Ha facilitato il processo di *review*, dando la possibilità agli assegnatari di rispondere alle domande dei revisori in tempo reale.

#### **Sprint Review**
Il team ha dato la dovuta importanza ad ogni Sprint Review: ogni membro vi ha partecipato, traendo spunti di miglioramento per lo sprint successivo sia dai consigli del Product Owner sia analizzando errori e punti di forza che hanno caratterizzato il lavoro degli altri team.

#### **Sprint Retrospective**
Successivamente ad ogni Sprint Review si è organizzata una Sprint Retrospective, che il team ha utilizzato come occasione per
- Discutere quanto appreso durante la Review
- Raccogliere le opinioni dei membri del team in merito alle modalità di lavoro applicate nello sprint appena concluso, proponendo *idee di miglioramento* e riconfermando *aspetti organizzativi già funzionali*
- Avere consapevolezza dell'*umore* dei vari membri e capire come mantenere alto l'*entusiasmo*
- Prendere coscienza dei *tempi del team* e riorganizzarsi in maniera efficiente, ottimizzando i carichi di lavoro.

**Tools per l'abbozzo**: lavagne, fogli di carta e post-it per l'*analisi* e la *modellazione* *Object Oriented*.

**Tools per l'organizzazione**: *GitHub project*, *Trello* e *Microsoft Teams*. 

**Tools per lo sviluppo**: *IntelliJ IDEA* (per la scrittura del codice), *Visual Studio Code* (per stesura file markdown), *StarUML* (per la creazione di diagrammi) e *git CLI* (per il controllo di versione).

## 9. Analisi retrospettiva
### Sprint 1

In seguito alla review dello Sprint 1, il team Knuth si è riunito in uno Sprint Retrospective che ha come obiettivi: individuare opportunità di **crescita** e **miglioramento** e incrementare la **produttività** e la **soddisfazione** per gli obiettivi raggiunti (vedasi il punto 8 - "Processo di sviluppo e di organizzazione del lavoro" del presente report).

Il risultato di questo meeting trova rappresentazione nella **whiteboard** digitale riportata di seguito. La whiteboard è stata suddivisa in tre colonne dedicate, rispettivamente, agli aspetti che hanno fatto provare rabbia, tristezza o soddisfazione. Ogni membro del team ha quindi potuto scrivere le proprie opinioni su **post-it** virtuali che ha posizionato sulle rispettive colonne.

<p align="center">
  <img src="./img/report img/Whiteboard Retrospective.png" alt="Whiteboard Retrospective" width="1000"/>
</p>
