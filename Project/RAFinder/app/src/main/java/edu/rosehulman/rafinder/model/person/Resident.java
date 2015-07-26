package edu.rosehulman.rafinder.model.person;

import edu.rosehulman.rafinder.model.SearchResultItem;

public class Resident implements SearchResultItem {
    private String name;
    private String room;
    private String hall;

    public Resident(String name, String room, String hall){
        this.name=name;
        this.room=room;
        this.hall=hall;
    }

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

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }


}
