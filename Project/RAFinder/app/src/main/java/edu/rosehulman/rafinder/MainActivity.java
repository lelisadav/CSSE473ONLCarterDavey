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
    private static final int HOME = 0;
    private static final int MY_RA = 1;
    private static final int EMERGENCY_CONTACTS = 2;
    private static final int DUTY_ROSTER = 3;
    private static final int HALL_ROSTER_OR_RESIDENT_LOGOUT = 4;
    private static final int RA_LOGOUT = 5;
    private static final int LOADING = -1;

    private int mFloor;
    private String mHall;
    private Hall currHall;
    private List<Employee> allRAs;
    private EmployeeLoader loader;
    private EmergencyContactLoader ecLoader;
    private DutyRosterLoader dutyRosterLoader;
    private DutyRoster roster;
    private HallLoader hallLoader;
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
    private Employee mUser;
    private String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loader = new EmployeeLoader(ConfigKeys.FIREBASE_ROOT_URL, this);

        mRaEmail = getIntent().getStringExtra(ConfigKeys.KEY_RA_EMAIL);
        mUserType = UserType.valueOf(getIntent().getStringExtra(ConfigKeys.KEY_USER_TYPE));
        mEmail = getIntent().getStringExtra(ConfigKeys.KEY_USER_EMAIL);

        Log.d(ConfigKeys.LOG_TAG, "Main got UserType <" + mUserType + "> and raEmail <" + mRaEmail + ">");

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
        // update the main content by replacing fragments
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
            ecLoader = new EmergencyContactLoader(this);
            fragment = LoadingFragment.newInstance();
            break;
        case DUTY_ROSTER:
            dutyRosterLoader = new DutyRosterLoader(ConfigKeys.FIREBASE_ROOT_URL, mHall, this);
            fragment = LoadingFragment.newInstance();
            break;
        case HALL_ROSTER_OR_RESIDENT_LOGOUT:
            if (mUserType.equals(UserType.RESIDENT)) {
                logout();
                return;
            } else {
                hallLoader = new HallLoader(ConfigKeys.FIREBASE_ROOT_URL, mHall, this);
                fragment = LoadingFragment.newInstance();
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
        new Firebase(ConfigKeys.FIREBASE_ROOT_URL).unauth();

        // clear persistent data
        SharedPreferences.Editor editor = getSharedPreferences(ConfigKeys.KEY_SHARED_PREFS, MODE_PRIVATE).edit();
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
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

    public void sendEmail(String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void sendFeedback(String name, String email) {
        // TODO: async task to send an email directly from the app
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
        mUser = loader.getEmployee(mEmail);
        onNavigationDrawerItemSelected(HOME);
    }

    @Override
    public void onHallRosterLoadingComplete() {
        Fragment fragment = HallRosterFragment.newInstance(mHall, mFloor + "");
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public void onDutyRosterLoadingComplete() {
        Fragment fragment;
        if (mUserType == UserType.RESIDENT) {
            fragment = StudentDutyRosterFragment.newInstance(mHall, dutyRosterLoader.getDate());
        } else {
            fragment = DutyRosterFragment.newInstance(mHall, dutyRosterLoader.getDate());
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public void onEmergencyContactsLoadingComplete() {
        Fragment fragment = EmergencyContactsFragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public DutyRoster getDutyRoster() {
        return dutyRosterLoader.getDutyRoster();
    }

    @Override
    public List<Employee> getMyRAs() {
        return loader.getMyRAs();
    }

    @Override
    public Hall getHall(String hall) {
        return hallLoader.getHall();
    }

    public Employee getUser() {
        return mUser;
    }

//    @Override
//    public DutyRoster getDutyRoster(String hall, LocalDate date) {
//        return new DutyRoster(ConfigKeys.FIREBASE_ROOT_URL + "/"+ConfigKeys.DutyRosters+"/" + hall, date);
//    }
}
