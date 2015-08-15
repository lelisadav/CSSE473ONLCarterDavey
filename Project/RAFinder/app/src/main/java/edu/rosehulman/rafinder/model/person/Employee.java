package edu.rosehulman.rafinder.model.person;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Arrays;

import edu.rosehulman.rafinder.MainActivity;

/**
 * Any Residence Life employee.
 */
public class Employee extends Resident {

    private String email;
    private int floor;
    private String hall;
    private String phoneNumber;
    private int room;
    private String status;
    private String statusDetail;
    private Firebase firebase;

    public Employee(DataSnapshot ds) {
        this(
            ds.child("name").getValue(String.class),
            ds.child("email").getValue(String.class),
            ds.child("floor").getValue(int.class),
            ds.child("hall").getValue(String.class),
            ds.child("phoneNumber").getValue(String.class),
            ds.child("room").getValue(int.class),
            ds.child("status").getValue(String.class),
            ds.child("statusDetail").getValue(String.class)
        );
        firebase = new Firebase(MainActivity.FIREBASE_ROOT_URL + ds.getRef().getPath().toString());
        firebase.addChildEventListener(new ChildrenListener());
    }

    public Employee(String firebaseUrl) {
        super("");
        firebase = new Firebase(MainActivity.FIREBASE_ROOT_URL + firebaseUrl);
        firebase.addChildEventListener(new ChildrenListener());
    }

    public Employee(String name,
                    String email,
                    int floor,
                    String hall,
                    String phoneNumber,
                    int room,
                    String status,
                    String statusDetail) {
        super(name);
        this.email = email;
        this.floor = floor;
        this.hall = hall;
        this.phoneNumber = phoneNumber;
        this.room = room;
        this.status = status;
        this.statusDetail = statusDetail;
    }

    public String getEmail() {
        return email;
    }

    public Firebase getFirebase() {
        return firebase;
    }

    public void setFirebase(Firebase firebase) {
        this.firebase = firebase;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    class ChildrenListener implements ChildEventListener {
        public void onCancelled(FirebaseError arg0) {
            // ignored
        }

        public void onChildAdded(DataSnapshot arg0, String arg1) {
            // ignored
        }

        public void onChildChanged(DataSnapshot arg0, String arg1) {
            switch (arg0.getKey()) {
            case "email":
                setEmail(arg0.getValue(String.class));
                break;
            case "floor":
                setFloor(arg0.getValue(int.class));
                break;
            case "hall":
                setHall(arg0.getValue(String.class));
                break;
            case "phoneNumber":
                setPhoneNumber(arg0.getValue(String.class));
                break;
            case "room":
                setRoom(arg0.getValue(int.class));
                break;
            case "status":
                setStatus(arg0.getValue(String.class));
                break;
            case "statusDetail":
                setStatusDetail(arg0.getValue(String.class));
                break;
            case "name":
                setName(arg0.getValue(String.class));
                break;
            }
        }

        public void onChildMoved(DataSnapshot arg0, String arg1) {
            // ignored
        }

        public void onChildRemoved(DataSnapshot arg0) {
            // ignored
        }
    }

    @Override
    public String toString() {
        return Arrays.asList(
                getName(),
                email,
                floor,
                hall,
                phoneNumber,
                room,
                status,
                statusDetail).toString();
    }
}
