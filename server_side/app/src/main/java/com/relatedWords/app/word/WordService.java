package com.relatedWords.app.word;

import com.relatedWords.app.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService {
    private WordDAO wordDAO;

    @Autowired
    public WordService(WordDAO wordDAO){
        this.wordDAO = wordDAO;
    }

    public Word getWordById(int id){
        return wordDAO.getWordById(id).orElseThrow(() -> new ResourceNotFound(String.format("No word found with id %s", id)));
    }

    public int addWord(Word word) {
        return wordDAO.addWord(word);
    }

    public ArrayList<Integer> getRelatedWordIds(String word) {
        Word targetWord = getWordByTextValue(word);
        return wordDAO.getRelatedWordIds(targetWord.getId()).orElseThrow(
                () -> new ResourceNotFound(String.format("Could not find the word \'%s\'", word))
        );
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

    public Word getWordByTextValue(String word) {
        return wordDAO.getWordByTextValue(word).orElseThrow(
                () -> new ResourceNotFound(String.format("Could not find the word \'%s\'", word)) );
    }

    public Word getRandomRelatedWord(String word) {
        ArrayList<Integer> relatedIds = getRelatedWordIds(word);
        int targetId = relatedIds.get((int) (Math.random()*relatedIds.size()));
        return getWordById(targetId);
    }

    public ArrayList<Word> getRelatedWordsSamePOS(String word) {
        Word targetWord = getWordByTextValue(word);
        List<Word> wordList = getRelatedWords(word).stream().filter(
                (w) -> w.getPartOfSpeech() == targetWord.getPartOfSpeech()).collect(Collectors.toList());
        ArrayList<Word> wordArrayList = new ArrayList<Word>(wordList);
        return wordArrayList;
    }
}
