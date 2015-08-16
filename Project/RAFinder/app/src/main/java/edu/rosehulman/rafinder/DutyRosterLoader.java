package edu.rosehulman.rafinder;

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
    private LoaderCallbacks callbacks;
    private LocalDate date;

    public DutyRosterLoader(String url, String hallName, LoaderCallbacks cb, List<Employee> ras) {
        callbacks = cb;
        this.ras = ras;
        Firebase firebase = new Firebase(url + "/" + DutyRosters + "/" + hallName);
        firebase.addListenerForSingleValueEvent(new DutyRosterValueEventListener());

    }

    public LocalDate modifyDate(LocalDate dt) {
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

    public interface LoaderCallbacks {
        public void onDutyRosterLoadingComplete();

    }

    public class DutyRosterValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            roster = new DutyRoster(dataSnapshot, modifyDate(LocalDate.now()), ras);
            callbacks.onDutyRosterLoadingComplete();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
