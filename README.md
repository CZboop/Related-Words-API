# Related-Words-API

Spring Boot REST API to return a similar word or words, either with a semantic similarity or similar letters. 

Python is used to create tables of semantic similarity, the ["Related Words SQL Maker"](https://github.com/CZboop/Related-Words-API/blob/main/data/related_words_sql_maker.py) class takes an existing Word2Vec model of word meanings/relations and outputs a SQL file to be used in the Spring Boot REST API, using Gensim. NLTK is used to add some information about whether the word falls into one of several broad part of speech categories. 

Java logic within the backend is used to retrieve words that have some of the same letters in the same positions, and there is an option to return words that are part of the same part of speech category.

Includes unit tests and integration tests, using Cucumber/Gherkin, JUnit, AssertJ and Mockito.

## How to Use
Runs locally on the default port http://localhost:8080/, with a built in dataset of 5000 words. The PSQL database is also local, and before the first time the app is run, a local database called "related_words" must be created that will be populated on initial app startup. PSQL can be downloaded [here](https://www.postgresql.org/download/) and [this article](https://www.prisma.io/dataguide/postgresql/setting-up-a-local-postgresql-database) walks through the PSQL setup process.

The Spring Boot app in the [AppApplication file](https://github.com/CZboop/Related-Words-API/blob/main/server_side/app/src/main/java/com/relatedWords/app/AppApplication.java) can then be run, and the API can then be used by visiting one of the endpoints while the app is running.

Other data can be used within the API, but the entire database should be one batch as created by the current data preparation script (rather than combining the existing data with new data, for example).

### Endpoints

```http://localhost:8080/api/word/related/{word}``` 
Returns all semantically related words
Example of response:
```
[
  {
    "id": 323,
    "value": "earth",
    "partOfSpeech": "VERB",
    "length": 5
  }
] 
```

```http://localhost:8080/api/word/related/{word}/random``` 
Returns one random semantically related word
Example of response:
```
{
  "id": 133,
  "value": "world",
  "partOfSpeech": "NOUN",
  "length": 5
}
```

```http://localhost:8080/api/word/related/{word}/pos``` 
Returns all semantically related words that have the same part of speech as the word in the API call
Example of response:
```
[
  {
    "id": 323,
    "value": "earth",
    "partOfSpeech": "VERB",
    "length": 5
  }
] 
```

```http://localhost:8080/api/word/letters/{word}``` 
Returns list of all words with one or more of the same letters in the same position, alongside the number of matching letters. All words will be the same length
Example of response:
```
{
  "shakes": 2,
  "branch": 2,
  "honest": 1
}
```

```http://localhost:8080/api/word/letters/{word}/limit={n}``` 
Returns list of words with number of matching letters in matching positions, up to a maximum length of the number in the API call
Example of response:
```
{
  "shakes": 2,
  "branch": 2,
  "honest": 1
}
```

```http://localhost:8080/api/word/letters/pos/{word}``` 
Returns list of all words with one or more matching letters in matching positions, with a list of indices of matching letters for each word
Example of response:
```
{
  "shakes": [
    2,
    4
  ],
  "branch": [
    2,
    3
  ],
  "honest": [
    5
  ]
}
```

```http://localhost:8080/api/word/letters/pos/{word}/limit={n}``` 
Returns list of words with number of matching letters in matching positions, with a list of indices of matching letters for each word, up to the limit n
Example of response:
```
{
  "shakes": [
    2,
    4
  ],
  "branch": [
    2,
    3
  ],
  "honest": [
    5
  ]
}
```
