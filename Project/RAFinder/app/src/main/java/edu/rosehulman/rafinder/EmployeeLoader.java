package edu.rosehulman.rafinder;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.rafinder.model.person.Administrator;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.GraduateAssistant;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;
import edu.rosehulman.rafinder.model.person.SophomoreAdvisor;

public class EmployeeLoader {
    private List<Employee> admins = new ArrayList<>();
    private List<Employee> ras = new ArrayList<>();
    private List<Employee> gas = new ArrayList<>();
    private List<Employee> sas = new ArrayList<>();
    private LoaderCallbacks callbacks;


    public EmployeeLoader(String url, LoaderCallbacks cb) {
        callbacks = cb;
        Firebase firebase = new Firebase(url + "/Employees");
        firebase.addListenerForSingleValueEvent(new EmployeeValueEventListener());
    }

    public List<Employee> getMyRAs() {
        List<Employee> myRAs = new ArrayList<>();
        for (Employee ra : ras) {
            if (ra.getHall().equals(callbacks.getMyHall())) {
                myRAs.add(ra);
            }
        }
        return myRAs;
    }

    public List<Employee> getMySAs() {
        List<Employee> mySAs = new ArrayList<>();
        for (Employee sa : sas) {
            if (sa.getHall().equals(callbacks.getMyHall())) {
                mySAs.add(sa);
            }
        }
        return mySAs;
    }

    public interface LoaderCallbacks {
        public String getMyHall();
    }

    public class EmployeeValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(MainActivity.LOG_TAG, "Loading Employee data...");
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                switch (child.getKey()) {
                case "Administrators":
                    for (DataSnapshot adminDS : child.getChildren()) {
                        Administrator admin = new Administrator(adminDS);
                        admins.add(admin);
                    }
                    break;
                case "Graduate Assistants":
                    for (DataSnapshot gaDS : child.getChildren()) {
                        GraduateAssistant ga = new GraduateAssistant(gaDS);
                        gas.add(ga);
                    }
                    break;
                case "Resident Assistants":
                    for (DataSnapshot raDS : child.getChildren()) {
                        ResidentAssistant ra = new ResidentAssistant(raDS);
                        ras.add(ra);
                    }
                    break;
                case "Sophomore Advisors":
                    for (DataSnapshot saDS : child.getChildren()) {
                        SophomoreAdvisor sa = new SophomoreAdvisor(saDS);
                        sas.add(sa);
                    }
                    break;
                }
            }
            Log.d(MainActivity.LOG_TAG, "Finished loading Employee data.");
            ((MainActivity) callbacks).updateInitialData();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

    public List<Employee> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Employee> admins) {
        this.admins = admins;
    }

    public List<Employee> getRAs() {
        return ras;
    }

    public void setRAs(List<Employee> ras) {
        this.ras = ras;
    }

    public List<Employee> getGas() {
        return gas;
    }

    public void setGas(List<Employee> gas) {
        this.gas = gas;
    }

    public List<Employee> getSAs() {
        return sas;
    }

    public void setSAs(List<Employee> sas) {
        this.sas = sas;
    }
}
