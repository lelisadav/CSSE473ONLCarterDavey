package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;

/**
 * A Residence Life administrator (an employee of the Office of Residence Life)
 */
public class Administrator extends Employee {
    public Administrator(DataSnapshot ds) {
        super(ds);
    }

    public Administrator(String firebaseUrl) {
        super(firebaseUrl);
    }
}
