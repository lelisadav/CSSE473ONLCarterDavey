package edu.rosehulman.rafinder.model;

import org.joda.time.LocalDate;

import edu.rosehulman.rafinder.model.person.Employee;


/**
 * Created by daveyle on 7/11/2015.
 */
public class DutyRosterItem {

    public interface DutyRosterCallbacks{

    }
        private String firebaseURL;
        private Employee friDuty;
        private Employee satDuty;
        private LocalDate friday;

        public DutyRosterItem(String fireBaseUrl){
            this.firebaseURL=fireBaseUrl;
            this.friDuty=getFridayWorker();
            this.satDuty=getSaturdayWorker();
        }
        private Employee getFridayWorker(){
            //use firebase url to pull friday worker
            return null;
        }
        private Employee getSaturdayWorker(){
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



