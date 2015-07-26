package edu.rosehulman.rafinder.model.person;

/**
 * A Residence Life administrator (an employee of the Office of Residence Life)
 */
public class Administrator extends Employee
{
    public Administrator(String name, String room, String floor, String hall, String phoneNumber,String email,  String location){
        super(name, room,floor, hall, phoneNumber, email, location);
    }
}
