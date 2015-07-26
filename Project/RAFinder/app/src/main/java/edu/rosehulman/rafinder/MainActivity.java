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

import edu.rosehulman.rafinder.controller.EmergencyContactsFragment;
import edu.rosehulman.rafinder.controller.HomeFragment;
import edu.rosehulman.rafinder.controller.reslife.DutyRosterFragment;
import edu.rosehulman.rafinder.controller.reslife.HallRosterFragment;
import edu.rosehulman.rafinder.controller.student.StudentProfileFragment;
import edu.rosehulman.rafinder.model.dummy.DummyData;
import edu.rosehulman.rafinder.model.person.Resident;

/**
 * The container activity for the entire app.
 */
public class MainActivity extends Activity implements ICallback {
    private static final int HOME = 0;
    private static final int MY_RA = 1;
    private static final int EMERGENCY_CONTACTS = 2;
    private static final int DUTY_ROSTER = 3;
    private static final int HALL_ROSTER = 4;
    private Resident selectedResident;
    public static boolean isRA = false;
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

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

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
        case HOME:
            fragment = HomeFragment.newInstance();
            break;
        case MY_RA:
            switchToProfile(DummyData.getMyRAs().get(0));
            fragment = StudentProfileFragment.newInstance();
            break;
        case EMERGENCY_CONTACTS:
            fragment = EmergencyContactsFragment.newInstance();
            break;
        case DUTY_ROSTER:
            fragment = DutyRosterFragment.newInstance();
            break;
        case HALL_ROSTER:
            if (isRA) {
                fragment = HallRosterFragment.newInstance();
                break;
            }
        default:
            // intentional fallthrough
            fragment = HomeFragment.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
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
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onDutyRosterInteraction() {

    }

    @Override
    public void onEmergencyContactsInteraction() {

    }

    @Override
    public void onHallRosterInteraction() {

    }

    @Override
    public void onHomeInteraction() {

    }

    @Override
    public void switchToProfile(Resident res) {
        this.selectedResident=res;
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment=StudentProfileFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

    }

    @Override
    public void onProfileInteraction() {

    }

    @Override
    public void onStudentProfileInteraction(Uri uri) {

    }

    @Override
    public Resident getSelectedResident() {
        return this.selectedResident;
    }
}
