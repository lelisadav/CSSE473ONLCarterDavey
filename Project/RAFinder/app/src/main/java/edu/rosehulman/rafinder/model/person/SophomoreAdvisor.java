package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * An SA (like an RA, but with limited privileges).
 * TODO: extend {@link ResidentAssistant} instead?
 */
public class SophomoreAdvisor extends Employee {
    public SophomoreAdvisor(DataSnapshot ds) {
        super(ds);
    }

    public SophomoreAdvisor(String name) {
        super(name);
    }

    public SophomoreAdvisor(Firebase firebase) {
        super(firebase);
    }
}
