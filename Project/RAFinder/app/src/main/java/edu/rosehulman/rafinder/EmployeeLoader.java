package edu.rosehulman.rafinder;

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
    private String url;
    private List<Employee> admins = new ArrayList<>();
    private List<Employee> ras = new ArrayList<>();
    private List<Employee> gas = new ArrayList<>();
    private List<Employee> sas = new ArrayList<>();

    public EmployeeLoader(String url) {
        this.url = url;
        Firebase firebase = new Firebase(this.url);
        firebase.addListenerForSingleValueEvent(new EmployeeValueEventListener());
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

    public class EmployeeValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                switch (child.getKey()) {
                case "Administrators":
                    for (DataSnapshot adminDS : child.getChildren()) {
                        Administrator admin = new Administrator(url + adminDS.getRef().getPath());
                        admins.add(admin);
                    }
                    break;
                case "Graduate Assistants":
                    for (DataSnapshot gaDS : child.getChildren()) {
                        GraduateAssistant ga = new GraduateAssistant(url + gaDS.getRef().getPath());
                        gas.add(ga);
                    }
                    break;
                case "Resident Assistants":
                    for (DataSnapshot raDS : child.getChildren()) {
                        ResidentAssistant ra = new ResidentAssistant(url + raDS.getRef().getPath());
                        ras.add(ra);
                    }
                    break;
                case "Sophomore Advisors":
                    for (DataSnapshot saDS : child.getChildren()) {
                        SophomoreAdvisor sa = new SophomoreAdvisor(url + saDS.getRef().getPath());
                        sas.add(sa);
                    }
                    break;
                }
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
