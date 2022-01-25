import java.util.ArrayList;

public class Room extends Structure {

    private ArrayList<Door> doors = null;

    public Room(String _name, String _desc) {
        super(_desc);
        name = _name;
        doors = new ArrayList<Door>();
    }

    public void addDoor(Door _door) {
        doors.add(_door);
    }

    public String toString() {
        return "Door name: " + name + ", desc: " + desc + ", doors: " + doors.toString();
    }
    
}
