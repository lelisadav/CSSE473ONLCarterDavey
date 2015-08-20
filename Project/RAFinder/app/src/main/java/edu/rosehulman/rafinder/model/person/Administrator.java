package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * A Residence Life administrator (an employee of the Office of Residence Life)
 */
@SuppressWarnings("unused")
public class Administrator extends Employee {
    public Administrator(DataSnapshot ds) {
        super(ds);
        setPosition(Position.ADMIN);
    }

    public Administrator(String name, String uid) {
        super(name, uid);
        setPosition(Position.ADMIN);
    }

    public Administrator(String name) {
        super(name);
        setPosition(Position.ADMIN);
    }

    public Administrator(Firebase firebase) {
        super(firebase);
        setPosition(Position.ADMIN);
    }
}
