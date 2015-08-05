package edu.rosehulman.rafinder.model;

import org.joda.time.LocalDate;

import edu.rosehulman.rafinder.model.person.Employee;

/**
 * A single item in the Duty Roster.
 */
public class DutyRosterItem {

    public interface DutyRosterCallbacks {

    }

    private String firebaseURL;
    private Employee friDuty;
    private Employee satDuty;
    private LocalDate friday;

    public DutyRosterItem(String fireBaseUrl) {
        this.firebaseURL = fireBaseUrl;
        this.friDuty = getFridayWorker();
        this.satDuty = getSaturdayWorker();
    }

    public DutyRosterItem(LocalDate friday, Employee friDuty, Employee satDuty) {
        this.friday = friday;
        this.friDuty = friDuty;
        this.satDuty = satDuty;
    }

    private Employee getFridayWorker() {
        //use firebase url to pull friday worker
        return null;
    }

    private Employee getSaturdayWorker() {
        //use firebase url to pull saturday worker
        return null;
    }

    public Employee getFriDuty() {
        return friDuty;
    }

    public void setFriDuty(Employee friDuty) {
        this.friDuty = friDuty;
    }

    public Employee getSatDuty() {
        return satDuty;
    }

    public void setSatDuty(Employee satDuty) {
        this.satDuty = satDuty;
    }

    public LocalDate getFriday() {
        return friday;
    }

    public void setFriday(LocalDate friday) {
        this.friday = friday;
    }

}



