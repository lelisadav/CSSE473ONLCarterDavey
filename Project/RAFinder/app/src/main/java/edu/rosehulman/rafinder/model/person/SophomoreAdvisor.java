package edu.rosehulman.rafinder.model.person;

import com.firebase.client.DataSnapshot;

/**
 * An SA (like an RA, but with limited privileges).
 * TODO: extend {@link ResidentAssistant} instead?
 */
public class SophomoreAdvisor extends Employee {
    public SophomoreAdvisor(DataSnapshot ds) {
        super(ds);
    }

    public SophomoreAdvisor(String firebaseUrl) {
        super(firebaseUrl);
    }
}
