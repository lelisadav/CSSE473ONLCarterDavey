package edu.rosehulman.rafinder;

import edu.rosehulman.rafinder.controller.EmergencyContactsFragment;
import edu.rosehulman.rafinder.controller.HomeFragment;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMyHallRAs;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMyRA;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMySAs;
import edu.rosehulman.rafinder.controller.reslife.DutyRosterFragment;
import edu.rosehulman.rafinder.controller.reslife.HallRosterFragment;
import edu.rosehulman.rafinder.controller.reslife.ProfileFragment;
import edu.rosehulman.rafinder.controller.student.StudentProfileFragment;

/**
 * Superinterface for Fragment callbacks.
 */
public interface ICallback
        extends NavigationDrawerFragment.NavigationDrawerCallbacks,
                HomeFragment.HomeListener,
                ProfileFragment.ProfileListener,
                EmergencyContactsFragment.EmergencyContactsListener,
                DutyRosterFragment.DutyRosterListener,
                HallRosterFragment.HallRosterListener,
                StudentProfileFragment.StudentProfileListener,
        HomeFragmentSubsectionMyHallRAs.HomeMyHallListener,
        HomeFragmentSubsectionMyRA.HomeMyRAListener,
        HomeFragmentSubsectionMySAs.HomeMySAListener
{
}
