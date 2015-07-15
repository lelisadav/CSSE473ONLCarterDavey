package edu.rosehulman.rafinder.model;

import java.util.ArrayList;
import java.util.List;

public class Floor {

    private int lobbyAfterRoomNumber;

    private int number;
    private List<RoomEntry> rooms;

    public List<RoomEntry> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomEntry> rooms) {
        this.rooms = rooms;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLobbyAfterRoomNumber() {
        return lobbyAfterRoomNumber;
    }

    public void setLobbyAfterRoomNumber(int lobbyAfterRoomNumber) {
        this.lobbyAfterRoomNumber = lobbyAfterRoomNumber;
    }

    private void populateRooms() {
        //do Firebase calls here for rooms;
        List<RoomEntry> roomList = new ArrayList<RoomEntry>();
        boolean dummyIsLobby = true;
        //find position of lobby
        if (dummyIsLobby) {
            roomList.add(new RoomEntry.Lobby());
            //add two to play nice with two column grid view.
            roomList.add(new RoomEntry.Lobby());
        }
    }


}
