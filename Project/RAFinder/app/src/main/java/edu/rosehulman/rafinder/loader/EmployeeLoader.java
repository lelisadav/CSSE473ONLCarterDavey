package edu.rosehulman.rafinder.loader;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.rafinder.ConfigKeys;
import edu.rosehulman.rafinder.model.person.Administrator;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.GraduateAssistant;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;
import edu.rosehulman.rafinder.model.person.SophomoreAdvisor;

@SuppressWarnings("unused")
public class EmployeeLoader {
    private List<Employee> admins = new ArrayList<>();
    private List<Employee> ras = new ArrayList<>();
    private List<Employee> gas = new ArrayList<>();
    private List<Employee> sas = new ArrayList<>();
    private final EmployeeLoaderListener callbacks;


    public EmployeeLoader(String url, EmployeeLoaderListener cb) {
        callbacks = cb;
        Firebase firebase = new Firebase(url + "/" + ConfigKeys.Employees);
        Log.d(ConfigKeys.LOG_TAG, "Loading Employee data...");
        firebase.addListenerForSingleValueEvent(new EmployeeValueEventListener());
    }

    public interface EmployeeLoaderListener {
        public String getMyHall();
        public void onEmployeeLoadingComplete();
    }

    private class EmployeeValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                switch (child.getKey()) {
                case ConfigKeys.Administrators:
                    for (DataSnapshot adminDS : child.getChildren()) {
                        Administrator admin = new Administrator(adminDS);
                        admins.add(admin);
                    }
                    break;
                case ConfigKeys.GraduateAssistants:
                    for (DataSnapshot gaDS : child.getChildren()) {
                        GraduateAssistant ga = new GraduateAssistant(gaDS);
                        gas.add(ga);
                    }
                    break;
                case ConfigKeys.ResidentAssistants:
                    for (DataSnapshot raDS : child.getChildren()) {
                        ResidentAssistant ra = new ResidentAssistant(raDS);
                        ras.add(ra);
                    }
                    break;
                case ConfigKeys.SophomoreAdvisors:
                    for (DataSnapshot saDS : child.getChildren()) {
                        SophomoreAdvisor sa = new SophomoreAdvisor(saDS);
                        sas.add(sa);
                    }
                    break;
                }
            }
            Log.d(ConfigKeys.LOG_TAG, "Finished loading Employee data.");
            callbacks.onEmployeeLoadingComplete();
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

    public List<Employee> getGAs() {
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
