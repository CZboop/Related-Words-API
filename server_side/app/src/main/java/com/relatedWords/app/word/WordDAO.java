package com.relatedWords.app.word;

import java.util.ArrayList;
import java.util.Optional;

public interface WordDAO {
    Optional<Word> getWordById(int id);

    void addWord(Word word);

    ArrayList<Integer> getRelatedWordIds(int id);

    Optional<Word> getWordByTextValue(String word);
}
