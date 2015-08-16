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

import org.joda.time.LocalDate;

import java.util.List;

import edu.rosehulman.rafinder.controller.EmergencyContactsFragment;
import edu.rosehulman.rafinder.controller.HomeFragment;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMyHallRAs;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMyRA;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMySAs;
import edu.rosehulman.rafinder.controller.LoadingFragment;
import edu.rosehulman.rafinder.controller.ProfileFragment;
import edu.rosehulman.rafinder.controller.DutyRosterFragment;
import edu.rosehulman.rafinder.controller.reslife.HallRosterFragment;
import edu.rosehulman.rafinder.model.DutyRoster;
import edu.rosehulman.rafinder.model.Hall;
import edu.rosehulman.rafinder.model.person.EmergencyContact;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;

/**
 * The container activity for the entire app.
 */
public class MainActivity extends Activity
        implements  NavigationDrawerFragment.NavigationDrawerCallbacks,
                    HomeFragment.HomeListener,
                    EmergencyContactsFragment.EmergencyContactsListener,
                    DutyRosterFragment.DutyRosterListener,
                    HallRosterFragment.HallRosterListener,
                    ProfileFragment.StudentProfileListener,
                    HomeFragmentSubsectionMyHallRAs.HomeMyHallListener,
                    HomeFragmentSubsectionMyRA.HomeMyRAListener,
                    HomeFragmentSubsectionMySAs.HomeMySAListener,
                    EmployeeLoader.EmployeeLoaderListener,
                    EmergencyContactsLoader.EmergencyContactsLoaderListener,
                    HallLoader.HallLoaderListener,
                    DutyRosterLoader.DutyRosterLoaderListener {

    private static final int HOME = 0;
    private static final int MY_RA = 1;
    private static final int EMERGENCY_CONTACTS = 2;
    private static final int DUTY_ROSTER = 3;
    private static final int HALL_ROSTER_OR_RESIDENT_LOGOUT = 4;
    private static final int RA_LOGOUT = 5;
    private static final int LOADING = -1;

    private int mFloor;
    private String mHallName;
    private Hall mHall;
    private Hall currHall;
    private EmployeeLoader loader;
    private EmergencyContactsLoader ecLoader;
    private DutyRosterLoader dutyRosterLoader;
    private DutyRoster roster;
    private HallLoader hallLoader;
    private UserType mUserType = UserType.RESIDENT;
    private Employee selectedResident;
    private ResidentAssistant myRA;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;
    private String mRaEmail;
    private Employee mUser;
    private String mEmail;

    private List<Employee> allRAs;
    private List<Employee> allSAs;
    private List<Employee> allGAs;
    private List<Employee> allAdmins;
    private DutyRoster mDutyRoster;
    private LocalDate mDate;
    private List<EmergencyContact> mEmergencyContacts;

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
            switchToProfile(myRA);
            return;
        case EMERGENCY_CONTACTS:
            if (mEmergencyContacts == null) {
                fragment = LoadingFragment.newInstance();
            } else {
                fragment = EmergencyContactsFragment.newInstance();
            }
            break;
        case DUTY_ROSTER:
            if (mDutyRoster == null) {
                fragment = LoadingFragment.newInstance();
            } else {
                fragment = DutyRosterFragment.newInstance(mHallName, mDate);
            }
            break;
        case HALL_ROSTER_OR_RESIDENT_LOGOUT:
            if (mUserType.equals(UserType.RESIDENT)) {
                logout();
                return;
            } else {
                if (mHall == null) {
                    fragment = LoadingFragment.newInstance();
                } else {
                    fragment = HallRosterFragment.newInstance(mHallName, mFloor + "");
                }
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
        return mEmergencyContacts;
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
        return mHallName;
    }

    @Override
    public DutyRoster getDutyRoster() {
        return mDutyRoster;
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

    // The onLoadingComplete family of methods are arranged in a daisy-chain of loaders, as Firebase's async,
    // callback-based (i.e. non-blocking) nature requires us to frontload the data to prevent NullPointer exceptions
    @Override
    public void onEmployeeLoadingComplete() {
        setAllEmployees();
        myRA = getRA(mRaEmail);
        mHallName = myRA.getHall();
        mFloor = myRA.getFloor();
        mUser = loader.getEmployee(mEmail);
        dutyRosterLoader = new DutyRosterLoader(ConfigKeys.FIREBASE_ROOT_URL, mHallName, this, allRAs);
    }

    private void setAllEmployees() {
        allRAs = loader.getRAs();
        allSAs = loader.getSAs();
        allGAs = loader.getGAs();
        allAdmins = loader.getAdmins();
    }

    @Override
    public void onDutyRosterLoadingComplete() {
        mDutyRoster = dutyRosterLoader.getDutyRoster();
        mDate = dutyRosterLoader.getDate();
        hallLoader = new HallLoader(ConfigKeys.FIREBASE_ROOT_URL, mHallName, this);
    }

    @Override
    public void onHallRosterLoadingComplete() {
        mHall = hallLoader.getHall();
        ecLoader = new EmergencyContactsLoader(this);
    }

    @Override
    public void onEmergencyContactsLoadingComplete() {
        mEmergencyContacts = ecLoader.getContactList();
        onNavigationDrawerItemSelected(HOME);
    }

}
