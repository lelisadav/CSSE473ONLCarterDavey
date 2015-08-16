package edu.rosehulman.rafinder;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import edu.rosehulman.rafinder.model.Hall;

public class HallLoader {
    private static final String resHalls = "ResHalls";

    private Hall hall;
    private final HallLoaderListener callbacks;

    public HallLoader(String url, String hallName, HallLoaderListener cb) {
        callbacks = cb;

        Firebase firebase = new Firebase(url + "/" + resHalls + "/" + hallName);
        Log.d(ConfigKeys.LOG_TAG, "Loading HallRoster data...");
        firebase.addListenerForSingleValueEvent(new HallValueEventListener());

    }

    public Hall getHall() {
        return hall;
    }

    public interface HallLoaderListener {
        public void onHallRosterLoadingComplete();
    }

    private class HallValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            hall = new Hall(dataSnapshot);
            Log.d(ConfigKeys.LOG_TAG, "Finished loading Hall data.");
            callbacks.onHallRosterLoadingComplete();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

}
