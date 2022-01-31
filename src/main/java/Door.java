public class Door extends WorldObject {

    Room[] link = null;

    public Door(String _desc, Room[] _link) {
        super(getDoorName(_link), _desc); // dont need name, leave blank TODO change later?
        link = _link;
    }

    private static String getDoorName(Room[] _link) {
        String[] exits = {"no exit", "no exit"};
        for (int i = 0; i < 2; i ++) {if (_link[i] != null) {exits[i] = _link[i].getName();}} // protection against null rooms
        return "door: " + exits[0] + "," + exits[1];
    }
    
}
