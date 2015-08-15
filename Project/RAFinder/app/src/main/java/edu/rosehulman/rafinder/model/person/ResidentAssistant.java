package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;

/**
 * An RA, who has the broadest access to the app.
 */
public class ResidentAssistant extends Employee {
    public ResidentAssistant(DataSnapshot ds){
        super(ds);
    }

    public ResidentAssistant(String firebaseUrl) {
        super(firebaseUrl);
    }
}
