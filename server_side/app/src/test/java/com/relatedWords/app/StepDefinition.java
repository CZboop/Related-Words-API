package com.relatedWords.app;

import com.relatedWords.app.word.Word;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinition {
    Integer wordId = null;
    String word = null;
    private ResponseEntity response;
    RestTemplate restTemplate = new RestTemplate();

//    find a word by id
    @Given("User gives id {int}")
    public void user_gives_word_id(Integer wordId) {
        this.wordId = wordId;
    }

    @When("The user makes a call to {string} get the details")
    public void the_user_makes_a_call_to_get_the_details(String url) {
        response = restTemplate.getForEntity(url + Integer.toString(wordId), Object.class);
    }

    @Then("The API should return the word and response code {int}")
    public void the_api_should_return_the_word_and_response_code(int statusCode) {
        assertEquals(statusCode, response.getStatusCodeValue());
    }

//   retrieve related words given word
    @Given("User gives word {string}")
    public void user_gives_word_as_string(String word) {
        this.word = word;
    }

    @When("The user makes a call to {string} to get related words")
    public void the_user_makes_a_call_to_get_related_words(String url) {
        response = restTemplate.getForEntity(url + word, List.class);
    }

    @Then("The API should return a list of related words and response code {int}")
    public void the_api_should_return_the_list_of_words_and_response_code(int statusCode) {
        assertEquals(statusCode, response.getStatusCodeValue());
    }

//    getting a single related word given a word
    @Given("User gives a word {string}")
    public void user_gives_a_word_as_string(String word) {
        this.word = word;
    }

    @When("The user makes a call to {string} to get a related word")
    public void the_user_makes_a_call_to_get_a_related_word(String url) {
        response = restTemplate.getForEntity(url + word + "/random", Object.class);
    }

    @Then("The API should return a related word and response code {int}")
    public void the_api_should_return_a_word_and_response_code(int statusCode) {
        assertEquals(statusCode, response.getStatusCodeValue());
    }

//    getting a word with same part of speech
    @Given("User gives a word {string} to find a related word with the same POS")
    public void user_gives_a_word_as_string_to_find_same_pos(String word) {
        this.word = word;
    }

    @When("The user makes a call to {string} to get a related word with the same POS")
    public void the_user_makes_a_call_to_get_a_related_word_with_same_pos(String url) {
        response = restTemplate.getForEntity(url + word + "/pos", Object.class);
    }

    @Then("The API should return a related word with the same POS and response code {int}")
    public void the_api_should_return_a_word_with_same_pos_and_response_code(int statusCode) {
        assertEquals(statusCode, response.getStatusCodeValue());
    }
}
