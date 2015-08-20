package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * An RA, who has the broadest access to the app.
 */
@SuppressWarnings("unused")
public class ResidentAssistant extends Employee {
    public ResidentAssistant(DataSnapshot ds) {
        super(ds);
        setPosition(Position.RA);
    }

    public ResidentAssistant(String name, String uid) {
        super(name, uid);
        setPosition(Position.RA);
    }

    public ResidentAssistant(String name) {
        super(name);
        setPosition(Position.RA);
    }

    public ResidentAssistant(Firebase firebase) {
        super(firebase);
        setPosition(Position.RA);
    }
}
