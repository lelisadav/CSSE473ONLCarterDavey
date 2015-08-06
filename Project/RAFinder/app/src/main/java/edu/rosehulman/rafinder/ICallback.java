package edu.rosehulman.rafinder;

import edu.rosehulman.rafinder.controller.EmergencyContactsFragment;
import edu.rosehulman.rafinder.controller.HomeFragment;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMyHallRAs;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMyRA;
import edu.rosehulman.rafinder.controller.HomeFragmentSubsectionMySAs;
import edu.rosehulman.rafinder.controller.ProfileFragment;
import edu.rosehulman.rafinder.controller.reslife.DutyRosterFragment;
import edu.rosehulman.rafinder.controller.reslife.HallRosterFragment;

/**
 * Superinterface for Fragment callbacks.
 */
public interface ICallback
        extends NavigationDrawerFragment.NavigationDrawerCallbacks,
                HomeFragment.HomeListener,
                EmergencyContactsFragment.EmergencyContactsListener,
                DutyRosterFragment.DutyRosterListener,
                HallRosterFragment.HallRosterListener,
                ProfileFragment.StudentProfileListener,
                HomeFragmentSubsectionMyHallRAs.HomeMyHallListener,
                HomeFragmentSubsectionMyRA.HomeMyRAListener,
                HomeFragmentSubsectionMySAs.HomeMySAListener {
}
