Song Lyrics
====
This project creates song lyrics using a Markov Model trained on the word transitions of specified "seed" song. 

Step 0) Run the application. Be sure to set your MySQL connection settings in `src/main/resources/application.properties`. 
```
mvn clean spring-boot:run
```

Step 1) Add a "seed" song.

POST /songs

Post Body: 
```
{
    "name": "Seed song Name",
    "lyrics": "Song lyrics go here"
}
```

Step 2) Generate new songs. 

GET /songs/{songId}/wordMarkovModel?startWord={startWord}&length={length}

Returns a new song generated based on the word transition frequencies of the "seed" song specified by {songId}. 