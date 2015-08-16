package edu.rosehulman.rafinder.model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import org.joda.time.LocalDate;

import edu.rosehulman.rafinder.ConfigKeys;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;

/**
 * A single item in the Duty Roster.
 */
public class DutyRosterItem {
    // DutyRosterItem Firebase keys
    private static final String fridayKey = "friday";
    private static final String saturdayKey = "saturday";

    public interface DutyRosterCallbacks {

    }

    private Employee friDuty;
    private Employee satDuty;
    private LocalDate friday;

    public DutyRosterItem(DataSnapshot ds) {
        friday = LocalDate.parse(ds.getKey(), ConfigKeys.formatter);
        for (DataSnapshot child : ds.getChildren()) {
            String firebaseUrl = ConfigKeys.Employees +
                                 "/" + ConfigKeys.ResidentAssistants +
                                 "/" + child.getValue(String.class);
            if (child.getKey().equals(fridayKey)) {
                friDuty = new ResidentAssistant(new Firebase(firebaseUrl));
            } else if (child.getKey().equals(saturdayKey)) {
                satDuty = new ResidentAssistant(new Firebase(firebaseUrl));
            }
        }
    }

//    public DutyRosterItem(String fireBaseUrl) {
//        this.firebaseURL = fireBaseUrl;
//        Firebase firebase = new Firebase(firebaseURL);
//        firebase.addListenerForSingleValueEvent(new DutyRosterListener(this));
////        this.friDuty = getFridayWorker();
////        this.satDuty = getSaturdayWorker();
//    }
//
//    public DutyRosterItem(LocalDate friday, Employee friDuty, Employee satDuty) {
//        this.friday = friday;
//        this.friDuty = friDuty;
//        this.satDuty = satDuty;
//    }

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

//    public class DutyRosterListener implements ValueEventListener {
//        private DutyRosterItem item;
//
//        public DutyRosterListener(DutyRosterItem item) {
//            this.item = item;
//        }
//
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            for (DataSnapshot child : dataSnapshot.getChildren()) {
//                if (child.getKey().equals(ConfigKeys.friday)) {
//                    friDuty = new ResidentAssistant(ConfigKeys.FIREBASE_ROOT_URL +
//                                                    "/"+ConfigKeys.Employees +"/"+ConfigKeys.ResidentAssistants+"/" +
//                                                    child.getValue(String.class));
//                } else if (child.getKey().equals(ConfigKeys.saturday)) {
//                    satDuty = new ResidentAssistant(ConfigKeys.FIREBASE_ROOT_URL +
//                                                    "/"+ConfigKeys.Employees +"/"+ConfigKeys.ResidentAssistants+"/" +
//                                                    child.getValue(String.class));
//                }
//            }
//
//        }
//
//        @Override
//        public void onCancelled(FirebaseError firebaseError) {
//
//        }
//    }

}



