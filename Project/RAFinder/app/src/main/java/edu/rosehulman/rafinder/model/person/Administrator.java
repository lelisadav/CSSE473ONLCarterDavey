package edu.rosehulman.rafinder.model.person;

/**
 * A Residence Life administrator (an employee of the Office of Residence Life)
 */
public class Administrator extends Employee {
    public Administrator(String name,
                         String email,
                         String hall,
                         String phoneNumber,
                         int room,
                         String status,
                         String statusDetail) {
        super(name, email, 0, hall, phoneNumber, room, status, statusDetail);
    }
}
