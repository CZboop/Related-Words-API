package com.relatedWords.app.word;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Word {
    private int id;
    private String value;
    private POS partOfSpeech;
    private int length;

    public Word(int id, String value, String partOfSpeech, int length) {
        this.id = id;
        this.value = value;
        this.partOfSpeech = POS.valueOf(partOfSpeech.toUpperCase());
        this.length = value.length();
    }
}
