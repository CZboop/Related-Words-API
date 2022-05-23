package com.relatedWords.app.word;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Word {
    private String value;
    private POS partOfSpeech;
    private int length;

    public Word(String value, POS partOfSpeech) {
        this.value = value;
        this.partOfSpeech = partOfSpeech;
        this.length = value.length();
    }
}
