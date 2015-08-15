package edu.rosehulman.rafinder.model;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import edu.rosehulman.rafinder.Configs;
import edu.rosehulman.rafinder.LoginActivity;
import edu.rosehulman.rafinder.UserType;


public class Login {

    private Firebase mFirebaseRef;

    private LoginActivity mActivity;

    /* Data from the authenticated user */
    private AuthData authData;
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
            this.authData = authData;
            mFirebaseRef.child(Configs.Employees).addListenerForSingleValueEvent(new EmployeeListener(authData.getUid()));

//            mActivity.launchMainActivity(this.authData);
        }
    }

    /**
     * Verifies the user provided a valid email.
     */
    public boolean isEmailValid(String email) {
        return email.contains("@"); // TODO eventually: ensure it's a rose address
    }

    /**
     * Verifies the password is longer than 4 characters.
     */
    public boolean isPasswordValid(String password) {
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
            Log.d(Configs.LOG_TAG, "In Resident callback for user <" + uid + ">");
            if (dataSnapshot.hasChild(uid)) {
                mUserType = UserType.RESIDENT;
                mRAEmail = dataSnapshot.child(uid).child(Configs.myRA).getValue().toString();
                mActivity.launchMainActivity(mUserType, mRAEmail);
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
            Log.d(Configs.LOG_TAG, "in Employees callback for user <" + uid + ">");
            Log.d(Configs.LOG_TAG, "DataSnapshot url = " + dataSnapshot.getRef().getPath().toString());
            if (dataSnapshot.getChildrenCount() != 4) {
                Log.wtf(Configs.LOG_TAG, "Employees table had wrong number of children");
            }
            DataSnapshot table;
            if (dataSnapshot.child(Configs.Administrators).hasChild(uid)) {
                mUserType = UserType.ADMINISTRATOR;
                table = dataSnapshot.child(Configs.Administrators);
            } else if (dataSnapshot.child(Configs.ResidentAssistants).hasChild(uid)) {
                mUserType = UserType.RESIDENT_ASSISTANT;
                table = dataSnapshot.child(Configs.ResidentAssistants);
            } else if (dataSnapshot.child(Configs.SophomoreAdvisors).hasChild(uid)) {
                mUserType = UserType.SOPHOMORE_ADVISOR;
                table = dataSnapshot.child(Configs.SophomoreAdvisors);
            } else if (dataSnapshot.child(Configs.GraduateAssistants).hasChild(uid)) {
                mUserType = UserType.GRADUATE_ASSISTANT;
                table = dataSnapshot.child(Configs.GraduateAssistants);
            } else {
                mFirebaseRef.child(Configs.Residents).addListenerForSingleValueEvent(new ResidentListener(uid));
                Log.d(Configs.LOG_TAG, "User <" + uid + "> not found in employees");
                return;
            }
            mRAEmail = table.child(uid).child(Configs.employeeEmail).getValue().toString();
            mActivity.launchMainActivity(mUserType, mRAEmail);
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            // ignored
        }
    }
}