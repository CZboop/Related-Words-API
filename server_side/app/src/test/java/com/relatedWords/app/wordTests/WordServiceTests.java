package com.relatedWords.app.wordTests;

import com.relatedWords.app.exception.ResourceNotFound;
import com.relatedWords.app.word.POS;
import com.relatedWords.app.word.Word;
import com.relatedWords.app.word.WordDataAccessService;
import com.relatedWords.app.word.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WordServiceTests {
    private WordDataAccessService mockWordRepository;
    private WordService underTest;

    @BeforeEach
    void setUp(){
        mockWordRepository = mock(WordDataAccessService.class);
        underTest = new WordService(mockWordRepository);
    }

    //     get word by id - success
    @Test
    void gettingValidWordByIdReturnsCorrespondingWord(){}

    //     get word by id - failure
    @Test
    void gettingInvalidWordByIdThrowsCorrectError(){}

    //     get related words - success
    @Test
    void gettingRelatedWordsForValidWordReturnsArrayListReturnedByDAO(){}

    //     get related words - failure
    @Test
    void gettingRelatedWordsForInvalidWordThrowsCorrectError(){}

    //     get random related word - success
    @Test
    void gettingRandomRelatedWordForValidWordReturnsOneOfTheRelatedWords(){}

    //     get random related word - failure
    @Test
    void gettingRandomRelatedWordForInvalidWordThrowsException(){}

    //     get word by text value - success
    @Test
    void gettingValidWordReturnsCorrespondingWord(){
        Word word1 = new Word(1,"word", "noun", "word".length());

        when(mockWordRepository.getWordByTextValue("word")).thenReturn(Optional.of(word1));

        Word expected = mockWordRepository.getWordByTextValue("word").get();
        Word actual = underTest.getWordByTextValue(word1.getValue());

        assertEquals(actual, expected);
    }

    //     get word by text value - failure
    @Test
    void gettingInvalidWordThrowsCorrectException(){
        Word word1 = new Word(1,"word", "noun", "word".length());
        Word word2 = new Word(2,"sentence", "noun", "sentence".length());
        Word word3 = new Word(3,"text", "noun", "text".length());

        String word = "wordd";

        when(mockWordRepository.getWordByTextValue(word)).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> underTest.getWordByTextValue(word))
                .isInstanceOf(ResourceNotFound.class).hasMessageContaining(String.format("Could not find the word \'%s\'", word));
    }

    //     get related word ids - success
    @Test
    void gettingRelatedWordIdsForValidWordReturnsSameArraylistOfWordIdsAsDAO(){
        Word word1 = new Word(1,"word", "noun", "word".length());
        Word word2 = new Word(2,"sentence", "noun", "sentence".length());
        Word word3 = new Word(3,"text", "noun", "text".length());

        ArrayList<Word> mockWords = new ArrayList<>(Arrays.asList(word2, word3));
        List<Integer> relatedIds = mockWords.stream()
                .map((w)->w.getId()).collect(Collectors.toList());

        when(mockWordRepository.getWordByTextValue("word")).thenReturn(Optional.of(word1));

        when(mockWordRepository.getRelatedWordIds(word1.getId())).thenReturn((ArrayList<Integer>) relatedIds);
        ArrayList<Integer> expected = mockWordRepository.getRelatedWordIds(1);

        ArrayList<Integer> actual = underTest.getRelatedWordIds(word1.getValue());

        assertEquals(actual, expected);
    }

    //     get related word ids - failure
    @Test
    void gettingRelatedWordIdsForInvalidWordReturnsCorrectException(){

    }

    //     get related word same pos - success
    @Test
    void gettingWordWithSamePOSReturnsWordWithSamePOS(){

    }
    //     get related word same pos - failure
    @Test
    void gettingInvalidWordWithSamePOSThrowsCorrectError(){}

    //     add word - success
    @Test
    void successFullyAddingWordReturnsNumberOfRowsAdded(){

    }

    //    add word - failure
    @Test
    void failureToAddWordReturnsNumberOfRowsAdded(){

    }




}
