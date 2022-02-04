import java.util.LinkedHashMap;
import java.util.Map;

public class WorldObjectFactory {
    // TODO integrate header objects into code (not yet done, class unused)
    private String[] headArr = {"Room", "Door", "Item", "Feature", "Lock", "Unlock"}; // all headers used in Dracolysh
    private Map<String, WorldObject> worldObjectsMap;
    // TODO make sure class encapsulation in each class...

    public WorldObjectFactory(LinkedHashMap<String, WorldObject> _worldObjectsMap) {
        worldObjectsMap = _worldObjectsMap; // define worldobject maps to be used during loading and linking of objects, disgarded later
    }

    // TODO add Map<String, WorldObject> _worldObjectsMap

    //
    private WorldObject getLinkedWorldObject (WorldObject _current, WorldObject _title, WorldObject _higher) {
        if (_current != null && _title != null) {
            /**
             * Creation of Door, Item or Features
             */
            if ( _higher != null && _current instanceof Door) {
                if (_current instanceof Room) {
                    return _current;
                }
                else if (_current instanceof Door && _title != null && _higher != null) {
                    return getLinkedDoor ((Door)_current, (Room)_title, (Room)_higher);
                }
                else if (_current instanceof Item && _higher != null) {
                    return getLinkedItem ((Item)_current, _higher);
                }
                else if (_current instanceof Feature && _higher != null) {
                    return getLinkedFeature ((Feature)_current, _higher);
                }
                else if (_current instanceof Lock && _higher != null) {
                    return getLinkedLock ((Lock)_current, _higher);
                }
            }
        }
        return null;
    }

    private Door getLinkedDoor (Door _door, Room firstRoom, Room secondRoom) {
        Room[] link = {firstRoom, secondRoom}; // get last room and room from Door title, typecheck they are Room objects
        _door.setLink(link);
        firstRoom.addDoor(_door); secondRoom.addDoor(_door);
        return _door;
    }

    private Item getLinkedItem (Item _current, WorldObject _higherObject) {
        if (_current instanceof Item) {
            _higherObject.addToStore(_current);
            return _current;
        }
        return null;
    }

    private Feature getLinkedFeature (Feature _current, WorldObject _higherObject) {
        if ( _current instanceof Feature) {
            _higherObject.addToStore(_current);
            return _current;
        }
        return null;
    }

    private Lock getLinkedLock (Lock _lock, WorldObject _toLock) {
        _toLock.addLock(_lock);
        return _lock;
    }

    private void linkUnlock (Lock _lock, WorldObject _unlock) {
        _lock.addUnlockObject(_unlock);
    }
    
    // takes string and format parameters for each value and creates an appopriate dungeon object (not returned)
    private WorldObject drakeToWorldObject (Drake _drake) {
        if (_drake != null) {
            if (_drake.isHead("Room")) {
                return new Room(_drake.getTitle(), _drake.getTail());
            }
            else if (_drake.isHead("Door")) { // door object, will throw error if lastObj and title are NOT door objects TODO add proper type checking later?
                return new Door(_drake.getTitle(), null); // create door instance 
            } 
            else if (_drake.isHead("Item")) { // not Room or Door object
                return new Item(_drake.getTitle(), _drake.getTail()); // create item object
            } 
            else if (_drake.isHead("Feature")) { // "Feature" option, TODO can only get once
                return new Feature(_drake.getTitle(), _drake.getTail());
            }
            else if (_drake.isHead("Lock")) { // "Lock" option
                return new Lock(_drake.getTitle(), _drake.getTail()); // create Lock object    
            }
            else if (_drake.isHead("Unlock")) { // "Unlock" option, no actual unlock obj, will add item or feature to unlock condition
            // TODO add option for doors or rooms for if they are discovered/travelled or not?
            } 
        }
        // invalid Drake object!
        System.out.println("invalid Drake object!: " + _drake);
        return null;
    }
}
