Feature: Word Get Information
  Scenario: When word id is passed that word is retrieved from the database
    Given User gives id 1
    When The user makes a call to "http://localhost:8080/api/word/get/" get the details
    Then The API should return the word and response code 200

  Scenario: When word given related words are retrieved from the database
    Given User gives word "example"
    When The user makes a call to "http://localhost:8080/api/word/related/" to get related words
    Then The API should return a list of related words and response code 200

  Scenario: When word given a single related word can be retrieved from the database
    Given User gives a word "word"
    When The user makes a call to "http://localhost:8080/api/word/related/" to get a related word
    Then The API should return a related word and response code 200

  Scenario: When word given related word with the same POS can be retrieved from the database
    Given User gives a word "word" to find a related word with the same POS
    When The user makes a call to "http://localhost:8080/api/word/related/" to get a related word with the same POS
    Then The API should return a related word with the same POS and response code 200