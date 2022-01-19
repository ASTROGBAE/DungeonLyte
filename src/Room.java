import java.util.ArrayList;

public class Room extends Dungeon {

    private ArrayList<Door> doors = null;

    public Room(String _name, String _desc) {
        super(_desc);
        name = _name;
        doors = new ArrayList<Door>();
    }

    public void addDoor(String _desc, Room[] _link) {
        doors.add(new Door(_desc, _link));
    }
    
}
