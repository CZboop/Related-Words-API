package com.relatedWords.app.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {
    private WordDAO wordDAO;

    @Autowired
    public WordService(WordDAO wordDAO){
        this.wordDAO = wordDAO;
    }
}
