package com.relatedWords.app;

import com.relatedWords.app.word.Word;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinition {
    Integer wordId = null;
    String name = null;
    private ResponseEntity response;
    RestTemplate restTemplate = new RestTemplate();

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

}
