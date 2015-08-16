package edu.rosehulman.rafinder;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.rafinder.model.DutyRoster;
import edu.rosehulman.rafinder.model.person.EmergencyContact;
import edu.rosehulman.rafinder.model.person.Employee;

public class EmergencyContactLoader {
    private static final String EmergencyContacts = "EmergencyContacts";
    List<EmergencyContact> contactList = new ArrayList<>();
    ContactLoaderListener listener;

    public EmergencyContactLoader(ContactLoaderListener listener) {
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
        Firebase firebaseContact = new Firebase(ConfigKeys.FIREBASE_ROOT_URL + "/" + EmergencyContacts);
        firebaseContact.addListenerForSingleValueEvent(new LoaderListener(this));


    }

    public List<EmergencyContact> getContactList() {
        return contactList;
    }

    public void setContactList(List<EmergencyContact> contactList) {
        this.contactList = contactList;
    }

    public interface ContactLoaderListener {
        public DutyRoster getDutyRoster();
        public List<Employee> getMyRAs();
        public void onEmergencyContactsLoadingComplete();
    }

    private class LoaderListener implements ValueEventListener {
        EmergencyContactLoader loader;

        public LoaderListener(EmergencyContactLoader loader) {
            this.loader = loader;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                String firebaseURL = ConfigKeys.FIREBASE_ROOT_URL + child.getRef().getPath().toString();
                EmergencyContact contact = new EmergencyContact(firebaseURL);
                loader.getContactList().add(contact);
            }
            listener.onEmergencyContactsLoadingComplete();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

}
