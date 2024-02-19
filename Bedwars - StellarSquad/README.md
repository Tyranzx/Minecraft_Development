# 🛌 BEDWARS | 1.7+
		by xJess (Lewysan) - Contributors: StellarSquad 🎲
  	      SpigotMC: https://www.spigotmc.org/members/jessia.1858326/
    
## [**⚠ IMPORTANTE ⚠**] Attualmente non é disponibile in nessun server. Voglio menzionare che é evidente che ho sufficienti prove veridiche per dimostrare la creazione di questo plugin, sia screenshot, video o gente che possa dimostrarlo. É stato sviluppato da zero, e ogni singolo successo e dettaglio del plugin è stato registrato o catturato tramite screenshot da me e dalla mia squad. La conseguenza di voler dimostrare il contrario, sará il confronto di prove legalmente veridiche, e non solo, anche contro server, come ShynoMC il quale, i membri dello staff sono stati presenti, alcuni, durante la sua creazione.

# Caratteristiche dettagliate:

# 📊 Scoreboard + Tablist + Player Meta 
  ### [✅] **PLAYER META**: 
  #### Voglio spiegare una cosa: Durante la creazione della modalitá KitPVP ho imparato a non dover dipendere da plugin che facciano la funzione di scoreboard, tablist e visiblitá del rank in ogni parte.
  #### Utilizzo un'api, StellarAPI, fatta da me, per me e per tutta la mia comunitá di StellarSquad per automatizzare la creazione di questa sincronizzazione con Luckperms. E non solo, contiene anche metodi e strutture di codici pronte all'uso.
  ### [✅] **SCOREBOARD**: 
  #### Scoreboard, come tutte le altre modalitá che faró in futuro, sempre utilizzando il piú possibile, tecniche e risorse proprie dell'api di spigot per aumentare e mantenere la sua ottimizzazione. 
  ### [✅] **TABLIST**:
  #### Per il tablist, utilizzeró, grazie al kitpvp che mi ha insegnato delle cose, tecniche e risorse di tipo legacy, ovviamente mantenendo la compatibilitá con piú versioni di minecraft ma non rendendo il plugin troppo pesante. 
  -  ![Todo](https://github.com/Tyranzx/Minecraft_Development/assets/70720366/18de7e1b-b87e-4069-b33f-38198e96e55f)

# 🎲 Sistema di Partite
 ### [✅] Sistema di modi:
 #### Questo core, ha la possibilitá di creare e di giocare, partite di tipo **SOLO**, quindi senza una squadra contro altri giocatori, e non solo, si é fatto pensando solo a 3 modi, Solo, Duo (double) e Trio.
 ### [✅] Sistema di creazione mappe: 
 #### Ho fatto il sistema di creazione il piú facile e capibile possibile, senza la urgenza di dover specificare manualmente ogni cosa, come per esempio, il letto del giocatore, lo si fa automaticamente.
 ### [✅] Selezione automatica di modo e partita
 ### [✅] Stato del giocatore:
 #### Quando il giocatore entra nella partita, ho fatto in modo che si leggano e salvino le seguenti informazioni: Partita alla quale entra e Modo della partita (quindi solo, duo o trio)
 ### [✅] Scoreboard automatica
 #### La scoreboard, conta quanti team ci sono, nella mappa di un modo di gioco, quindi "solo", "duo", "trio" hanno le proprie mappe.
 #### Quindi, "X" per i team i quali sono vuoti e "V" per i team che hanno almeno un giocatore. Questo, vuol dire che i letti che vengono distrutti, se i team non esistono (o almeno i giocatori non ci sono) il letto conterá come esistente.
 - ![Teams](https://github.com/Tyranzx/Minecraft_Development/assets/70720366/d3b0df96-0c80-4798-92e4-b73fe22e9697)

 # 🍀 SQLite3 (Database)
 ### [✅] Database giocatore: La modalitá di kitpvp non utilizza un sistema di database ed é per questo che non é stato menzionato. Le bedwars peró faranno un giro inaspettato, non perché ci sia la voglia di farlo ma perche c'é la neccesitá di aggiungere una database per salvare le proprieta, informazioni e in generale le statistiche del giocatore. Non é la prima volta che utilizzo database nei miei progetti, peró é una cosa che non volevo aggiungere al kitpvp ma alle bedwars si. Per renderlo, un progetto, non solamente completo ma anche ottimo da usare.
 ### [🔴] Database mappe:  Le mappe non sono salvate in un database, questo perche vogliamo la velocitá di modifica delle mappe in qualsiasi momento. 50% Opzionale 50% Utile
