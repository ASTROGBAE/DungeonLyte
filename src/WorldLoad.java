import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class WorldLoad {
    
    private String fileToScan;
    private String[] headers = {"Room", "Door", "Item", "Feature", "Lock", "Unlock"}; // all headers used in Dracolysh
    ArrayList<String> headerList;
    WorldObjectFactory factory;

    public WorldLoad(String _fileToScan) {
        fileToScan = "data/"+_fileToScan; // init file of scan 
        headerList = new ArrayList<String>();
        for (String s : headers) {headerList.add(s);} // scan in head objects // TODO make this more efficient? just makes head object arraylist before even seeing if valid...
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
                scanPerHead(gameStream, header); 
            }
            catch(IOException e)  {e.printStackTrace();}
        }
        return factory.getFirstRoom(); // return room
    }
    
    // scan game at a specific header level, remembering the last head in precedence (for linear referencing purposes)
    private void scanPerHead(FileInputStream _gameStream, String _header) {
        //returns true if there is another line to read
        // TODO fix! only runs once // TODO think this is fixed? check!
        Scanner gameScan = new Scanner(_gameStream); // new scan per level
        String higherObjName = "";
        while(gameScan.hasNextLine())  { // scanning for rooms
            String lineToDrake = gameScan.nextLine();
            if (!lineToDrake.equals("")) { // if line not empty (ignore empty lines)
                Drake drake = new Drake(lineToDrake); // create drake object for current level

                if (drake.compareToHeader(_header, headerList) < 0) { // if scan of current drake is higher than the level, get title of currrent drake for further reference
                    higherObjName = drake.getTitle(); // TODO not working with door obj?
                }
                if (drake.isHead(_header)) { // if right level to make object
                    factory.create(drake, higherObjName);// create dungeon item
            }
            }
        }
        gameScan.close(); // create new scanner, update rooms to return, close scanner
    }

    // TODO add functionality for comments?

    // TODO add functionality for var?


}
