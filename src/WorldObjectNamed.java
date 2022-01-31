
/**
 * WorldObject class, but with a name (all world objects except Doors, which do not need a name)
 */
public abstract class WorldObjectNamed extends WorldObject {
    protected String name;

    public WorldObjectNamed(String _name, String _desc) {
        super(_desc);
        name = _name;
    }

    // get methods
    public String getName() {
        return name;
    }

}
