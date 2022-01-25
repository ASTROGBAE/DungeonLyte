public class Door extends WorldObject {

    Room[] link = null;

    public Door(String _desc, Room[] _link) {
        super("", _desc); // dont need name, leave blank TODO change later?
        link = _link;
    }
    
}
