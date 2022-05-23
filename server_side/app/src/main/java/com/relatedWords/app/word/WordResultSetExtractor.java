package com.relatedWords.app.word;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class WordResultSetExtractor implements ResultSetExtractor {

    @Override
    public ArrayList<Integer> extractData(ResultSet rs) throws SQLException,
            DataAccessException {
        ArrayList<Integer> wordIds = new ArrayList<Integer>();
        while (rs.next()){
            wordIds.add(rs.getInt("word2_id"));
        }
        return wordIds;
    }
}
