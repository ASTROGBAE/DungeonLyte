import java.util.LinkedHashMap;
import java.util.Map;

public class WorldObjectFactory {
    // TODO integrate header objects into code (not yet done, class unused)
    // TODO make sure class encapsulation in each class...

    Map<String,WorldObject> worldObjectMap;

    public WorldObjectFactory() {
        worldObjectMap = new LinkedHashMap<String,WorldObject>();
    }

    // TODO add factory design pattern patterning (loookup)

    public Room getFirstRoom() {
        return (Room)worldObjectMap.entrySet().iterator().next().getValue();
    }

    // return world object, null if it does not exist
    private WorldObject getObject(String name) {
        return worldObjectMap.get(name);
    }

    // add WorldObject to map
    private void addObjectToMap(WorldObject toAdd) {
        worldObjectMap.put(toAdd.getName(), toAdd);
    }
    
    // takes string and format parameters for each value and creates an appopriate dungeon object (not returned)
    public WorldObject create (Drake _drake, String higherName) {
        if (_drake != null) {
            if (_drake.isHead("Room")) {
                Room room = new Room(_drake.getTitle(), _drake.getTail()); 
                addObjectToMap(room); return room;
            }

            WorldObject higherObject = getObject(higherName);
            if (higherObject != null) { // lower than room, requires a higher object to work

                if (_drake.isHead("Door")) { // door object, will throw error if lastObj and title are NOT door objects TODO add proper type checking later?
                    Room titleRoom = (Room)getObject(_drake.getTitle());
                    Room[] link = {titleRoom, (Room)higherObject}; // get last room and room from Door title, typecheck they are Room objects
                    Door door = new Door(_drake.getTitle(), link); // create door instance 
                    door.setLink(link);
                    titleRoom.addDoor(door); ((Room)higherObject).addDoor(door);
                    addObjectToMap(door); return door;
                }

                else if (_drake.isHead("Item")) { // not Room or Door object
                    Item item = new Item(_drake.getTitle(), _drake.getTail()); // create item object
                    higherObject.addToStore(item);
                    addObjectToMap(item); return item;
                } 

                else if (_drake.isHead("Feature")) { // "Feature" option, TODO can only get once
                    Feature feature = new Feature(_drake.getTitle(), _drake.getTail());
                    higherObject.addToStore(feature);
                    addObjectToMap(feature); return feature;
                }

                else if (_drake.isHead("Lock")) { // "Lock" option
                    Lock lock = new Lock(_drake.getTitle(), _drake.getTail()); // create Lock object
                    String lockDescription = _drake.getTail(); // TODO use this lock description in place of the regular when needed?
                    higherObject.addLock(lock); // add lock to higher obj
                    addObjectToMap(lock); return lock;
                }

                else if (_drake.isHead("Unlock")) { // "Unlock" option, no actual unlock obj, will add item or feature to unlock condition
                // TODO add option for doors or rooms for if they are discovered/travelled or not?
                Lock higherLock = (Lock)higherObject;
                WorldObject unlockObject = getObject(_drake.getTitle());
                String unlockDescription = _drake.getTail(); // TODO use this lock description in place of the regular when needed?
                // TODO figure out a system for lock and unlock descriptions?
                higherLock.addUnlockObject(unlockObject);
                } 
            }
        }
        // invalid Drake object!
        System.out.println("invalid Drake object!: " + _drake);
        return null;
    }
}
