package edu.rosehulman.rafinder.model.person;

/**
 * An SA (like an RA, but with limited privileges).
 * TODO: extend {@link ResidentAssistant} instead?
 */
public class SophomoreAdvisor extends Employee {
    public SophomoreAdvisor(String name, String room, String floor, String hall, String phoneNumber,String email,  String location){
        super(name, room,floor, hall, phoneNumber, email, location);
    }

}
