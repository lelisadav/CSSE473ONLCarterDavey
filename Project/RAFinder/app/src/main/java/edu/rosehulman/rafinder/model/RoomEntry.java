package edu.rosehulman.rafinder.model;

import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.rosehulman.rafinder.controller.reslife.HallRosterFragment;
import edu.rosehulman.rafinder.model.person.GraduateAssistant;
import edu.rosehulman.rafinder.model.person.Resident;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;
import edu.rosehulman.rafinder.model.person.SophomoreAdvisor;

/**
 * An entry for one room of the {@link HallRosterFragment}.
 */
public class RoomEntry {
    private static final String ResidentAssistant = "Resident Assistant";
    private static final String SophomoreAdvisor = "Sophomore Advisor";
    private static final String GraduateAssistant = "Graduate Assistant";
    private static final String Resident = "Resident";

    private List<Resident> residents;
    private String hallName;
    private String roomNumber;

    public RoomEntry(DataSnapshot ds, String hallName) {
        this.hallName = hallName;
        roomNumber = ds.getKey();
        residents = new ArrayList<>();
        for (DataSnapshot child : ds.getChildren()) {
            switch (child.getValue(String.class)) {
            case Resident:
                residents.add(new Resident(child.getKey()));
                break;
            case SophomoreAdvisor:
                residents.add(new SophomoreAdvisor(child.getKey()));
                break;
            case ResidentAssistant:
                residents.add(new ResidentAssistant(child.getKey()));
                break;
            case GraduateAssistant:
                residents.add(new GraduateAssistant(child.getKey()));
                break;
            default:
                break;
            }
        }

    }

    RoomEntry() {
        residents = new ArrayList<>();
    }

//    public RoomEntry(String firebaseURL) {
//        this();
//        this.firebaseURL = firebaseURL;
//        Firebase firebase = new Firebase(firebaseURL);
//        firebase.addListenerForSingleValueEvent(new RoomEntryListener(this));
//    }
//
//    public RoomEntry(String hallName, String roomNumber, Resident... residents) {
//        this.hallName = hallName;
//        this.roomNumber = roomNumber;
//        this.residents = Arrays.asList(residents);
//    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hall) {
        hallName = hall;
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

//    public class RoomEntryListener implements ValueEventListener {
//
//        private RoomEntry roomEntry;
//
//        public RoomEntryListener(RoomEntry roomEntry) {
//            this.roomEntry = roomEntry;
//        }
//
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            roomEntry.setRoomNumber(dataSnapshot.getKey());
//            for (DataSnapshot child : dataSnapshot.getChildren()) {
//                String fireBaseUrl = ConfigKeys.FIREBASE_ROOT_URL + child.getRef().getPath().toString();
//                Resident res = new Resident(fireBaseUrl);
//                roomEntry.residents.add(res);
//            }
//        }
//
//        @Override
//        public void onCancelled(FirebaseError firebaseError) {
//
//        }
//    }
}
