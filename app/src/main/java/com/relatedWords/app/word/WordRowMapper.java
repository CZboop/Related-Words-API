package com.relatedWords.app.word;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WordRowMapper implements RowMapper<Word> {

    @Override
    public Word mapRow(ResultSet rs, int rowNum) throws SQLException, IllegalArgumentException {
        Word word = new Word(
                rs.getInt("id"),
                rs.getString("value"),
                rs.getString("part_of_speech"),
                rs.getInt("length")
        );
        return word;
    }
}
