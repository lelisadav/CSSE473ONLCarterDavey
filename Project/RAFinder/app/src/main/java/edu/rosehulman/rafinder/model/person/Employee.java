package edu.rosehulman.rafinder.model.person;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Any Residence Life employee.
 */
public class Employee extends Resident {

    private String email;
    private int floor;
    private String hall;
    private String phoneNumber;
    private int room;
    private String status;
    private String statusDetail;
    private Firebase firebase;

    public Employee(String url){
        super(url);
        this.firebase=new Firebase(url);
        this.firebase.addChildEventListener(new ChildrenListener(this));
    }

    public Employee(String name,
                    String email,
                    int floor,
                    String hall,
                    String phoneNumber,
                    int room,
                    String status,
                    String statusDetail) {
        super(name);
        this.email = email;
        this.floor = floor;
        this.hall = hall;
        this.phoneNumber = phoneNumber;
        this.room = room;
        this.status = status;
        this.statusDetail = statusDetail;
    }

    public String getEmail() {
        return email;
    }

    public Firebase getFirebase() {
        return firebase;
    }

    public void setFirebase(Firebase firebase) {
        this.firebase = firebase;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }


//    public static ResidentAssistant convertToRA(Employee e){
//        ResidentAssistant ra= new ResidentAssistant(e.getFirebase().toString());
//        ra.setEmail(e.getEmail());
//        ra.setFloor(e.getFloor());
//        ra.setHall(e.getHall());
//        ra.setPhoneNumber(e.getPhoneNumber());
//        ra.setStatus(e.getStatus());
//        ra.setStatusDetail(e.getStatusDetail());
//        ra.setName(e.getName());
//        return ra;
//    }
    /**
     * Milestone listener
     */
    class ChildrenListener implements ChildEventListener {
        private Employee employee;

        public ChildrenListener(Employee employee) {
            this.employee = employee;
        }

        /**
         * Do nothing
         */
        public void onCancelled(FirebaseError arg0) {
            // TODO Auto-generated method stub.
        }

        /**
         * Fills in the new milestone's properties including the milestone name,
         * description and list of tasks for that milestone
         */
        public void onChildAdded(DataSnapshot arg0, String arg1) {
            if (arg0.getKey().equals("email")){
                this.employee.setEmail(arg0.getValue(String.class));
            }
            else if (arg0.getKey().equals("floor")){
                this.employee.setFloor(arg0.getValue(int.class));
            }
            else if (arg0.getKey().equals("hall")){
                this.employee.setHall(arg0.getValue(String.class));
            }
            else if (arg0.getKey().equals("phoneNumber")){
                this.employee.setPhoneNumber(arg0.getValue(String.class));
            }
            else if (arg0.getKey().equals("room")){
                this.employee.setRoom(arg0.getValue(int.class));
            }
            else if (arg0.getKey().equals("status")){
                this.employee.setStatus(arg0.getValue(String.class));
            }
            else if (arg0.getKey().equals("statusDetail")){
                this.employee.setStatusDetail(arg0.getValue(String.class));
            }
            else if (arg0.getKey().equals("name")){
                this.employee.setName(arg0.getValue(String.class));
            }




//            if (arg0.getKey().equals("name")) {
//                this.milestone.setName(arg0.getValue(String.class));
//                if (this.milestone.getChangeNotifier() != null) {
//                    this.milestone.getChangeNotifier().onChange();
//                }
//            } else if (arg0.getKey().equals("description")) {
//                this.milestone.setDescription(arg0.getValue(String.class));
//            } else if (arg0.getKey().equals("due_date")) {
//                this.milestone.setDueDate(new DueDate(arg0.getValue(String.class)));
//            } else if (arg0.getKey().equals("task_percent")) {
//                this.milestone.setTaskPercent(arg0.getValue(Integer.class));
//            } else if (arg0.getKey().equals("tasks")) {
//                for (DataSnapshot child : arg0.getChildren()) {
//                    Task t = new Task(child.getRef().toString());
//
//                    if (!this.milestone.tasks.contains(t)) {
//                        t.setParentNames(this.milestone.parentProjectName, this.milestone.getName());
//                        this.milestone.tasks.add(t);
//                    }
//                }
//            }else if (arg0.getKey().equals("burndown_data")){
//                for (DataSnapshot child : arg0.getChildren()){
//                    BurndownObject bo=new BurndownObject(child.getRef().toString());
//                    bo.setTimeStamp(new Long(child.getKey()));
//                    bo.setChangeNotifier(this.milestone.burndownObjectChangeNotifier);
//                    if (!this.milestone.burndownData.getBurndownObjects().contains(bo)){
//                        this.milestone.burndownData.addBurndownObject(bo);
//                    }
//                }
//            }
        }

        /**
         * This will be called when the milestone data in Firebased is updated
         */
        public void onChildChanged(DataSnapshot arg0, String arg1) {
            // TODO Auto-generated method stub.

        }

        /**
         * Might do something here for the tablet
         */
        public void onChildMoved(DataSnapshot arg0, String arg1) {
            // nothing
        }

        /**
         * Do nothing
         */
        public void onChildRemoved(DataSnapshot arg0) {
            // nothing
        }
    }


}
