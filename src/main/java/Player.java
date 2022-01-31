import java.util.ArrayList;

public class Player extends WorldObject {

    ArrayList<Item> inventory;

    public Player(String _name, String _desc) {
        super(_name, _desc);
        inventory = new ArrayList<Item>();
    }
    
}
