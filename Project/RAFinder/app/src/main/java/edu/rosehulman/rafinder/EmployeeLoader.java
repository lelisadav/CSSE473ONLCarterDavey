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

/**
 * Created by daveyle on 8/7/2015.
 */
public class EmployeeLoader {
    Firebase firebase;
    String url;
    List<Employee> admins=new ArrayList<>();
    List<Employee> ras=new ArrayList<>();
    List<Employee> gas=new ArrayList<>();
    List<Employee> sas=new ArrayList<>();
    public EmployeeLoader(String url){
        this.url=url;
        firebase=new Firebase(this.url);
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

    public class EmployeeValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                if (child.getKey().equals("Administrators")){
                    for (DataSnapshot adminDS:child.getChildren()){
                        Administrator admin=new Administrator(adminDS.toString());
                        admins.add(admin);
                    }


                }else if (child.getKey().equals("Graduate Assistants")){
                    for (DataSnapshot gaDS:child.getChildren()){
                        GraduateAssistant ga=new GraduateAssistant(gaDS.toString());
                        gas.add(ga);
                    }

                }else if (child.getKey().equals("Resident Assistants")){
                    for (DataSnapshot raDS:child.getChildren()){
                        ResidentAssistant ra=new ResidentAssistant(raDS.toString());
                        ras.add(ra);
                    }

                }else if (child.getKey().equals("Sophomore Advisors")){
                    for (DataSnapshot saDS:child.getChildren()){
                        SophomoreAdvisor sa=new SophomoreAdvisor(saDS.toString());
                        sas.add(sa);
                    }

                }else {

                }
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
