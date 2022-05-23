package com.relatedWords.app.word;

import java.util.Optional;

public interface WordDAO {
    Optional<Word> getWordById(int id);

    void addWord(Word word);
}
