package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;

import edu.rosehulman.rafinder.ConfigKeys;
import edu.rosehulman.rafinder.model.SearchResultItem;

public class Resident implements SearchResultItem {
    private String name;

    public Resident() {
    }

    public Resident(String name) {
        this.name = name;
    }

    public Resident(DataSnapshot ds) {
        name = ds.child(ConfigKeys.employeeName).getValue(String.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
