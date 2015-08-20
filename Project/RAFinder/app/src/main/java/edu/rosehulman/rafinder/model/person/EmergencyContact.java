package edu.rosehulman.rafinder.model.person;

import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Arrays;

public class EmergencyContact implements Comparable<EmergencyContact> {
    // EmergencyContact Firebase keys
    private static final String ecEmail = "Email";
    private static final String ecPhone = "Phone";

    private String name;
    private String email;
    private String phone;
    private Position position;
    private Priority priority;

    public enum Priority {
        ON_DUTY,
        MY_RA,
        STAFF
    }

    public EmergencyContact(String firebaseURL) {
        Firebase firebase = new Firebase(firebaseURL);

        firebase.addListenerForSingleValueEvent(new EmergencyContactListener(this));
    }

    public EmergencyContact(Employee employee, boolean isOnDuty) {
        priority = (isOnDuty) ? Priority.ON_DUTY : Priority.MY_RA;
        position = employee.getPosition();
        name = employee.getName();
        email = employee.getEmail();
        phone = employee.getPhoneNumber();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    //-1 this < another
    //0 ==
    //1 this > another
    @Override
    public int compareTo(@NonNull EmergencyContact another) {
        if (priority.ordinal() > another.getPriority().ordinal()) {
            return -1;
        } else if (priority.ordinal() < another.getPriority().ordinal()) {
            return 1;
        }
        return 0;
    }

    private class EmergencyContactListener implements ValueEventListener {
        private final EmergencyContact contact;

        public EmergencyContactListener(EmergencyContact emergencyContact) {
            contact = emergencyContact;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            contact.setName(dataSnapshot.getKey());
            contact.setPriority(Priority.STAFF);
            contact.setPosition(Position.ADMIN);
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                if (child.getKey().equals(ecEmail)) {
                    contact.setEmail(child.getValue(String.class));
                } else if (child.getKey().equals(ecPhone)) {
                    contact.setPhone(child.getValue(String.class));
                }
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            // ignored
        }
    }

    @Override
    public String toString() {
        return Arrays.asList(name,
                email,
                phone,
                position,
                priority
        ).toString();
    }
}
