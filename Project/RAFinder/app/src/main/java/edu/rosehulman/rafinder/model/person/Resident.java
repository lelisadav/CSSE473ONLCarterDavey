package edu.rosehulman.rafinder.model.person;

import edu.rosehulman.rafinder.model.SearchResultItem;

public abstract class Resident implements SearchResultItem {
    private String name;
    private String room;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom(){
        return room;
    }
    public void setRoom(String room){
        this.room=room;
    }


}
