package edu.rosehulman.rafinder;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.List;

import edu.rosehulman.rafinder.model.DutyRoster;
import edu.rosehulman.rafinder.model.person.Employee;

public class DutyRosterLoader {
    private static final String DutyRosters = "DutyRosters";
    private final List<Employee> ras;
    private DutyRoster roster;
    private final DutyRosterLoaderListener callbacks;
    private LocalDate date;

    public DutyRosterLoader(String url, String hallName, DutyRosterLoaderListener cb, List<Employee> ras) {
        callbacks = cb;
        this.ras = ras;
        Firebase firebase = new Firebase(url + "/" + DutyRosters + "/" + hallName);
        Log.d(ConfigKeys.LOG_TAG, "Loading Duty Roster data...");
        firebase.addListenerForSingleValueEvent(new DutyRosterValueEventListener());
    }

    private LocalDate modifyDate(LocalDate dt) {
        if (dt.getDayOfWeek() == DateTimeConstants.FRIDAY) {
            dt = dt.minusDays(1);
        } else if (dt.getDayOfWeek() == DateTimeConstants.SATURDAY) {
            dt = dt.minusDays(2);
        } else if (dt.getDayOfWeek() == DateTimeConstants.SUNDAY) {
            dt = dt.minusDays(3);
        }
        date = dt;
        return dt;

    }

    public LocalDate getDate() {
        return date;
    }

    public DutyRoster getDutyRoster() {
        return roster;
    }

    public interface DutyRosterLoaderListener {
        public void onDutyRosterLoadingComplete();
    }

    private class DutyRosterValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            roster = new DutyRoster(dataSnapshot, modifyDate(LocalDate.now()), ras);
            Log.d(ConfigKeys.LOG_TAG, "Finished loading Duty Roster data.");
            callbacks.onDutyRosterLoadingComplete();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
