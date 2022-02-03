import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class WorldLoad {
    
    private String fileToScan;

    public WorldLoad(String _fileToScan) {
        fileToScan = "data/"+_fileToScan; // init file of scan 
        // TODO implement non-room categories! (wah?)
    }

    /**
     * scans game.txt for all possible WorldObjects
     * @return room object that player begins in with reference connections to all over WorldObject's in the game
     */
    // TODO init as an anonymous function???
    public Room loadGame() {
        Map<String, WorldObject> worldObjectsMap = new LinkedHashMap<String, WorldObject>(); // define worldobject maps to be used during loading and linking of objects, disgarded later
        Map<String, Header> headers = new LinkedHashMap<String, Header>(); 
        for (String header : headers) { // perform a seperate scan per header object
            try { 
                FileInputStream gameStream =new FileInputStream(fileToScan);
                scanLinesPerLevel(gameStream, header, worldObjectsMap); 
            }
            catch(IOException e)  {e.printStackTrace();}
        }
        return (Room) worldObjectsMap.entrySet().iterator().next().getValue(); // return first object in map, can only syntatically be a Room object
    }
    
    // scan game at a specific header level, remembering the last head in precedence (for linear referencing purposes)
    private void scanLinesPerLevel(FileInputStream _gameStream, String _level, Map<String, WorldObject> _worldObjectsMap) {
        //returns true if there is another line to read
        // TODO fix! only runs once // TODO think this is fixed? check!
        Scanner gameScan = new Scanner(_gameStream); // new scan per level
        String higherObjName = "";
        while(gameScan.hasNextLine())  { // scanning for rooms
            String lineToDrake = gameScan.nextLine();
            if (!lineToDrake.equals("")) { // if line not empty (ignore empty lines)
                Drake curDrake = new Drake(lineToDrake); // create drake object for current level

                if (curDrake.compareToHeader(_level, headers) < 0) { // if scan of current drake is higher than the level, get title of currrent drake for further reference
                    higherObjName = curDrake.getTitle(); // TODO not working with door obj?
                }
                if (curDrake.isHead(_level)) { // if right level to make object
                    createWorldObject (curDrake, higherObjName, _worldObjectsMap); // create dungeon item
            }
            }
        }
        gameScan.close(); // create new scanner, update rooms to return, close scanner
    }

    // takes string and format parameters for each value and creates an appopriate dungeon object (not returned)
    private void createWorldObject (Drake _drake, String _higherObjName, Map<String, WorldObject> _worldObjectsMap) {
        if (_drake != null) {
            // TODO figure out if first used drake is not a head, causes error (when lastTitle == "" here)
            /**
             * Creation of Rooms
             */
            if (_drake.isHead("Room")) {
                Room _room = new Room(_drake.getTitle(), _drake.getTail());
                _worldObjectsMap.put(_drake.getTitle(), _room); // TODO refactor usage of title?
            }
            if (!_higherObjName.equals("")) { // if drake is below door level, need a previous object to be read in
                WorldObject lastObj = _worldObjectsMap.get(_higherObjName);
                /**
                 * Creation of Door, Item or Features
                 */
                if (_drake.isHead("Door")) { // door object, will throw error if lastObj and title are NOT door objects TODO add proper type checking later?
                    Room[] link = {(Room)lastObj, (Room)_worldObjectsMap.get(_drake.getTitle())}; // get last room and room from Door title, typecheck they are Room objects
                    Door _door = new Door(_drake.getTitle(), link); // create door instance 
                    for (Room r : link) {r.addDoor(_door);} // add door ref to both rooms
                    // TODO what to do if multiple doors connect the same doors? should a new door connector object be used to make sure only one works or?
                } 
                else if (_drake.isHead("Item")) { // not Room or Door object
                    Item _item = new Item(_drake.getTitle(), _drake.getTail()); // create item object
                    _worldObjectsMap.put(_drake.getTitle(), _item); // put in worldObjects list for later reference
                    getWorldObject(_higherObjName, _worldObjectsMap).addToStore(_item); // add to parent worldObject
                    // TODO refactor so that you can include Room/Door in 
                } 
                else if (_drake.isHead("Feature")) { // "Feature" option, TODO can only get once
                    Feature _feature = new Feature(_drake.getTitle(), _drake.getTail());
                    _worldObjectsMap.put(_drake.getTitle(), new Feature(_drake.getTitle(), _drake.getTail())); // put in worldObjects list for later reference
                    getWorldObject(_higherObjName, _worldObjectsMap).addToStore(_feature); // if not full, add
                }
                else if (_drake.isHead("Lock")) { // "Lock" option
                    Lock _lock = new Lock(_drake.getTitle(), _drake.getTail()); // create Lock object
                    _worldObjectsMap.put(_drake.getTitle(), new Feature(_drake.getTitle(), _drake.getTail())); // put in worldObjects list for later reference
                    getWorldObject(_higherObjName, _worldObjectsMap).addLock(_lock); // if not full, add
                    // TODO refactor this, collapse options!    
                }
                else if (_drake.isHead("Unlock")) { // "Unlock" option, no actual unlock obj, will add item or feature to unlock condition
                // TODO add option for doors or rooms for if they are discovered/travelled or not?
                    Lock parentLock = (Lock) getWorldObject(_higherObjName, _worldObjectsMap); // get parent lock
                    WorldObject unlock = getWorldObject(_drake.getTitle(), _worldObjectsMap); // get unlock world object
                    parentLock.addUnlockObject(unlock); // add unlock object ref to lock
                    
                } 
            } // TODO refactor all these, collapse options!   
        }
        else {
            // invalid Drake object!
            System.out.println("invalid Drake object!: " + _drake);
        }
    }

    private WorldObject getWorldObject(String title, Map<String, WorldObject> _worldObjectsMap) {
        if (_worldObjectsMap.containsKey(title)) {
            return _worldObjectsMap.get(title);
        }
        return null;
    }

    private Map<String, Header> getHeaderMap() {
        Header toGetHeaders = new Header("Room"); // TODO make this more elegent? Kinda weird way of getting the list...
        String[] heads = toGetHeaders.getHeaderArr(); // TODO idk, make a 'headers' object to return instead? this is kinda shite...
        // TODO maybe have a 'headers' class instead of just heads, use that instead and compare strings
        Map<String, Header> map = new LinkedHashMap<String, Header>(); // map to return
        for (String h : heads) {map.put(h, new Head(h));}
        return map;
    }

    // TODO add functionality for comments?

    // TODO add functionality for var?


}
