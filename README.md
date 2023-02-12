<h1>Kandidatoppgave for PayEx</h1>

<h3>Endepunkter</h3>
* /network/top
* /tvshow/top10
* /tvshow/summary
* /tvshow/top-episode

<h3>Teknologier</h3>

Applikasjonen er skrevet i Java. Jeg har valgt Spring Boot 
som rammeverk for oppgaven, da dette inneholder en rekke 
fordeler for å sette opp et REST API, bl.a. innebygd server.

Jeg har brukt H2 som en lokal, in-memory database. Dette er brukt 
for å kjapt kunne komme i gang med oppgaven, og kan senere erstattes 
med en annen SQL database, som Postgresql eller lignende.

Applikasjonene er delt opp i følgende Layers:

**Entity**: 

Her ligger alle våre entiteter/DB tabeller:
* TvShow
* Network
* Episode
* Genre

Entitetene er bygget opp ved hjelp av Java Persistance (JPA).

**Services**:

Service laget brukes for å gjøre beregninger og samhandle med 
ulike entiteter i applikasjonen. 

**Controllers**

Her ligger våre Api-endepunkt, der informasjon kan hentes 
fra brukeren.


**Repositories**

Dette laget brukes for å gjøre SQL spørringer mot dataen i DB.
Repositoriene extender JpaRepositories Interfacet som inneholder 
en rekke nyttige funsjoner for å samhandle med data i DB. Her
kan også nye metoder defineres ved å bruke Derived Queries, der 
metode-navnet kan brukes for å definere spørringen.


<h4>Annet</h4>
**Factory**

Factory har som hovedoppgave å transformere JSON objekter fra 
spørringer mot eksternt API, om til tilsvarende Entities.

**Utils**

Utils er hjemmet til alle hjelpemetoder i applikasjonen.


<h4>Kall til eksternt API TvMaze</h4>

Request mot TvMaze er kjørt ved hjelp av **RestTemplate**.
Denne bruker Blocking Client. Dette vil si at kallet kjøres
på en tråd, som igjen betyr at applikasjonen venter på at kallet 
er klart, før den kjører videre til neste prosedyre.

Her kunne det også vært brukt en Non-Blocking Client, som 
**WebClient**.

<h3>Selvkritikk</h3>

I oppgaven har jeg ikke lagt mye fokus på testing, noe som
absolutt burde vært implementert i prosessen fra starten.
Skulle jeg startet på nytt ville jeg kjørt TDD (Test Driven Development) 
fra start, som antageligvis ville spart meg for mye feilsøkingn og retting. 
