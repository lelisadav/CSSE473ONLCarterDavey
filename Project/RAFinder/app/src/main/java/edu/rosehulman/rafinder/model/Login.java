package edu.rosehulman.rafinder.model;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import edu.rosehulman.rafinder.ConfigKeys;
import edu.rosehulman.rafinder.LoginActivity;
import edu.rosehulman.rafinder.UserType;


public class Login {
    // Resident Firebase key
    private static final String myRA = "myRA";

    private static final String Residents = "Residents";

    private Firebase mFirebaseRef;
    private LoginActivity mActivity;

    private UserType mUserType;
    private String mRAEmail = "";

    public Login(Firebase firebaseRef, LoginActivity loginActivity) {
        mFirebaseRef = firebaseRef;
        mActivity = loginActivity;
    }

    /**
     * Authenticates the user with Firebase, using the specified email address and password
     */
    public void loginWithPassword(String username, String password) {
        mFirebaseRef.authWithPassword(username, password, new AuthResultHandler(
                "password"));
    }

    /**
     * Authenticates a user to allow them to login.
     */
    private void setAuthenticatedUser(AuthData authData) {
        if (authData != null) {
            mFirebaseRef.child(ConfigKeys.Employees).addListenerForSingleValueEvent(new EmployeeListener(authData.getUid()));
        }
    }

    /**
     * Verifies the user provided a valid email.
     */
    public static boolean isEmailValid(String email) {
        return email.matches(".*?@.*?\\..*"); // TODO eventually: ensure it's a rose address
    }

    /**
     * Verifies the password is longer than 4 characters.
     */
    public static boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * A custom AuthResultHandler
     */
    private class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        /**
         * Creates a new AuthResultHandler
         */
        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        /**
         * On successful authentication, calls the setAuthenticatedUser method
         */
        public void onAuthenticated(AuthData authData) {
            setAuthenticatedUser(authData);
        }

        /**
         * On failed login, displays an error message
         */
        public void onAuthenticationError(FirebaseError firebaseError) {
            mActivity.showError(firebaseError.toString());
        }
    }

    private class ResidentListener implements ValueEventListener {
        private final String uid;

        public ResidentListener(String uid) {
            this.uid = uid;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(ConfigKeys.LOG_TAG, "In Resident callback for user <" + uid + ">");
            if (dataSnapshot.hasChild(uid)) {
                mUserType = UserType.RESIDENT;
                mRAEmail = dataSnapshot.child(uid).child(myRA).getValue().toString();
                mActivity.launchMainActivity(mUserType, mRAEmail);
            } else {
                Log.e(ConfigKeys.LOG_TAG, "No resident found with uid <" + uid + ">");
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            // ignored
        }
    }

    private class EmployeeListener implements ValueEventListener {
        private final String uid;

        public EmployeeListener(String uid) {
            this.uid = uid;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(ConfigKeys.LOG_TAG, "in Employees callback for user <" + uid + ">");
            Log.d(ConfigKeys.LOG_TAG, "DataSnapshot url = " + dataSnapshot.getRef().getPath().toString());
            if (dataSnapshot.getChildrenCount() != 4) {
                Log.wtf(ConfigKeys.LOG_TAG, "Employees table had wrong number of children");
            }
            DataSnapshot table;
            if (dataSnapshot.child(ConfigKeys.Administrators).hasChild(uid)) {
                mUserType = UserType.ADMINISTRATOR;
                table = dataSnapshot.child(ConfigKeys.Administrators);
            } else if (dataSnapshot.child(ConfigKeys.ResidentAssistants).hasChild(uid)) {
                mUserType = UserType.RESIDENT_ASSISTANT;
                table = dataSnapshot.child(ConfigKeys.ResidentAssistants);
            } else if (dataSnapshot.child(ConfigKeys.SophomoreAdvisors).hasChild(uid)) {
                mUserType = UserType.SOPHOMORE_ADVISOR;
                table = dataSnapshot.child(ConfigKeys.SophomoreAdvisors);
            } else if (dataSnapshot.child(ConfigKeys.GraduateAssistants).hasChild(uid)) {
                mUserType = UserType.GRADUATE_ASSISTANT;
                table = dataSnapshot.child(ConfigKeys.GraduateAssistants);
            } else {
                mFirebaseRef.child(Residents).addListenerForSingleValueEvent(new ResidentListener(uid));
                Log.d(ConfigKeys.LOG_TAG, "User <" + uid + "> not found in employees");
                return;
            }
            mRAEmail = table.child(uid).child(ConfigKeys.employeeEmail).getValue().toString();
            mActivity.launchMainActivity(mUserType, mRAEmail);
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            // ignored
        }
    }
}