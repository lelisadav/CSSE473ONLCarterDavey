package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * An SA (like an RA, but with limited privileges).
 */
@SuppressWarnings("unused")
public class SophomoreAdvisor extends Employee {
    public SophomoreAdvisor(DataSnapshot ds) {
        super(ds);
        setPosition(Position.SA);
    }

    public SophomoreAdvisor(String name, String uid) {
        super(name, uid);
        setPosition(Position.SA);
    }

    public SophomoreAdvisor(String name) {
        super(name);
        setPosition(Position.SA);
    }

    public SophomoreAdvisor(Firebase firebase) {
        super(firebase);
        setPosition(Position.SA);
    }
}
