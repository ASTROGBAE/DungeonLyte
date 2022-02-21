public class Lock extends WorldObject {

    protected boolean locked;
    protected WorldObject keyObject;

    /**
     * Locked until unlocked
     * @param _name
     * @param _desc
     */
    public Lock(String _name, String _desc) {
        super(_name, _desc);
        locked = false;
    }

    public boolean lockIsLocked() {
        return locked;
    }

    public void addkeyObject(WorldObject _key) {
        // TODO perhaps add "lockable" condition/parent to throw type cheking?
        if (_key instanceof Item || _key instanceof Feature) {
            keyObject = _key;
        }
        //else {bad!} TODO, throw error?
    }
    
}
