package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * An RA, who has the broadest access to the app.
 */
public class ResidentAssistant extends Employee {
    public ResidentAssistant(DataSnapshot ds) {
        super(ds);
    }

    public ResidentAssistant(String name) {
        super(name);
    }

    public ResidentAssistant(Firebase firebase) {
        super(firebase);
    }
}
