package edu.rosehulman.rafinder;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import edu.rosehulman.rafinder.controller.EmergencyContactsFragment;
import edu.rosehulman.rafinder.controller.HomeFragment;
import edu.rosehulman.rafinder.controller.LoadingFragment;
import edu.rosehulman.rafinder.controller.ProfileFragment;
import edu.rosehulman.rafinder.controller.reslife.DutyRosterFragment;
import edu.rosehulman.rafinder.controller.reslife.HallRosterFragment;
import edu.rosehulman.rafinder.controller.student.StudentDutyRosterFragment;
import edu.rosehulman.rafinder.model.DutyRoster;
import edu.rosehulman.rafinder.model.Hall;
import edu.rosehulman.rafinder.model.person.EmergencyContact;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;

/**
 * The container activity for the entire app.
 */
public class MainActivity extends Activity implements ICallback {
    public static final String LOG_TAG = "RAF";

    protected static final String KEY_SHARED_PREFS = "RA_FINDER_SHARED_PREFERENCES";
    protected static final String KEY_USER_TYPE = "KEY_USER_TYPE";
    protected static final String KEY_RA_EMAIL = "KEY_RA_EMAIL";
    protected static final String KEY_USER_EMAIL = "KEY_USER_EMAIL";

    private static final int HOME = 0;
    private static final int MY_RA = 1;
    private static final int EMERGENCY_CONTACTS = 2;
    private static final int DUTY_ROSTER = 3;
    private static final int HALL_ROSTER_OR_RESIDENT_LOGOUT = 4;
    private static final int RA_LOGOUT = 5;
    private static final int LOADING = -1;

    public static String HALL = "HALL";
    public static String FLOOR = "FLOOR";
    public static String FIREBASE_ROOT_URL = "https://ra-finder.firebaseio.com";
    public static String dateFormatter = "yyyy-MM-dd";
    public static DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormatter);

    private int mFloor;
    private String mHall;
    private Hall currHall;
    private List<Employee> allRAs;
    private EmployeeLoader loader;
    private EmergencyContactLoader ecLoader;
    private UserType mUserType = UserType.RESIDENT;
    private Employee selectedResident;
    private ResidentAssistant mUserRA;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private String mRaEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loader = new EmployeeLoader(FIREBASE_ROOT_URL, this);
//        ecLoader = new EmergencyContactLoader(this);

        mRaEmail = getIntent().getStringExtra(KEY_RA_EMAIL);
        mUserType = UserType.valueOf(getIntent().getStringExtra(KEY_USER_TYPE));

        Log.d(LOG_TAG, "Main got UserType <" + mUserType + "> and raEmail <" + mRaEmail + ">");

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // updateDrawerList the drawer with the right list for either Employee or Resident
        mNavigationDrawerFragment.updateDrawerList(mUserType);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // updateDrawerList the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment;

        switch (position) {
        case LOADING:
            fragment = LoadingFragment.newInstance();
            break;
        case HOME:
            fragment = HomeFragment.newInstance();
            break;
        case MY_RA:
            switchToProfile(mUserRA);
            return;
        case EMERGENCY_CONTACTS:
            fragment = EmergencyContactsFragment.newInstance();
            break;
        case DUTY_ROSTER:
            // TODO: kick off DutyRosterLoader, set to loading fragment, move logic to loadingComplete callback
            LocalDate date = LocalDate.now();
            int DoW = date.getDayOfWeek();
            if (DoW < DateTimeConstants.FRIDAY) {
                date.plusDays(DateTimeConstants.FRIDAY - DoW);
            }
            if (DoW > DateTimeConstants.FRIDAY) {
                date.minusDays(DoW - DateTimeConstants.FRIDAY);
            }
            if (mUserType == UserType.RESIDENT) {
                fragment = StudentDutyRosterFragment.newInstance(mHall, date);
            } else {
                fragment = DutyRosterFragment.newInstance(mHall, date);
            }
            break;
        case HALL_ROSTER_OR_RESIDENT_LOGOUT:
            if (mUserType.equals(UserType.RESIDENT)) {
                logout();
                return;
            } else {
                // TODO: kick off HallRosterLoader, set to loading fragment
                fragment = HallRosterFragment.newInstance(mHall, mFloor + "");
                break;
            }
        case RA_LOGOUT:
            logout();
            return;
        default:
            fragment = HomeFragment.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private void logout() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        new Firebase(FIREBASE_ROOT_URL).unauth();

        // clear persistent data
        SharedPreferences.Editor editor = getSharedPreferences(KEY_SHARED_PREFS, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();

        startActivity(loginIntent);
        finish();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ResidentAssistant getRA(String email) {
        for (Employee ra : getAllRAs()) {
            if (ra.getEmail().equals(email)) {
                return (ResidentAssistant) ra;
            }
        }
        return null;
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        phoneNumber = normalizeNumber(phoneNumber);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        // TODO:
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    /**
     * Borrowed from {@link PhoneNumberUtils#normalizeNumber(String)}, for use on devices below API21
     */
    public static String normalizeNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int len = phoneNumber.length();
        for (int i = 0; i < len; i++) {
            char c = phoneNumber.charAt(i);
            // Character.digit() supports ASCII and Unicode digits (fullwidth, Arabic-Indic, etc.)
            int digit = Character.digit(c, 10);
            if (digit != -1) {
                sb.append(digit);
            } else if (sb.length() == 0 && c == '+') {
                sb.append(c);
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                return normalizeNumber(PhoneNumberUtils.convertKeypadLettersToDigits(phoneNumber));
            }
        }
        return sb.toString();
    }

    public void sendEmail(String emailAddress) {
        // TODO:
    }

    @Override
    public List<EmergencyContact> getEmergencyContacts() {

        return ecLoader.getContactList();
    }

    @Override
    public void switchToProfile(Employee res) {
        selectedResident = res;
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = ProfileFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public List<Employee> getMySAs() {
        return loader.getMySAs();
    }

    @Override
    public List<Employee> getAllRAs() {
        if (allRAs == null) {
            allRAs = loader.getRAs();
        }
        return allRAs;
    }

    @Override
    public Employee getSelectedResident() {
        return selectedResident;
    }

    public UserType getUserType() {
        return mUserType;
    }

    @Override
    public String getMyHall() {
        return mHall;
    }

    @Override
    public void onEmployeeLoadingComplete() {
        mUserRA = getRA(mRaEmail);
        mHall = mUserRA.getHall();
        mFloor = mUserRA.getFloor();
        currHall = new Hall(FIREBASE_ROOT_URL + "/ResHalls/" + mHall);
        onNavigationDrawerItemSelected(0);
    }

    // TODO: onHallRosterLoadingComplete - fragment logic and startup here

    // TODO: onDutyRosterLoadingComplete - fragment logic and startup here

    @Override
    public DutyRoster getRoster() {
        return new DutyRoster(FIREBASE_ROOT_URL + "/DutyRosters/" + mHall, LocalDate.now());
    }

    @Override
    public List<Employee> getMyRAs() {
        return loader.getMyRAs();
    }

    @Override
    public Hall getHall(String hall) {
        return new Hall(FIREBASE_ROOT_URL + "/ResHalls/" + hall);
    }

    @Override
    public DutyRoster getDutyRoster(String hall, LocalDate date) {
        return new DutyRoster(FIREBASE_ROOT_URL + "/DutyRosters/" + hall, date);
    }
}
