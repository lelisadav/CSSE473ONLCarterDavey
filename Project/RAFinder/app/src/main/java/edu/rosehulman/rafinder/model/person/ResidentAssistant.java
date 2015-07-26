package edu.rosehulman.rafinder.model.person;

/**
 * An RA, who has the broadest access to the app.
 */
public class ResidentAssistant extends Employee {
    public ResidentAssistant(String name, String room, String floor, String hall, String phoneNumber,String email,  String location){
        super(name, room,floor, hall, phoneNumber, email, location);
    }
}
