package edu.rosehulman.rafinder.model.person;

/**
 * An SA (like an RA, but with limited privileges).
 * TODO: extend {@link ResidentAssistant} instead?
 */
public class SophomoreAdvisor extends Employee {
    public SophomoreAdvisor(String name,
                            String email,
                            int floor,
                            String hall,
                            String phoneNumber,
                            int room,
                            String status,
                            String statusDetail) {
        super(name, email, floor, hall, phoneNumber, room, status, statusDetail);
    }
}
