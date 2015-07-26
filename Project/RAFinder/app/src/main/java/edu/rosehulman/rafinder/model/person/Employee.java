package edu.rosehulman.rafinder.model.person;

/**
 * Any Residence Life employee.
 */
public class Employee extends Resident {

    //private String name;
    private String phoneNumber;
    private String emailAddress;

    private String location;

    private String floor;
    public Employee(String name, String room, String floor, String hall, String phoneNumber,String email,  String location){
        super(name, room, hall);
        this.floor=floor;
        this.phoneNumber=phoneNumber;
        this.emailAddress=email;

        this.location=location;

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }




    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


}
