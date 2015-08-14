package edu.rosehulman.rafinder.model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.joda.time.LocalDate;

import edu.rosehulman.rafinder.MainActivity;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;

/**
 * A single item in the Duty Roster.
 */
public class DutyRosterItem {

    public interface DutyRosterCallbacks {

    }

    private String firebaseURL;
    private Employee friDuty;
    private Employee satDuty;
    private LocalDate friday;

    public DutyRosterItem(String fireBaseUrl) {
        this.firebaseURL = fireBaseUrl;
        Firebase firebase = new Firebase(firebaseURL);
        firebase.addListenerForSingleValueEvent(new DutyRosterListener(this));
//        this.friDuty = getFridayWorker();
//        this.satDuty = getSaturdayWorker();
    }

    public DutyRosterItem(LocalDate friday, Employee friDuty, Employee satDuty) {
        this.friday = friday;
        this.friDuty = friDuty;
        this.satDuty = satDuty;
    }

//    private Employee getFridayWorker() {
//        //use firebase url to pull friday worker
//        return null;
//    }
//
//    private Employee getSaturdayWorker() {
//        //use firebase url to pull saturday worker
//        return null;
//    }

    public Employee getFriDuty() {
        return friDuty;
    }

    public void setFriDuty(Employee friDuty) {
        this.friDuty = friDuty;
    }

    public Employee getSatDuty() {
        return satDuty;
    }

    public void setSatDuty(Employee satDuty) {
        this.satDuty = satDuty;
    }

    public LocalDate getFriday() {
        return friday;
    }

    public void setFriday(LocalDate friday) {
        this.friday = friday;
    }

    public class DutyRosterListener implements ValueEventListener {
        private DutyRosterItem item;
        public DutyRosterListener(DutyRosterItem item){
            this.item=item;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot child : dataSnapshot.getChildren()){
                if (child.getKey().equals("friday")){
                    friDuty=new ResidentAssistant(MainActivity.FIREBASE_ROOT_URL +"/Employees/Resident Assistants/"+child.getValue(String.class));
                }
                else if (child.getKey().equals("saturday")){
                    satDuty=new ResidentAssistant(MainActivity.FIREBASE_ROOT_URL +"/Employees/Resident Assistants/"+child.getValue(String.class));
                }
            }

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

}



