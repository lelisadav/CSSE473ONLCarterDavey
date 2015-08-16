package edu.rosehulman.rafinder.model;

import com.firebase.client.DataSnapshot;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.HashMap;

import edu.rosehulman.rafinder.ConfigKeys;
import edu.rosehulman.rafinder.model.person.Employee;

public class DutyRoster {
    HashMap<LocalDate, DutyRosterItem> roster;
    private LocalDate startDate;

    public DutyRoster(DataSnapshot ds, LocalDate startDate) {
        this.startDate = startDate;
        roster = new HashMap<>();
        for (DataSnapshot child : ds.getChildren()) {
            LocalDate rosterDate = LocalDate.parse(child.getKey(), ConfigKeys.formatter);
            if (!rosterDate.isBefore(getStartDate())) {
                DutyRosterItem item = new DutyRosterItem(child);
                roster.put(rosterDate, item);
            }
        }
    }

    //    public DutyRoster(String firebaseURL, LocalDate startDate){
//        Firebase firebase=new Firebase(firebaseURL);
//        this.startDate=startDate;
//        firebase.addListenerForSingleValueEvent(new DutyRosterListener(this));
//    }
    public Employee getOnDutyNow() {
        LocalDate now = LocalDate.now();
        if (now.getDayOfWeek() != DateTimeConstants.FRIDAY && now.getDayOfWeek() != DateTimeConstants.SATURDAY) {
            return null;
        } else {

            if (now.getDayOfWeek() == DateTimeConstants.SATURDAY) {
                now = now.minusDays(1);
            }
            DutyRosterItem item = roster.get(now);
            if (now.getDayOfWeek() == DateTimeConstants.FRIDAY) {
                return item.getFriDuty();
            } else {
                return item.getSatDuty();
            }
        }
    }

    public HashMap<LocalDate, DutyRosterItem> getRoster() {
        return roster;
    }

    public void setRoster(HashMap<LocalDate, DutyRosterItem> roster) {
        this.roster = roster;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

//    private class DutyRosterListener implements ValueEventListener{
//
//        DutyRoster roster;
//        public DutyRosterListener(DutyRoster roster){
//            this.roster=roster;
//        }
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            for (DataSnapshot child: dataSnapshot.getChildren()){
//                LocalDate rosterDate=LocalDate.parse(child.getKey(), ConfigKeys.formatter);
//                if (!rosterDate.isBefore(roster.getStartDate())){
//                    String fireBaseUrl = ConfigKeys.FIREBASE_ROOT_URL + child.getRef().getPath().toString();
//                    DutyRosterItem item=new DutyRosterItem(fireBaseUrl);
//                    roster.getRoster().put(rosterDate, item);
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
