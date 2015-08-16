package edu.rosehulman.rafinder;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.rafinder.model.DutyRoster;
import edu.rosehulman.rafinder.model.person.EmergencyContact;
import edu.rosehulman.rafinder.model.person.Employee;

public class EmergencyContactsLoader {
    private static final String EmergencyContacts = "EmergencyContacts";
    private List<EmergencyContact> contactList = new ArrayList<>();
    private final EmergencyContactsLoaderListener listener;

    public EmergencyContactsLoader(EmergencyContactsLoaderListener listener) {
        this.listener = listener;
        if (listener != null) {
            Employee raOnDuty = listener.getDutyRoster().getOnDutyNow();
            if (raOnDuty != null) {
                contactList.add(new EmergencyContact(raOnDuty, true));
            }

            List<Employee> myRAs = listener.getMyRAs();
            for (Employee employee : myRAs) {
                contactList.add(new EmergencyContact(employee, false));
            }
        }
        Firebase firebase = new Firebase(ConfigKeys.FIREBASE_ROOT_URL + "/" + EmergencyContacts);
        Log.d(ConfigKeys.LOG_TAG, "Loading Emergency Contact data...");
        firebase.addListenerForSingleValueEvent(new LoaderListener());


    }

    public List<EmergencyContact> getContactList() {
        return contactList;
    }

    public void setContactList(List<EmergencyContact> contactList) {
        this.contactList = contactList;
    }

    public interface EmergencyContactsLoaderListener {
        public DutyRoster getDutyRoster();
        public List<Employee> getMyRAs();
        public void onEmergencyContactsLoadingComplete();
    }

    private class LoaderListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                String firebaseURL = ConfigKeys.FIREBASE_ROOT_URL + child.getRef().getPath().toString();
                EmergencyContact contact = new EmergencyContact(firebaseURL);
                contactList.add(contact);
            }
            Log.d(ConfigKeys.LOG_TAG, "Finished loading Emergency Contact data.");
            listener.onEmergencyContactsLoadingComplete();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

}
