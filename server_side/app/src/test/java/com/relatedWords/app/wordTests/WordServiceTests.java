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
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void gettingValidWordByIdReturnsCorrespondingWord(){
        Word word1 = new Word(1,"word", "noun", "word".length());

        when(mockWordRepository.getWordById(1)).thenReturn(Optional.of(word1));

        Word expected = mockWordRepository.getWordById(1).get();
        Word actual = underTest.getWordById(1);

        assertEquals(actual, expected);
    }

    //     get word by id - failure
    @Test
    void gettingInvalidWordByIdThrowsCorrectError(){
        int id = 0;

        assertThatThrownBy(() -> underTest.getWordById(id))
                .isInstanceOf(ResourceNotFound.class).hasMessageContaining(String.format("No word found with id %s", id));
    }

    //     get related words - success
    @Test
    void gettingRelatedWordsForValidWordReturnsArrayListReturnedByDAO(){
        Word word1 = new Word(1,"word", "noun", "word".length());
        Word word2 = new Word(2,"sentence", "noun", "sentence".length());
        Word word3 = new Word(3,"text", "noun", "text".length());

        ArrayList<Word> mockWords = new ArrayList<>(Arrays.asList(word2, word3));
        List<Integer> relatedIds = mockWords.stream()
                .map((w)->w.getId()).collect(Collectors.toList());

        when(mockWordRepository.getWordByTextValue("word")).thenReturn(Optional.of(word1));
        when(mockWordRepository.getRelatedWordIds(word1.getId())).thenReturn(Optional.of((ArrayList<Integer>) relatedIds));
        when(mockWordRepository.getWordById(word2.getId())).thenReturn(Optional.of(word2));
        when(mockWordRepository.getWordById(word3.getId())).thenReturn(Optional.of(word3));

        ArrayList<Word> expected = (ArrayList<Word>) relatedIds.stream().map(
                (i) -> mockWordRepository.getWordById(i).get()).collect(Collectors.toList());
        ArrayList<Word> actual = underTest.getRelatedWords(word1.getValue());

        assertEquals(actual, expected);
    }

    //     get related words - failure
    @Test
    void gettingRelatedWordsForInvalidWordThrowsCorrectError(){
        Word word1 = new Word(1,"word", "noun", "word".length());
        Word word2 = new Word(2,"sentence", "noun", "sentence".length());
        Word word3 = new Word(3,"text", "noun", "text".length());

        ArrayList<Word> mockWords = new ArrayList<>(Arrays.asList(word2, word3));
        List<Integer> relatedIds = mockWords.stream()
                .map((w)->w.getId()).collect(Collectors.toList());

        when(mockWordRepository.getWordByTextValue("word")).thenReturn(Optional.of(word1));
        when(mockWordRepository.getRelatedWordIds(word1.getId())).thenReturn(Optional.of((ArrayList<Integer>) relatedIds));
        when(mockWordRepository.getWordById(word2.getId())).thenReturn(Optional.of(word2));
        when(mockWordRepository.getWordById(word3.getId())).thenReturn(Optional.of(word3));

        String word = "worddd";

        assertThatThrownBy(() -> underTest.getRelatedWords(word))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessageContaining(String.format("Could not find the word \'%s\'", word));
    }

    //     get random related word - success
    @Test
    void gettingRandomRelatedWordForValidWordReturnsOneOfTheRelatedWords(){
        Word word1 = new Word(1,"word", "noun", "word".length());
        Word word2 = new Word(2,"sentence", "noun", "sentence".length());
        Word word3 = new Word(3,"text", "noun", "text".length());

        ArrayList<Word> mockWords = new ArrayList<>(Arrays.asList(word2, word3));
        List<Integer> relatedIds = mockWords.stream()
                .map((w)->w.getId()).collect(Collectors.toList());

        when(mockWordRepository.getWordByTextValue("word")).thenReturn(Optional.of(word1));
        when(mockWordRepository.getRelatedWordIds(word1.getId())).thenReturn(Optional.of((ArrayList<Integer>) relatedIds));
        when(mockWordRepository.getWordById(word2.getId())).thenReturn(Optional.of(word2));
        when(mockWordRepository.getWordById(word3.getId())).thenReturn(Optional.of(word3));

        ArrayList<Word> allRelated = (ArrayList<Word>) relatedIds.stream().map(
                (i) -> mockWordRepository.getWordById(i).get()).collect(Collectors.toList());
        Word actual = underTest.getRandomRelatedWord(word1.getValue());

        assertTrue(allRelated.stream().anyMatch(item -> actual.equals(item)));
    }

    //     get random related word - failure
    @Test
    void gettingRandomRelatedWordForInvalidWordThrowsException(){
        Word word1 = new Word(1,"word", "noun", "word".length());
        Word word2 = new Word(2,"sentence", "noun", "sentence".length());
        Word word3 = new Word(3,"text", "noun", "text".length());

        ArrayList<Word> mockWords = new ArrayList<>(Arrays.asList(word2, word3));
        List<Integer> relatedIds = mockWords.stream()
                .map((w)->w.getId()).collect(Collectors.toList());

        when(mockWordRepository.getWordByTextValue("word")).thenReturn(Optional.of(word1));
        when(mockWordRepository.getRelatedWordIds(word1.getId())).thenReturn(Optional.of((ArrayList<Integer>) relatedIds));
        when(mockWordRepository.getWordById(word2.getId())).thenReturn(Optional.of(word2));
        when(mockWordRepository.getWordById(word3.getId())).thenReturn(Optional.of(word3));

        String invalidWord = "NaN";

        assertThatThrownBy(() -> underTest.getRandomRelatedWord(invalidWord))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessageContaining(String.format("Could not find the word \'%s\'", invalidWord));
    }

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

        when(mockWordRepository.getRelatedWordIds(word1.getId())).thenReturn(Optional.of((ArrayList<Integer>) relatedIds));
        ArrayList<Integer> expected = mockWordRepository.getRelatedWordIds(1).get();

        ArrayList<Integer> actual = underTest.getRelatedWordIds(word1.getValue());

        assertEquals(actual, expected);
    }

    //     get related word ids - failure
    @Test
    void gettingRelatedWordIdsForInvalidWordReturnsCorrectException(){
        String invalidWord = "NaN";

        assertThatThrownBy(() -> underTest.getRelatedWordIds(invalidWord))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessageContaining(String.format("Could not find the word \'%s\'", invalidWord) );
    }

    //     get related word same pos - success
    @Test
    void gettingWordWithSamePOSReturnsWordWithSamePOS(){
        Word word1 = new Word(1,"word", "noun", "word".length());
        Word word2 = new Word(2,"sentence", "noun", "sentence".length());
        Word word3 = new Word(3,"text", "verb", "text".length());

        Word targetWord = new Word(4,"message", "verb", "message".length());

        ArrayList<Word> mockWords = new ArrayList<>(Arrays.asList(word2, word3));
        List<Integer> relatedIds = mockWords.stream()
                .map((w)->w.getId()).collect(Collectors.toList());

        when(mockWordRepository.getWordByTextValue("message")).thenReturn(Optional.of(targetWord));
        when(mockWordRepository.getRelatedWordIds(targetWord.getId())).thenReturn(Optional.of((ArrayList<Integer>) relatedIds));
        when(mockWordRepository.getWordById(1)).thenReturn(Optional.of(word1));
        when(mockWordRepository.getWordById(2)).thenReturn(Optional.of(word2));
        when(mockWordRepository.getWordById(3)).thenReturn(Optional.of(word3));

        ArrayList<Word> expected = new ArrayList<>(List.of(word3));

        ArrayList<Word> actual = underTest.getRelatedWordsSamePOS(targetWord.getValue());

        assertEquals(actual, expected);
    }

    //     get related word same pos - failure
    @Test
    void gettingInvalidWordWithSamePOSThrowsCorrectError(){
        Word word1 = new Word(1,"word", "noun", "word".length());
        Word word2 = new Word(2,"sentence", "noun", "sentence".length());
        Word word3 = new Word(3,"text", "noun", "text".length());

        Word targetWord = new Word(4,"message", "verb", "message".length());

        ArrayList<Word> mockWords = new ArrayList<>(Arrays.asList(word2, word3));
        List<Integer> relatedIds = mockWords.stream()
                .map((w)->w.getId()).collect(Collectors.toList());

        when(mockWordRepository.getWordByTextValue("message")).thenReturn(Optional.of(targetWord));
        when(mockWordRepository.getRelatedWordIds(targetWord.getId())).thenReturn(Optional.of((ArrayList<Integer>) relatedIds));
        when(mockWordRepository.getWordById(1)).thenReturn(Optional.of(word1));
        when(mockWordRepository.getWordById(2)).thenReturn(Optional.of(word2));
        when(mockWordRepository.getWordById(3)).thenReturn(Optional.of(word3));

        ArrayList<Word> actual = underTest.getRelatedWordsSamePOS(targetWord.getValue());

        ArrayList<Word> expected = new ArrayList<>();
        assertEquals(actual, expected);
    }

    //     add word - success
    @Test
    void successFullyAddingWordReturnsNumberOfRowsAddedAsReturnedByDAO(){
        Word wordToAdd = new Word(1,"word", "noun", "word".length());
        when(mockWordRepository.addWord(wordToAdd)).thenReturn(1);

        int actual = underTest.addWord(wordToAdd);
        int expected = 1;

        assertEquals(actual, expected);
    }
}
