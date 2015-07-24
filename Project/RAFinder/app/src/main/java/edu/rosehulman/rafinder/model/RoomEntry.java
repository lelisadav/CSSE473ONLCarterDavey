package edu.rosehulman.rafinder.model;

import edu.rosehulman.rafinder.controller.reslife.HallRosterFragment;
import edu.rosehulman.rafinder.model.person.Resident;

/**
 * An entry for one room of the {@link HallRosterFragment}.
 */
public class RoomEntry {

    private Resident[] residents;
    private String hallName;
    private String roomNumber;

    public RoomEntry() {

    }

    public Resident[] getResidents() {
        return residents;
    }

    public void setResidents(Resident[] residents) {
        this.residents = residents;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hall) {
        this.hallName = hall;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public static class Lobby extends RoomEntry {
        public Lobby() {
            super.roomNumber = "Lobby";
        }
    }
}
