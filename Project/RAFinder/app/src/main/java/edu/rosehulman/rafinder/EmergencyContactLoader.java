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

/**
 * Created by daveyle on 8/14/2015.
 */
public class EmergencyContactLoader {
    List<EmergencyContact> contactList=new ArrayList<>();
    ContactLoaderListener listener;
    public EmergencyContactLoader(ContactLoaderListener listener){
        this.listener=listener;
        if (listener!=null){
            Employee raOnDuty=listener.getRoster().getOnDutyNow();
            if (raOnDuty!=null){
                contactList.add(new EmergencyContact(raOnDuty, true));
            }
            List<Employee> myRAs=listener.getMyRAs();
            for (Employee employee : myRAs){
                contactList.add(new EmergencyContact(employee, false));
            }
        }
        Firebase firebaseContact=new Firebase(MainActivity.FIREBASE_ROOT_URL +"/EmergencyContacts");
        firebaseContact.addListenerForSingleValueEvent(new LoaderListener(this));


    }

    public List<EmergencyContact> getContactList() {
        return contactList;
    }

    public void setContactList(List<EmergencyContact> contactList) {
        this.contactList = contactList;
    }
    public interface ContactLoaderListener{
        public DutyRoster getRoster();
        public List<Employee> getMyRAs();
    }
    private class LoaderListener implements ValueEventListener{
        EmergencyContactLoader loader;
        public LoaderListener(EmergencyContactLoader loader){
            this.loader=loader;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot child: dataSnapshot.getChildren()){
                String firebaseURL = MainActivity.FIREBASE_ROOT_URL + child.getRef().getPath().toString();
                EmergencyContact contact=new EmergencyContact(firebaseURL);
                loader.getContactList().add(contact);
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

}
