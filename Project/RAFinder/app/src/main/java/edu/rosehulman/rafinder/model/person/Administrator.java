package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * A Residence Life administrator (an employee of the Office of Residence Life)
 */
public class Administrator extends Employee {
    public Administrator(DataSnapshot ds) {
        super(ds);
    }

    public Administrator(String name, String uid) {
        super(name, uid);
    }

    public Administrator(String name) {
        super(name);
    }

    public Administrator(Firebase firebase) {
        super(firebase);
    }
}
