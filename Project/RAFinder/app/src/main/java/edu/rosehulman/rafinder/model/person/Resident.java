package edu.rosehulman.rafinder.model.person;

import edu.rosehulman.rafinder.model.SearchResultItem;

public class Resident implements SearchResultItem {
    private String name;

    public Resident(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
