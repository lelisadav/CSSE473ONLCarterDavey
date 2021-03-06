package edu.rosehulman.rafinder.loader;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.List;

import edu.rosehulman.rafinder.ConfigKeys;
import edu.rosehulman.rafinder.model.DutyRoster;
import edu.rosehulman.rafinder.model.person.Employee;

public class DutyRosterLoader {
    public static final String DutyRosters = "DutyRosters";
    private final List<Employee> ras;
    private final boolean isEdit;
    private DutyRoster roster;
    private final DutyRosterLoaderListener callbacks;
    private LocalDate date;

    public DutyRosterLoader(String hallName, DutyRosterLoaderListener cb, List<Employee> ras, boolean isEdit) {
        callbacks = cb;
        this.ras = ras;
        this.isEdit = isEdit;
        Firebase firebase = new Firebase(ConfigKeys.FIREBASE_ROOT_URL + "/" + DutyRosters + "/" + hallName);
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
        public void onDutyRosterLoadingComplete(boolean isEdit);
    }

    private class DutyRosterValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            roster = new DutyRoster(dataSnapshot, modifyDate(LocalDate.now()), ras);
            Log.d(ConfigKeys.LOG_TAG, "Finished loading Duty Roster data.");
            callbacks.onDutyRosterLoadingComplete(isEdit);
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
