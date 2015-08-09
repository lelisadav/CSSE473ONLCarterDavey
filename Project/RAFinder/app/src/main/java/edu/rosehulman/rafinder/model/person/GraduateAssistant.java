package edu.rosehulman.rafinder.model.person;

/**
 * A GA (Like an RA, but with somewhat limited priviledges (FIXME?).
 */
public class GraduateAssistant extends Employee {
    public GraduateAssistant(String url){
        super(url);
    }
    public GraduateAssistant(String name,
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
