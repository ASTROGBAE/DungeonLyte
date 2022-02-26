package com.astrogbae;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class WorldLoad {

    private String[] headers = {"Room", "Door", "Item", "Feature", "Lock", "Unlock"}; // all headers used in Dracolysh
    ArrayList<String> headerList;
    WorldObjectFactory factory;

    public WorldLoad() { 
        headerList = new ArrayList<String>();
        for (String s : headers) {headerList.add(s);} // scan in head objects // TODO make this more efficient? just makes head object arraylist before even seeing if valid...
        factory = new WorldObjectFactory();
        // TODO implement non-room categories! (wah?)
    }

    /**
     * scans game.txt for all possible WorldObjects
     * @return room object that player begins in with reference connections to all over WorldObject's in the game
     */
    // TODO currently not in use, fix?
    // TODO init as an anonymous function???
    public Room loadFirstRoom(String _sourceText) { // param: // string code in Dracolysh to translate

        if (_sourceText == null || _sourceText.equals("")) { // if sourceText is null or "", return null
            return null;
        }
        else { // else, if it contains nonempty chars...
            for (String header : headers) { // perform a seperate scan per header object
                scan(header, _sourceText); 
            }
            return factory.getFirstRoom(); // return room
        }
    }
    
    // scan game at a specific header level, remembering the last head in precedence (for linear referencing purposes)
    private void scan(String _header, String _sourceText) {
        //returns true if there is another line to read
        Scanner gameScan = new Scanner(_sourceText); // read in source text as new scanner object, scan per line
        String higherObjName = "";
        while(gameScan.hasNextLine())  { // scanning for rooms
            String line = gameScan.nextLine();
            if (line != null && !line.equals("")) { // CHECK if line not null or empty, skip if so
                Drake drake = new Drake(line); // create drake object for current level
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
