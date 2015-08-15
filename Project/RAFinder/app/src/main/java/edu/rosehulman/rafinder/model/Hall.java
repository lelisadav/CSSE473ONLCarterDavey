package edu.rosehulman.rafinder.model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Residence Hall.
 */
public class Hall implements SearchResultItem {
    // HallRoster Firebase keys
    private static final String roster = "Roster";
    private String name;
    private HashMap<String, Floor> floors;
    private Firebase firebase;

    public Hall(DataSnapshot ds) {
        name = ds.getKey();
        floors = new HashMap<>();
        for (DataSnapshot child : ds.child(roster).getChildren()) {
            floors.put(child.getKey(), new Floor(child, name));
        }

    }

//    public Hall(String url){
//        floors=new HashMap<>();
//        firebase=new Firebase(url);
//        firebase.addListenerForSingleValueEvent(new HallListener(this));
//    }

//    public Hall(String name, Floor... floors) {
//        this.name = name;
//        this.floors = floors;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Floor> getFloors() {
        ArrayList<Floor> f = new ArrayList<Floor>();
        f.addAll(floors.values());
        return f;
    }

    public void setFloors(List<Floor> floors) {
        this.floors.clear();
        for (Floor f : floors) {
            this.floors.put(f.getNumber(), f);
        }
    }

    public String[] getFloorNumbers() {
        String[] floorNumbers = new String[floors.size()];
        for (int i = 0; i < floorNumbers.length; i++) {
            floorNumbers[i] = floors.get(i).getNumber();
        }
        return floorNumbers;
    }

    public Floor getFloor(String floorName) {
        return floors.get(floorName);
    }

    public int getFloorCount() {
        return floors.size();
    }

//    private class HallListener implements ValueEventListener{
//        private Hall hall;
//
//        public HallListener(Hall hall){
//            this.hall=hall;
//        }
//
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            for (DataSnapshot floor : dataSnapshot.getChildren()){
//                String url = ConfigKeys.FIREBASE_ROOT_URL + floor.getRef().getPath().toString();
//                Floor flr = new Floor(url);
//                hall.getFloors().add(flr);
//            }
//        }
//
//        @Override
//        public void onCancelled(FirebaseError firebaseError) {
//
//        }
//    }
}
