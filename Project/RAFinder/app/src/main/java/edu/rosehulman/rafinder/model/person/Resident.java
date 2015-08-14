package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import edu.rosehulman.rafinder.model.SearchResultItem;

public class Resident implements SearchResultItem {
    private String name;
    private String firebaseURL;

    public Resident(String fireBaseUrl){
        this.firebaseURL=fireBaseUrl;
        Firebase firebase=new Firebase(firebaseURL);
        firebase.addListenerForSingleValueEvent(new ResidentListener(this));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class ResidentListener implements ValueEventListener{
        private Resident res;

        public ResidentListener(Resident res){
            this.res=res;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot child:dataSnapshot.getChildren()){
                if(child.getKey().equals("name")){
                    res.setName(child.getValue(String.class));
                }
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
