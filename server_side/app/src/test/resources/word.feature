Feature: Word Get Information
  Scenario: When word id is passed that word is retrieved from the database
    Given User gives id 1
    When The user makes a call to "http://localhost:8080/api/word/get/" get the details
    Then The API should return the word and response code 200