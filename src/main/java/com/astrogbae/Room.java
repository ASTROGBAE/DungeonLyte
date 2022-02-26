package com.astrogbae;

import java.util.ArrayList;

public class Room extends WorldObject {

    private ArrayList<Door> doors = null;

    public Room(String _name, String _desc) {
        super(_name, _desc);
        doors = new ArrayList<Door>();
    }

    public void addDoor(Door _door) {
        doors.add(_door);
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public String toString() {
        return "Door name: " + name + ", desc: " + desc + ", doors: " + doors.toString();
    }
    
}
