# Prova Finale di Ingegneria del Software - AA 2020-2021
![alt text](src/main/resources/startingTitle.png)



Il progetto consiste nell’implementazione di un sistema distribuito composto da un singolo server in grado di gestire una partita alla volta e multipli client (uno per giocatore) che possono partecipare ad una sola partita alla volta utilizzando il pattern MVC (Model-View-Controller).
La rete è stata gestita con l'utilizzo delle socket.

Interazione e gameplay: linea di comando (CLI) e grafica (GUI).

## Documentazione

### UML
I seguenti diagrammi delle classi rappresentano rispettivamente il modello iniziale sviluppato durante la fase di progettazione e i diagrammi del prodotto finale nelle parti critiche riscontrate.
- [UML Iniziali](https://github.com/)
- [UML Finali](https://github.com/ )
### JavaDoc
La seguente documentazione include una descrizione per la maggior parte delle classi e dei metodi utilizzati, segue le tecniche di documentazione di Java e può essere consultata al seguente indirizzo: [Javadoc]()

### Coverage report
Al seguente link è possibile consultare il report della coverage dei test effettuati con Junit: [Report]()


### Librerie e Plugins
|Libreria/Plugin|Descrizione|
|---------------|-----------|
|__Maven__|Strumento di automazione della compilazione utilizzato principalmente per progetti Java.|
|__JavaFx__|Libreria grafica per realizzare interfacce utente.|
|__JUnit__|Framework di unit testing.|

## Funzionalità
### Funzionalità Sviluppate
- Regole Complete
- CLI
- GUI
- Socket
- 2 FA (Funzionalità Avanzate):
    - __Resilienza alle disconnessioni
    - __


## Compilazione e packaging
I jar sono stati realizzati con l'ausilio di Maven Shade Plugin.
Di seguito sono forniti i jar precompilati.
Per compilare i jar autonomamente, posizionarsi nella root del progetto e lanciare il comando:
```
mvn clean package
```
I jar compilati verranno posizionati all'interno della cartella ```target/``` con i nomi
```masterofrenaissance-client.jar``` e ```masterofrenaissance-server.jar```.

### Jars
I Jar del progetto possono essere scaricati al seguente link: [Jars]().


### Master of Renaissance 
Le seguenti istruzioni descrivono come eseguire il client con interfaccia CLI o GUI.

#### CLI
Per lanciare  Master of Renaissance CLI digitare da terminale il comando:
```
java -jar AM26.jar 
```
#### GUI
Per poter lanciare la modalità GUI sono disponibili due opzioni:
- effettuare doppio click sull'eseguibile ```masterofrenaissance-client.jar```
- digitare da terminale il comando:
```
java -jar AM26.jar 
```

### Master of Renaissance Server
Per lanciare MAsters of Renaissance Server digitare da terminale il comando:
```
java -jar AM26.jar  [--port <port_number>]
```
#### Parametri
- `--port` `-p` : permette di specificare la porta del server. Se non specificato il valore di default è __1234__;

## Componenti del gruppo
- [__Alessandra Atria__](https://github.com/alessandraatria)
- [__Alice Cariboni__](https://github.com/alicecariboni)
- [__Sofia Canestraci __](https://github.com/sofiacanestraci)
