package edu.rosehulman.rafinder;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import edu.rosehulman.rafinder.model.DutyRoster;

/**
 * Created by daveyle on 8/15/2015.
 */
public class DutyRosterLoader {
    private DutyRoster roster;
    private LoaderCallbacks callbacks;
    private LocalDate date;
    public DutyRosterLoader(String url, String hallName, LoaderCallbacks cb){
        callbacks=cb;
        Firebase firebase=new Firebase(url+"/"+Configs.DutyRosters+"/"+hallName);
        firebase.addListenerForSingleValueEvent(new DutyRosterValueEventListener());

    }
    public LocalDate modifyDate(LocalDate dt){
        if (dt.getDayOfWeek()== DateTimeConstants.FRIDAY){
            dt=dt.minusDays(1);
        }
        else if (dt.getDayOfWeek()==DateTimeConstants.SATURDAY){
            dt=dt.minusDays(2);
        }
        else if (dt.getDayOfWeek()==DateTimeConstants.SUNDAY){
            dt=dt.minusDays(3);
        }
        date=dt;
        return dt;

    }

    public LocalDate getDate() {
        return date;
    }

    public DutyRoster getDutyRoster() {
        return roster;
    }

    public interface LoaderCallbacks {
        public void onDutyRosterLoadingComplete(); // TODO: similar for other loaders
    }
    public class DutyRosterValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            roster=new DutyRoster(dataSnapshot, modifyDate(LocalDate.now()));
            callbacks.onDutyRosterLoadingComplete();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}