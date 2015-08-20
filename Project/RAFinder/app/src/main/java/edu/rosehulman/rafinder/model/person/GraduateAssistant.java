package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * A GA (Like an RA, but with somewhat limited privileges.
 */
@SuppressWarnings("unused")
public class GraduateAssistant extends Employee {
    public GraduateAssistant(DataSnapshot ds) {
        super(ds);
        setPosition(Position.GA);
    }

    public GraduateAssistant(String name, String uid) {
        super(name, uid);
        setPosition(Position.GA);
    }

    public GraduateAssistant(String name) {
        super(name);
        setPosition(Position.GA);
    }

    public GraduateAssistant(Firebase firebase) {
        super(firebase);
        setPosition(Position.GA);
    }
}
