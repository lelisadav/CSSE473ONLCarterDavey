package edu.rosehulman.rafinder.models;

/**
 * Created by daveyle on 7/11/2015.
 */
public class ResLifeWorker extends Resident implements SearchResultItem {

    private String name;
    private String phoneNumber;

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
}
