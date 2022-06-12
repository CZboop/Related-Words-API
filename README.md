# Related-Words-API

Spring Boot REST API to return a similar word or words, either with a semantic similarity or similar letters. 

Python is used to create tables of semantic similarity, the ["Related Words SQL Maker"](https://github.com/CZboop/Related-Words-API/blob/main/data/related_words_sql_maker.py) class takes an existing Word2Vec model of word meanings/relations and outputs a SQL file to be used in the Spring Boot REST API, using Gensim. NLTK is used to add some information about whether the word falls into one of several broad part of speech categories. 
Java logic within the backend is used to retrieve words that have some of the same letters in the same positions.

Includes unit tests and integration tests, using Cucumber/Gherkin, JUnit, AssertJ and Mockito.
