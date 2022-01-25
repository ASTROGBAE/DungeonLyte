public class Door extends WorldObject {

    Room[] link = null;

    public Door(String _desc, Room[] _link) {
        super(getDoorName(_link), _desc); // dont need name, leave blank TODO change later?
        link = _link;
    }

    private static String getDoorName(Room[] _link) {
        return "door: " + _link[0].getName() + "," + _link[1].getName();
    }
    
}
