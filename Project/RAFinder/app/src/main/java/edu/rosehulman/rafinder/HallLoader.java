package edu.rosehulman.rafinder;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import edu.rosehulman.rafinder.model.Hall;

/**
 * Created by daveyle on 8/15/2015.
 */
public class HallLoader {
    private Hall hall;
    private LoaderCallbacks callbacks;
    public HallLoader(String url, String hallName, LoaderCallbacks cb){
        callbacks=cb;

        Firebase firebase=new Firebase(url+"/"+Configs.resHalls+"/"+hallName);
        firebase.addListenerForSingleValueEvent(new HallValueEventListener());

    }
    public Hall getHall(){
        return hall;
    }

    public interface LoaderCallbacks {
        public void onHallLoadingComplete(); // TODO: similar for other loaders
    }
    public class HallValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            hall=new Hall(dataSnapshot);
            callbacks.onHallLoadingComplete();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

}
