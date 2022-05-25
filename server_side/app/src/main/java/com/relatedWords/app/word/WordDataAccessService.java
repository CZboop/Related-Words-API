package com.relatedWords.app.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class WordDataAccessService implements WordDAO {
    private JdbcTemplate jdbcTemplate;
    private WordRowMapper wordRowMapper;
    private ResultSetExtractor relatedWordIdExtractor;

    @Autowired
    public WordDataAccessService(JdbcTemplate jdbcTemplate, WordRowMapper wordRowMapper, ResultSetExtractor relatedWordIdExtractor){
        this.jdbcTemplate = jdbcTemplate;
        this.wordRowMapper = wordRowMapper;
        this.relatedWordIdExtractor = relatedWordIdExtractor;
    }

    @Override
    public Optional<Word> getWordById(int id) {
        String sql = """
                SELECT * FROM words WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, wordRowMapper, id).stream().findFirst();
    }

    @Override
    public int addWord(Word word){
        String sql = """
                INSERT INTO words (text_value, part_of_speech, length) VALUES (?, ?, ?);
                """;
        return jdbcTemplate.update(sql, word.getValue(), word.getPartOfSpeech().name(), word.getLength());
    }

    @Override
    public Optional<Word> getWordByTextValue(String word) {
        String sql = """
                SELECT * FROM words WHERE text_value = ?;
                """;
        return jdbcTemplate.query(sql, wordRowMapper, word).stream().findFirst();
    }

    @Override
    public Optional<ArrayList<Integer>> getRelatedWordIds(int id){
        String sql = """
                SELECT word2_id FROM semantic_similar WHERE word1_id = ?;
                """;
        return Optional.of((ArrayList<Integer>) jdbcTemplate.query(sql, relatedWordIdExtractor, id));

    }
}
