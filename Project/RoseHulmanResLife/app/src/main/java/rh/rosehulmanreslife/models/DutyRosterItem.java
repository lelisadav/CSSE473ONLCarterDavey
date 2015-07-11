package rh.rosehulmanreslife.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.util.Calendar;


/**
 * Created by daveyle on 7/11/2015.
 */
public class DutyRosterItem {

    public interface DutyRosterCallbacks{

    }
        private String firebaseURL;
        private ResLifeWorker friDuty;
        private ResLifeWorker satDuty;
        private LocalDate friday;

        public DutyRosterItem(String fireBaseUrl){
            this.firebaseURL=fireBaseUrl;
            this.friDuty=getFridayWorker();
            this.satDuty=getSaturdayWorker();
        }
        private ResLifeWorker getFridayWorker(){
            //use firebase url to pull friday worker
            return null;
        }
        private ResLifeWorker getSaturdayWorker(){
            //use firebase url to pull saturday worker
            return null;
        }
        public ResLifeWorker getFriDuty() {
            return friDuty;
        }

        public void setFriDuty(ResLifeWorker friDuty) {
            this.friDuty = friDuty;
        }
        public ResLifeWorker getSatDuty() {
            return satDuty;
        }

        public void setSatDuty(ResLifeWorker satDuty) {
            this.satDuty = satDuty;
        }

        public LocalDate getFriday() {
            return friday;
        }

        public void setFriday(LocalDate friday) {
            this.friday = friday;
        }

    }



