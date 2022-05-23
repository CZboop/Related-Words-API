package com.relatedWords.app.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WordDataAccessService implements WordDAO {
    private JdbcTemplate jdbcTemplate;
    private WordRowMapper wordRowMapper;

    @Autowired
    public WordDataAccessService(JdbcTemplate jdbcTemplate, WordRowMapper wordRowMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.wordRowMapper = wordRowMapper;
    }

    @Override
    public Optional<Word> getWordById(int id) {
        String sql = """
                SELECT * FROM words WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, wordRowMapper, id).stream().findFirst();
    }

    @Override
    public void addWord(Word word){
        String sql = """
                INSERT INTO words (text_value, part_of_speech, length) VALUES (?, ?, ?);
                """;
        jdbcTemplate.update(sql, word.getValue(), word.getPartOfSpeech().name(), word.getLength());
    }
}
