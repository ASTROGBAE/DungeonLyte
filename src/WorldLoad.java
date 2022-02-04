import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class WorldLoad {
    
    private String fileToScan;
    private String[] headers = {"Room", "Door", "Item", "Feature", "Lock", "Unlock"}; // all headers used in Dracolysh
    Map<String,WorldObject> worldObjectMap;
    WorldObjectFactory factory;

    public WorldLoad(String _fileToScan) {
        fileToScan = "data/"+_fileToScan; // init file of scan 
        worldObjectMap = new LinkedHashMap<String,WorldObject>();
        factory = new WorldObjectFactory();
        // TODO implement non-room categories! (wah?)
    }

    /**
     * scans game.txt for all possible WorldObjects
     * @return room object that player begins in with reference connections to all over WorldObject's in the game
     */
    // TODO init as an anonymous function???
    public Room loadGame() {

        for (String header : headers) { // perform a seperate scan per header object
            try { 
                FileInputStream gameStream =new FileInputStream(fileToScan);
                scanLinesPerLevel(gameStream, header); 
            }
            catch(IOException e)  {e.printStackTrace();}
        }
        return null; // return room
    }
    
    // scan game at a specific header level, remembering the last head in precedence (for linear referencing purposes)
    private void scanLinesPerLevel(FileInputStream _gameStream, String _header) {
        //returns true if there is another line to read
        // TODO fix! only runs once // TODO think this is fixed? check!
        Scanner gameScan = new Scanner(_gameStream); // new scan per level
        String higherObjName = "";
        while(gameScan.hasNextLine())  { // scanning for rooms
            String lineToDrake = gameScan.nextLine();
            if (!lineToDrake.equals("")) { // if line not empty (ignore empty lines)
                Drake curDrake = new Drake(lineToDrake); // create drake object for current level

                if (curDrake.compareToHeader(_header, headers) < 0) { // if scan of current drake is higher than the level, get title of currrent drake for further reference
                    higherObjName = curDrake.getTitle(); // TODO not working with door obj?
                }
                if (curDrake.isHead(_header)) { // if right level to make object
                    createWorldObject (curDrake, higherObjName, _worldObjectsMap); // create dungeon item
            }
            }
        }
        gameScan.close(); // create new scanner, update rooms to return, close scanner
    }

    private WorldObject getWorldObject(String title, Map<String, WorldObject> _worldObjectsMap) {
        if (_worldObjectsMap.containsKey(title)) {
            return _worldObjectsMap.get(title);
        }
        return null;
    }

    private Map<String, Headers> getHeaderMap() {
        Headers toGetHeaders = new Headers("Room"); // TODO make this more elegent? Kinda weird way of getting the list...
        String[] heads = toGetHeaders.getHeaderArr(); // TODO idk, make a 'headers' object to return instead? this is kinda shite...
        // TODO maybe have a 'headers' class instead of just heads, use that instead and compare strings
        Map<String, Headers> map = new LinkedHashMap<String, Headers>(); // map to return
        for (String h : heads) {map.put(h, new Head(h));}
        return map;
    }

    // TODO add functionality for comments?

    // TODO add functionality for var?


}
