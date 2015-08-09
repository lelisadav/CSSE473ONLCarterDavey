package edu.rosehulman.rafinder.model.person;

import edu.rosehulman.rafinder.model.SearchResultItem;

public class Resident implements SearchResultItem {
    private String name;
    private String firebaseURL;

    public Resident(String fireBaseUrl){
        this.firebaseURL=fireBaseUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
