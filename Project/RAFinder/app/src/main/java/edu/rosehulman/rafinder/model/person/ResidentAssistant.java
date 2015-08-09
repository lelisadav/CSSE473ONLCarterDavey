package edu.rosehulman.rafinder.model.person;

/**
 * An RA, who has the broadest access to the app.
 */
public class ResidentAssistant extends Employee {
    public ResidentAssistant(String url){
        super(url);
    }
    public ResidentAssistant(String name,
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
