package edu.rosehulman.rafinder.model.person;

/**
 * A GA (Like an RA, but with somewhat limited priviledges (FIXME?).
 */
public class GraduateAssistant extends Employee {
    public GraduateAssistant(String name, String room, String floor, String hall, String phoneNumber,String email,  String location){
        super(name, room,floor, hall, phoneNumber, email, location);
    }
}
