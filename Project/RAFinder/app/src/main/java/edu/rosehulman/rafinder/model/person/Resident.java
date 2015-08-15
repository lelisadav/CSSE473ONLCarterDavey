package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;

import edu.rosehulman.rafinder.model.SearchResultItem;

public class Resident implements SearchResultItem {
    private String name;

    public Resident(String name) {
        if (name.equals("")) return;
        this.name = name;
    }

    public Resident(DataSnapshot ds) {
        name = ds.child("name").getValue(String.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
