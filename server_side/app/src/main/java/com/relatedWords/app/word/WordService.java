package com.relatedWords.app.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WordService {
    private WordDAO wordDAO;

    @Autowired
    public WordService(WordDAO wordDAO){
        this.wordDAO = wordDAO;
    }

    public Word getWordById(int id){
        return wordDAO.getWordById(id).orElseThrow();
    }

    public void addWord(Word word) {
        wordDAO.addWord(word);
    }

    public ArrayList<Integer> getRelatedWordIds(String word) {
        Word targetWord = getWordByTextValue(word);
        return wordDAO.getRelatedWordIds(targetWord.getId());
    }

    public ArrayList<Word> getRelatedWords(String word){
        ArrayList<Integer> relatedIds = getRelatedWordIds(word);
        ArrayList<Word> relatedWords = new ArrayList<>();
        for (Integer id : relatedIds){
            Word relatedWord = getWordById(id);
            relatedWords.add(relatedWord);
        }
        return relatedWords;
    }

    private Word getWordByTextValue(String word) {
        return wordDAO.getWordByTextValue(word).orElseThrow();
    }

    public Word getRandomRelatedWord(String word) {
        ArrayList<Integer> relatedIds = getRelatedWordIds(word);
        int targetId = relatedIds.get((int) (Math.random()*relatedIds.size()));
        return getWordById(targetId);
    }
}
