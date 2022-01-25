import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class WorldLoad {
    
    
    private Map<String, Room> rooms;
    private ArrayList<String> headers = new ArrayList<String>();
    private String fileToScan;

    public WorldLoad(String _fileToScan) {
        fileToScan = _fileToScan; // init file of scan 
        rooms = new LinkedHashMap<String, Room>();
        String[] heads = {"Room", "Door"}; for (String s : heads) {headers.add(s);} // scan in head objects
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
                lastHigherTitle = curDrake.getTitle();
            }
            toDungeonObject (curDrake, lastHigherTitle); // create dungeon item
        }
    }

    // takes string and format parameters for each value and creates an appopriate dungeon object (not returned)
    private void toDungeonObject (Drake drakeToConvert, String lastTitle) {
        String head = drakeToConvert.getHead();
        String title = drakeToConvert.getTitle();
        String tail = drakeToConvert.getTail();
        if (head == "Room") {
            rooms.put(title, new Room(title, tail)); // TODO refactor usage of title?
        }
        else if (head == "Door") {
            Room[] link = {rooms.get(lastTitle), rooms.get(title)}; // get last room and subject room from title
            Door _door = new Door(tail, link); // create door instance 
            for (Room r : link) {r.addDoor(_door);} // add door ref to both rooms
        }
    }


}
