package com.astrogbae;

public class Lock extends WorldObject {

    protected boolean locked;
    protected WorldObject unlockObject;

    /**
     * unlocked until unlock
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

    public void addUnlockObject(WorldObject _unlock) {
        // TODO perhaps add "lockable" condition/parent to throw type cheking?
        if (_unlock instanceof Item || _unlock instanceof Feature) {
            unlockObject = _unlock;
        }
        //else {bad!} TODO, throw error?
    }
    
}
