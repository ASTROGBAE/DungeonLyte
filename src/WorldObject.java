import java.util.ArrayList;

/**
 * WorldObject class, but with a no name (only a Doors, which does not need a name)
 */
public abstract class WorldObject {
    protected String name, desc;
    ArrayList<WorldObject> store;

    public WorldObject(String _name, String _desc) {
        // TODO add special regex checking to make sure name/description does not contain special characters like "/", etc...
        name = _name;
        desc = _desc;
        store = new ArrayList<WorldObject>();

    }

    // get methods

    public String getName() {
        return name;
    }
    public String getDescription() {
        return desc;
    }

    public void addToStore(WorldObject _add) {
        store.add(_add); 
    }

        // TODO add characteristics to all world objects, allowing them to hold world objects lower in precedence than them? 
        // TODO check precedence works afterwards 
        // make sure certain rules are upheld (like doors)

}
