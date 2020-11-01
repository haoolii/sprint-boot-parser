package com.fubon.mappingfield.parser;

public class ParserTerm {
    private long id;
    private String key;
    private String value;

    public ParserTerm(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
