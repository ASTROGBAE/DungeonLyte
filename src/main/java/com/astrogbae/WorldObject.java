package com.astrogbae;

import java.util.ArrayList;

/**
 * WorldObject class, but with a no name (only a Doors, which does not need a name)
 */
public abstract class WorldObject {
    protected String name, desc;
    ArrayList<Lock> locks;
    ArrayList<WorldObject> store;

    public WorldObject(String _name, String _desc) {
        // TODO add special regex checking to make sure name/description does not contain special characters like "/", etc...
        name = _name;
        desc = _desc;
        store = new ArrayList<WorldObject>();
        locks = new ArrayList<Lock>();

    }

    // get methods

    public String getName() {
        return name;
    }
    public String getDescription() {
        return desc;
    }

    public ArrayList<WorldObject> getStore() {
        return store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldObject that = (WorldObject) o;
        return firstName.equals(that.firstName) &&
          lastName.equals(that.lastName);
    }

    // boolean checks

    /**
     * check if any lock object conneted to this WorldObject is locked
     * @return return true if any connected lock is locked, false otherwise
     */
    public Boolean isLocked() {
        for (Lock l : locks) {
            if (l.isLocked()) {return true;}
        }
        return false;
    }

    // construction methods

    public void addToStore(WorldObject _add) {
        store.add(_add); 
    }

    public void addLock(Lock _add) {
        locks.add(_add); 
    }

        // TODO add characteristics to all world objects, allowing them to hold world objects lower in precedence than them? 
        // TODO check precedence works afterwards 
        // make sure certain rules are upheld (like doors)

}
