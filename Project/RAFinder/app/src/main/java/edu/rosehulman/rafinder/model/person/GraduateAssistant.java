package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * A GA (Like an RA, but with somewhat limited priviledges (FIXME?).
 */
public class GraduateAssistant extends Employee {
    public GraduateAssistant(DataSnapshot ds) {
        super(ds);
    }

    public GraduateAssistant(String name, String uid) {
        super(name, uid);
    }

    public GraduateAssistant(String name) {
        super(name);
    }

    public GraduateAssistant(Firebase firebase) {
        super(firebase);
    }
}
