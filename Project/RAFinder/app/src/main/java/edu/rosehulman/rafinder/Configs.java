package edu.rosehulman.rafinder;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by daveyle on 8/15/2015.
 */
public class Configs {
    public static String HALL = "HALL";
    public static String FLOOR = "FLOOR";
    public static final String DATE="DATE";
    public static String FIREBASE_ROOT_URL = "https://ra-finder.firebaseio.com";
    public static String dateFormatter = "yyyy-MM-dd";
    public static DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormatter);
    public static final String LOG_TAG = "RAF";
    protected static final String KEY_SHARED_PREFS = "RA_FINDER_SHARED_PREFERENCES";
    protected static final String KEY_USER_TYPE = "KEY_USER_TYPE";
    protected static final String KEY_RA_EMAIL = "KEY_RA_EMAIL";
    protected static final String KEY_USER_EMAIL = "KEY_USER_EMAIL";
    public static final String ecEmail="Email";
    public static final String ecPhone="Phone";
    public static final String employeeName="name";
    public static final String employeeEmail="email";
    public static final String employeeFloor="floor";
    public static final String employeeHall="hall";
    public static final String employeePhone="phoneNumber";
    public static final String employeeRoom="room";
    public static final String employeeStatus="status";
    public static final String employeeStatusDetail="statusDetail";
    public static final String employeePicture="profilePicture";
    public static final String roster="Roster";
    public static final String resHalls="ResHalls";
    public static final String ResidentAssistant="Resident Assistant";
    public static final String SophomoreAdvisor="Sophomore Advisor";
    public static final String GraduateAssistant="Graduate Assistant";
    public static final String Resident="Resident";
    public static final String friday="friday";
    public static final String saturday="saturday";
    public static final String Employees ="Employees";
    public static final String ResidentAssistants="Resident Assistants";
    public static final String SophomoreAdvisors="Sophomore Advisors";
    public static final String GraduateAssistants="Graduate Assistants";
    public static final String Administrators="Administrators";
    public static final String Residents="Residents";
    public static final String EmergencyContacts="EmergencyContacts";
    public static final String DutyRosters="DutyRosters";
    public static final String myRA="myRA";
}
