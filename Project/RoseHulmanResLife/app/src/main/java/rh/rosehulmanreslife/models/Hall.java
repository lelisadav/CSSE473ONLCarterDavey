package rh.rosehulmanreslife.models;

/**
 * Created by daveyle on 7/11/2015.
 */
public class Hall implements SearchResultItem {
    private String name;



    private Floor[] floors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Floor[] getFloors() {
        return floors;
    }

    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }
    public int[] getFloorNumbers(){
        int[] floorNumbers=new int[floors.length];
        for (int i=0; i<floorNumbers.length; i++){
            floorNumbers[i]=floors[i].getNumber();
        }
        return floorNumbers;
    }
    public int getFloorCount(){
        return floors.length;
    }
}
