package edu.rosehulman.rafinder;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import edu.rosehulman.rafinder.controller.DutyRosterFragment;
import edu.rosehulman.rafinder.controller.EmergencyContactsFragment;
import edu.rosehulman.rafinder.controller.HomeFragment;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMyHallRAs;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMyRA;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMySAs;
import edu.rosehulman.rafinder.controller.LoadingFragment;
import edu.rosehulman.rafinder.controller.ProfileFragment;
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
    private static final int EMPLOYEE_LOGOUT = 5;
    private static final int LOADING = -1;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private String mEmail;
    private String mRaEmail;
    private int mFloor;
    private String mHallName;

    private EmployeeLoader loader;
    private DutyRosterLoader dutyRosterLoader;
    private HallLoader hallLoader;
    private EmergencyContactsLoader ecLoader;

    private UserType mUserType = UserType.RESIDENT;

    private List<Employee> allRAs;
    private List<Employee> allSAs;
    private List<Employee> allGAs;
    private List<Employee> allAdmins;
    private List<EmergencyContact> mEmergencyContacts;
    private Employee mSelectedEmployee;
    private Employee mUser;
    private ResidentAssistant myRA;
    private LocalDate mDate;
    private DutyRoster mDutyRoster;
    private Hall mHall;

    /**
     * Borrowed from {@link PhoneNumberUtils#normalizeNumber(String)}, for use on devices below API21
     */
    private static String normalizeNumber(String phoneNumber) {
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
            if (mEmergencyContacts != null) {
                fragment = EmergencyContactsFragment.newInstance();
            } else {
                fragment = LoadingFragment.newInstance();
            }
            break;
        case DUTY_ROSTER:
            if (mDutyRoster != null) {
                fragment = DutyRosterFragment.newInstance(mHallName, mDate);
            } else {
                fragment = LoadingFragment.newInstance();
            }
            break;
        case HALL_ROSTER_OR_RESIDENT_LOGOUT:
            if (mUserType.equals(UserType.RESIDENT)) {
                logout();
                return;
            } else {
                if (mHall != null) {
                    fragment = HallRosterFragment.newInstance(mHallName, mFloor + "");
                } else {
                    fragment = LoadingFragment.newInstance();
                }
                break;
            }
        case EMPLOYEE_LOGOUT:
            logout();
            return;
        default:
            fragment = HomeFragment.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment, fragment.getClass().toString())
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

    private void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.global, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            refreshFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshFragment() {
        // TODO: refresh without altering the backstack
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.content_frame);
        manager.beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
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

    @Override
    public void sendFeedback(String name, String email, String body) {
        // TODO: async task to send an email directly from the app
        Log.d(ConfigKeys.LOG_TAG, "Preparing to send feedback email...");
        String subject = getString(R.string.profile_feedback_subject_format, name);
        String ccList = ",cartersm@rose-hulman.edu";
        new SendEmailAsyncTask().execute(subject, body, email + ccList);
    }

    /**
     * Borrowed from <a href=http://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a/2033124#2033124>This
     * StackOverflowArticle</a>
     * For sending email directly from the app.
     */
    private class SendEmailAsyncTask extends AsyncTask<String, Void, Boolean> {
        public static final int SUBJECT = 0;
        public static final int BODY = 1;
        public static final int TO = 2;
        GmailSender gmailSender;

        public SendEmailAsyncTask() {
            gmailSender = new GmailSender(ConfigKeys.FEEDBACK_EMAIL, ConfigKeys.FEEDBACK_PASSWORD);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            if (BuildConfig.DEBUG) {
                Log.v(SendEmailAsyncTask.class.getName(), "doInBackground()");
            }
            try {
                gmailSender.sendMail(params[SUBJECT], params[BODY], ConfigKeys.FEEDBACK_EMAIL, params[TO]);
                return true;
            } catch (AuthenticationFailedException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
                e.printStackTrace();
                return false;
            } catch (MessagingException e) {
                return false;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @Override
    public List<EmergencyContact> getEmergencyContacts() {
        return mEmergencyContacts;
    }

    @Override
    public void switchToProfile(Employee employee) {
        mSelectedEmployee = employee;
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = ProfileFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public List<Employee> getMySAs() {
        List<Employee> mySAs = new ArrayList<>();
        for (Employee sa : allSAs) {
            if (sa.getHall().equals(getMyHall())) {
                mySAs.add(sa);
            }
        }
        return mySAs;
    }

    @Override
    public Employee getSelectedEmployee() {
        return mSelectedEmployee;
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
        List<Employee> myRAs = new ArrayList<>();
        for (Employee ra : allRAs) {
            if (ra.getHall().equals(mHallName)
                && ra.getFloor() == mFloor) {
                myRAs.add(ra);
            }
        }
        return myRAs;
    }

    @Override
    public List<Employee> getMyHallRAs() {
        List<Employee> hallRAs = new ArrayList<>();
        for (Employee ra : allRAs) {
            if (ra.getHall().equals(mHallName) && !ra.equals(myRA)) {
                hallRAs.add(ra);
            }
        }
        return hallRAs;
    }

    private Employee getEmployee(String email) {
        for (List<Employee> employees : Arrays.asList(allRAs, allSAs, allGAs, allAdmins)) {
            for (Employee e : employees) {
                if (e.getEmail().equals(email)) {
                    return e;
                }
            }
        }
        return null;
    }

    private ResidentAssistant getRA(String email) {
        for (Employee ra : allRAs) {
            if (ra.getEmail().equals(email)) {
                return (ResidentAssistant) ra;
            }
        }
        return null;
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
        mUser = getEmployee(mEmail);
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
        // We've finished loading; go to the Home page
        onNavigationDrawerItemSelected(HOME);
    }

}
