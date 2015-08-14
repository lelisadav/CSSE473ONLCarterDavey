package edu.rosehulman.rafinder.model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.rafinder.MainActivity;

/**
 * An entire floor of {@link RoomEntry} objects.
 */
public class Floor {

    private int lobbyAfterRoomNumber;

    private String number;
    private List<RoomEntry> rooms;

    public Floor(String url){
        rooms=new ArrayList<>();
        Firebase firebase=new Firebase(url);
        firebase.addListenerForSingleValueEvent(new FloorListener(this));
    }

    public Floor(String number, List<RoomEntry> rooms, int lobbyAfterRoomNumber) {
        this.number = number;
        this.rooms = rooms;
        this.lobbyAfterRoomNumber = lobbyAfterRoomNumber;
    }

    public List<RoomEntry> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomEntry> rooms) {
        this.rooms = rooms;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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
    private class FloorListener implements ValueEventListener{
        Floor floor;

        public FloorListener(Floor f){
            floor=f;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot room: dataSnapshot.getChildren()){
                String firebaseURL = MainActivity.FIREBASE_ROOT_URL + room.getRef().getPath().toString();
                RoomEntry roomEntry=new RoomEntry(firebaseURL);
                floor.getRooms().add(roomEntry);
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }


}
