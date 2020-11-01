package com.fubon.mappingfield.parser;

import java.util.List;

public class MappingField {
    private long id;
    private String name;
    private List<ParserTerm> items;

    public MappingField(long id, String name, List<ParserTerm> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItems(List<ParserTerm> items) {
        this.items = items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public List<ParserTerm> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }
}
