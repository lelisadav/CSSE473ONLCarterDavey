package edu.rosehulman.rafinder.model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.rosehulman.rafinder.controller.reslife.HallRosterFragment;
import edu.rosehulman.rafinder.model.person.Resident;

/**
 * An entry for one room of the {@link HallRosterFragment}.
 */
public class RoomEntry {
    private String firebaseURL;

    private List<Resident> residents;
    private String hallName;
    private String roomNumber;
    public RoomEntry(){

    }

    public RoomEntry(String firebaseURL) {
        this.firebaseURL=firebaseURL;
        Firebase firebase=new Firebase(firebaseURL);
        firebase.addListenerForSingleValueEvent(new RoomEntryListener(this));

    }

    public RoomEntry(String hallName, String roomNumber, Resident... residents) {
        this.hallName = hallName;
        this.roomNumber = roomNumber;
        this.residents = Arrays.asList(residents);
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hall) {
        this.hallName = hall;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Resident[] getResidents() {
        return residents.toArray(new Resident[residents.size()]);
    }

    public void setResidents(Resident[] residents) {
        this.residents = Arrays.asList(residents);
    }

    public static class Lobby extends RoomEntry {
        public Lobby() {
            super.roomNumber = "Lobby";
        }
    }
    public class RoomEntryListener implements ValueEventListener{

        private RoomEntry roomEntry;
        public RoomEntryListener(RoomEntry roomEntry){
            this.roomEntry=roomEntry;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            roomEntry.setRoomNumber(dataSnapshot.getKey());
            for (DataSnapshot child: dataSnapshot.getChildren()){
                Resident res=new Resident(child.getRef().getPath().toString());
                roomEntry.residents.add(res);
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
