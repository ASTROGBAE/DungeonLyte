import java.util.ArrayList;

public class Room extends WorldObjectNamed {

    private ArrayList<Door> doors = null;

    public Room(String _name, String _desc) {
        super(_name, _desc);
        doors = new ArrayList<Door>();
    }

    public void addDoor(Door _door) {
        doors.add(_door);
    }

    public String toString() {
        return "Door name: " + name + ", desc: " + desc + ", doors: " + doors.toString();
    }
    
}
