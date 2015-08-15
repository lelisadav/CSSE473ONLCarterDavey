package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;

/**
 * A GA (Like an RA, but with somewhat limited priviledges (FIXME?).
 */
public class GraduateAssistant extends Employee {
    public GraduateAssistant(DataSnapshot ds) {
        super(ds);
    }

    public GraduateAssistant(String firebaseUrl) {
        super(firebaseUrl);
    }
}
