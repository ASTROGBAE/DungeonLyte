import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class WorldLoad {
    
    
    private Map<String, Room> rooms;
    private Map<String, WorldObject> nonRooms;
    private ArrayList<String> headers = new ArrayList<String>();
    private String fileToScan;

    public WorldLoad(String _fileToScan) {
        fileToScan = "data/"+_fileToScan; // init file of scan 
        rooms = new LinkedHashMap<String, Room>();
        nonRooms = new LinkedHashMap<String, WorldObject>();
        // TODO implement non-room categories!
        String[] heads = {"Room", "Door", "Item", "Feature", "Lock"}; for (String s : heads) {headers.add(s);} // scan in head objects
    }

    // return first room with connected objects 
    // TODO init as an anonymous function???
    public Room loadGame() {
        try { FileInputStream gameStream =new FileInputStream(fileToScan);
            for (String header : headers) { // perform a seperate scan per header object
                Scanner gameScan = new Scanner(gameStream); levelScan(header, gameScan); gameScan.close(); // create new scanner, update rooms to return, close scanner
            }
            return rooms.entrySet().iterator().next().getValue();
        }
        catch(IOException e)  {e.printStackTrace();}
        return null; // TODO add optional clause in case IO doesn't work?
    }
    
    // scan game at a specific header level, remembering the last head in precedence (for linear referencing purposes)
    private void levelScan(String level, Scanner _gameScan) {
        //returns true if there is another line to read
        while(_gameScan.hasNextLine())  { // scanning for rooms
            String drakeParam = _gameScan.nextLine();
            Drake curDrake = new Drake(drakeParam); // create drake object for current level
            String higherObjName = "";

            if (curDrake.compareToHeader(level, headers) < 0) { // if scan of current drake is higher than the level, get title of currrent drake for further reference
                // order in terms of precedence, perhaps?
                higherObjName = curDrake.getTitle(); // TODO not working with door obj?
            }
            drakeToObject (curDrake, higherObjName); // create dungeon item
        }
    }

    // takes string and format parameters for each value and creates an appopriate dungeon object (not returned)
    private void drakeToObject (Drake _drake, String _higherObjName) {
        // TODO figure out if first used drake is not a head, causes error (when lastTitle == "" here)
        if (_drake.isRoom()) {
            rooms.put(_drake.getTitle(), new Room(_drake.getTitle(), _drake.getTail())); // TODO refactor usage of title?
        }
        else if (!_drake.isRoom()) { // not room object
            if (_higherObjName == "") {System.out.println("invalid Dracolysh syntax! Door must always bee the first drake in a script.");}
            Room lastRoom = rooms.get(_higherObjName);
            if (_drake.getHead().equals("Door")) { // door object (add links)
                Room[] link = {lastRoom, rooms.get(_drake.getTitle())}; // get last room and room from Door title
                Door _door = new Door(_drake.getTitle(), link); // create door instance 
                for (Room r : link) {r.addDoor(_door);} // add door ref to both rooms
                nonRooms.put(_door.getName(), _door); // add door object to nonRooms list
            } else if (_drake.getHead().equals("Item")) { // not Room or Door object
                Item _item = new Item(_drake.getTitle(), _drake.getTail());
                nonRooms.put(_drake.getTitle(), _item);
                getWorldObject(_higherObjName).addToStore(_item);
                // TODO refactor so that you can include Room/Door in 
            } else if (_drake.getHead().equals("Feature")) { // "Feature" option, TODO can only get once
                Feature _feature = new Feature(_drake.getTitle(), _drake.getTail());
                nonRooms.put(_drake.getTitle(), new Feature(_drake.getTitle(), _drake.getTail()));
                getWorldObject(_higherObjName).addToStore(_feature); // if not full, add
                // TODO refactor this, collapse options!
                // TODO add "lock" option!
            } else {
                // invalid Drake object!
            }
        }
    }

    private WorldObject getWorldObject(String title) {
        if (rooms.containsKey(title)) {
            return rooms.get(title);
        } else if (nonRooms.containsKey(title)) {
            return nonRooms.get(title);
        }
        return null;
    }

    // TODO add functionality for comments?

    // TODO add functionality for var?


}
