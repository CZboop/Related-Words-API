package com.relatedWords.app.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("api/word")
public class WordController {
    private WordService wordService;

    @Autowired
    public WordController(WordService wordService){
        this.wordService = wordService;
    }

    @GetMapping("/get/{id}")
    public Word getWordById(@PathVariable int id){
        return wordService.getWordById(id);
    }

    @PostMapping("/add")
    public void addWord(@RequestBody Word word){
        wordService.addWord(word);
    }

    @GetMapping("/related/{word}")
    public ArrayList<Word> getRelatedWords(@PathVariable String word){
        return wordService.getRelatedWords(word);
    }

    @GetMapping("/related/{word}/random")
    public Word getRandomRelatedWord(@PathVariable String word){
        return wordService.getRandomRelatedWord(word);
    }

    @GetMapping("/related/{word}/pos")
    public ArrayList<Word> getRelatedWordsSamePOS(@PathVariable String word){
        return wordService.getRelatedWordsSamePOS(word);
    }

    @GetMapping("/letters/{word}")
    public HashMap<String, Integer> getWordsNumberOfMatchingLetters(@PathVariable String word){
        return wordService.getWordsNumberOfMatchingLetters(word);
    }

    @GetMapping("/letters/{word}/limit={n}")
    public HashMap<String, Integer> getNWordsNumberOfMatchingLetters(@PathVariable String word, @PathVariable int n){
        return wordService.getNWordsNumberOfMatchingLetters(word, n);
    }

    @GetMapping("/letters/pos/{word}")
    public HashMap<String, ArrayList> getWordsPositionOfMatchingLetters(@PathVariable String word){
        return wordService.getWordsPositionOfMatchingLetters(word);
    }

    @GetMapping("/letters/pos/{word}/limit={n}")
    public HashMap<String, ArrayList> getNWordsPositionOfMatchingLetters(@PathVariable String word, @PathVariable int n){
        return wordService.getNWordsPositionOfMatchingLetters(word, n);
    }
}
