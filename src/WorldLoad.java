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
        fileToScan = _fileToScan; // init file of scan 
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
            Drake curDrake = new Drake(_gameScan.nextLine()); // create drake object for current level
            String lastHigherTitle = "";

            if (curDrake.compareToHeader(level, headers) < 0) { // if scan of current drake is higher than the level, get title of currrent drake for further reference
                // order in terms of precedence, perhaps?
                lastHigherTitle = curDrake.getTitle(); // TODO not working with door obj?
            }
            toDungeonObject (curDrake, lastHigherTitle); // create dungeon item
        }
    }

    // takes string and format parameters for each value and creates an appopriate dungeon object (not returned)
    private void toDungeonObject (Drake _drake, String lastTitle) {
        if (_drake.isRoom()) {
            rooms.put(_drake.getTitle(), new Room(_drake.getTitle(), _drake.getTail())); // TODO refactor usage of title?
        }
        else if (!_drake.isRoom()) { // not room object
            Room lastRoom = rooms.get(lastTitle);
            if (_drake.getHead() == "Room") { // room object (add links)
                Room[] link = {lastRoom, rooms.get(_drake.getTitle())}; // get last room and room from Door title
                Door _door = new Door(_drake.getTitle(), link); // create door instance 
                for (Room r : link) {r.addDoor(_door);} // add door ref to both rooms
                nonRooms.put(_door.getName(), _door); // add door object to nonRooms list
            } else if (_drake.getHead() == "Item") { // not Room or Door object
                Item _item = new Item(_drake.getTitle(), _drake.getTail());
                nonRooms.put(_drake.getTitle(), _item);
                getWorldObject(lastTitle).addToStore(_item);
                // TODO refactor so that you can include Room/Door in 
            } else { // "Feature" option, TODO can only get once
                Feature _feature = new Feature(_drake.getTitle(), _drake.getTail());
                nonRooms.put(_drake.getTitle(), new Feature(_drake.getTitle(), _drake.getTail()));
                getWorldObject(lastTitle).addToStore(_feature);
                // TODO refactor this, collapse options!
                // TODO add "lock" option!
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
