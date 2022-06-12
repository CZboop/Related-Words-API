package com.relatedWords.app.word;

import com.relatedWords.app.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return (ArrayList<Word>) relatedWords.stream().filter(w ->
                        !w.getValue().startsWith(word) && !word.startsWith(w.getValue()))
                .collect(Collectors.toList());
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

    public ArrayList<Word> getWordsSameLen(String word){
        return wordDAO.getWordsSameLen(word).orElseThrow(
                () -> new ResourceNotFound(String.format("Could not find any similar words to \'%s\'", word))
        );
    }

    public HashMap<String, Integer> getWordsNumberOfMatchingLetters(String word) {
        ArrayList<String> possibleWords = (ArrayList<String>) getWordsSameLen(word).stream()
                .map(Word::getValue).collect(Collectors.toList());
        int len = word.length();
        HashMap<String, Integer> letterOverlaps = new HashMap<>();
        for (String comparisonWord : possibleWords){
            int letterMatchCount = 0;
            for (int i = 0; i < len; i++){
                if (word.charAt(i)==comparisonWord.charAt(i)){
                    letterMatchCount += 1;
                }
            }
            letterOverlaps.put(comparisonWord, letterMatchCount);
        }
        HashMap<String, Integer> haveLetterOverlaps = (HashMap<String, Integer>) letterOverlaps.entrySet().stream()
                .filter(a->a.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (haveLetterOverlaps.size() > 0){
            return haveLetterOverlaps;
        }
        throw new ResourceNotFound(String.format("No words with letter overlaps for \'%s\'", word));
    }

    public HashMap<String, Integer> getNWordsNumberOfMatchingLetters(String word, int n) {
        HashMap<String, Integer> allMatches = getWordsNumberOfMatchingLetters(word);
        return (HashMap<String, Integer>) allMatches.entrySet().stream()
                    .limit(n)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public HashMap<String, ArrayList> getWordsPositionOfMatchingLetters(String word) {
        HashMap<String, Integer> allMatches = getWordsNumberOfMatchingLetters(word);
        HashMap<String, ArrayList> matchWPositions = new HashMap<>();
        List<String> allMatchWords = allMatches.keySet().stream().toList();
        for (String comparisonWord: allMatchWords){
            ArrayList<Integer> matchInds = new ArrayList<>();
            for (int i=0 ; i < word.length(); i++){
                if (comparisonWord.charAt(i)==(word.charAt(i))){
                    matchInds.add(i);
                }
            }
            matchWPositions.put(comparisonWord, matchInds);
        }
        return matchWPositions;
    }

    public HashMap<String, ArrayList> getNWordsPositionOfMatchingLetters(String word, int n){
        HashMap<String, ArrayList> allMatches = getWordsPositionOfMatchingLetters(word);
        int mapSize = allMatches.size();
        if (mapSize > 0){
            return (HashMap<String, ArrayList>) allMatches.entrySet().stream()
                    .limit(n)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        else {
            throw new ResourceNotFound(String.format("No matching words for \'%s\'", word));
        }
    }
}
