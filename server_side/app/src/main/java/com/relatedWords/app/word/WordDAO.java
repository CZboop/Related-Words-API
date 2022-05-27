package com.relatedWords.app.word;

import java.util.ArrayList;
import java.util.Optional;

public interface WordDAO {
    Optional<Word> getWordById(int id);

    int addWord(Word word);

    Optional<ArrayList<Integer>> getRelatedWordIds(int id);

    Optional<Word> getWordByTextValue(String word);

    Optional<ArrayList<Word>> getWordsSameLen(String word);
}
