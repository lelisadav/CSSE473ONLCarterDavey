package edu.rosehulman.rafinder;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
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
import edu.rosehulman.rafinder.controller.ProfileFragment;
import edu.rosehulman.rafinder.controller.reslife.DutyRosterFragment;
import edu.rosehulman.rafinder.controller.reslife.HallRosterFragment;
import edu.rosehulman.rafinder.controller.student.StudentDutyRosterFragment;
import edu.rosehulman.rafinder.model.DutyRoster;
import edu.rosehulman.rafinder.model.DutyRosterItem;
import edu.rosehulman.rafinder.model.Hall;
import edu.rosehulman.rafinder.model.RoomEntry;
import edu.rosehulman.rafinder.model.person.EmergencyContact;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;

/**
 * The container activity for the entire app.
 */
public class MainActivity extends Activity implements ICallback {
    public static final String LOG_TAG = "RAF";
    private static final int HOME = 0;
    private static final int MY_RA = 1;
    private static final int EMERGENCY_CONTACTS = 2;
    private static final int DUTY_ROSTER = 3;
    private static final int HALL_ROSTER_OR_RESIDENT_LOGOUT = 4;
    private static final int RA_LOGOUT = 5;
    protected static final String KEY_USER_TYPE = "KEY_USER_TYPE";
    protected static final String KEY_RA_EMAIL = "KEY_RA_EMAIL";
    public static String HALL="HALL";
    public static String FLOOR="FLOOR";
    private static int myFloor = 3;
    private static String myHall = "Lakeside";
    private Hall currHall;
    public static String URL="https://ra-finder.firebaseio.com";
    private List<Employee> allRAs;
    private EmployeeLoader loader;
    private EmergencyContactLoader ecLoader;
    private UserType mUserType = UserType.RESIDENT;
    private Employee selectedResident;
    private ResidentAssistant mUserRA;
    public static String dateFormatter="yyyy-MM-dd";
    public static DateTimeFormatter formatter= DateTimeFormat.forPattern(dateFormatter);
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loader = new EmployeeLoader(URL + "/Employees", this);
        ecLoader=new EmergencyContactLoader(this);
        currHall=new Hall(URL+"/ResHalls/"+myHall);

        mUserType = UserType.valueOf(getIntent().getStringExtra(KEY_USER_TYPE));
        mUserRA = getRA(getIntent().getStringExtra(KEY_RA_EMAIL));

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

    private ResidentAssistant getRA(String email) {
        for (Employee ra : getAllRAs()) {
            if (ra.getEmail().equals(email)) {
                return (ResidentAssistant) ra;
            }
        }
        return null;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // updateDrawerList the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment;

        switch (position) {
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
            LocalDate date=LocalDate.now();
            int DoW=date.getDayOfWeek();
            if (DoW<DateTimeConstants.FRIDAY){
                date.plusDays(DateTimeConstants.FRIDAY-DoW);
            }
            if (DoW>DateTimeConstants.FRIDAY){
                date.minusDays(DoW-DateTimeConstants.FRIDAY);
            }
            if (mUserType==UserType.RESIDENT){
                fragment= StudentDutyRosterFragment.newInstance(myHall, date);
            }
            else {
                fragment = DutyRosterFragment.newInstance(myHall, date);
            }
            break;
        case HALL_ROSTER_OR_RESIDENT_LOGOUT:
            if (mUserType.equals(UserType.RESIDENT)) {
                logout();
                return;
            } else {
                fragment = HallRosterFragment.newInstance(myHall, myFloor+"");
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
        // TODO: logout da Firebase
        Intent loginIntent = new Intent(this, LoginActivity.class);
        Firebase ref = new Firebase(getString(R.string.firebase_url));
        ref.unauth();

        startActivity(loginIntent);
        finish();
    }

    public void onSectionAttached(int number) {
        switch (number) {
        case 1:
            mTitle = getString(R.string.title_section1);
            break;
        case 2:
            mTitle = getString(R.string.title_section2);
            break;
        case 3:
            mTitle = getString(R.string.title_section3);
            break;
        }
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

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    public void sendEmail(String emailAddress) {

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
            //allRAs=DummyData.getMyRAs();
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
        return myHall;
    }

    @Override
    public DutyRoster getRoster() {
        return new DutyRoster(URL+"/DutyRosters/"+myHall, LocalDate.now());
    }

    @Override
    public List<Employee> getMyRAs(){
        return loader.getMyRAs();
    }

    @Override
    public Hall getHall(String hall){
       return new Hall(URL+"/ResHalls/"+hall);
    }

    @Override
    public DutyRoster getDutyRoster(String hall, LocalDate date) {
        return new DutyRoster(URL+"/DutyRosters/"+hall, date);
    }
}
