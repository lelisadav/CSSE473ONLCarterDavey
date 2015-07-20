package edu.rosehulman.rafinder.model.person;

import edu.rosehulman.rafinder.model.SearchResultItem;

public class Employee extends Resident {

    private String name;
    private String phoneNumber;
    private String dutyStatus;
    private String floor;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDutyStatus() {
        return dutyStatus;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
    }
    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

}
