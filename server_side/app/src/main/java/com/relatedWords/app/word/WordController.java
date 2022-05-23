package com.relatedWords.app.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
