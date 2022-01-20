import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class World {

    private Map<String, Room> rooms;
    private ExpFormat[] headers = {new ExpFormat("ROOM"), new ExpFormat("DOOR")};

    public World() {
        rooms = new HashMap<String, Room>();
        loadGame(); // load in game objects
    }

    // return file to be scanned  
    private void loadGame() {
        try { FileInputStream gameStream =new FileInputStream("data/game.txt");
            for(ExpFormat h : headers) { Scanner gameScan = new Scanner(gameStream); // perform a seperate scan per header object
                scanHeaders(h, gameScan);
                gameScan.close();     //closes the scanner 
            }
        }
        catch(IOException e)  {e.printStackTrace();}  
    }
    
    // scan in objects from scan according to the provided head object 
    private void scanHeaders(ExpFormat head, Scanner _gameScan) {
        //returns true if there is another line to read
        while(_gameScan.hasNextLine())  { // scanning for rooms
            String line = _gameScan.nextLine();
            String lastRoomTitle = "";

            if (head.getHead() == "ROOM") { // TODO find out why there is an empty first entry?
                String title = head.getTitle(line);
                String tail = head.getTail(line);
                rooms.put(title, new Room(title, tail)); // TODO refactor usage of title?
            }
            else if (head.getHead() == "DOOR") {
                if (headers[0].isExpression(line)) { // if room expression but door scan, then remember last room
                    // TODO refactor so this is if exp is cleaner? make each format a variable perhaps?
                    lastRoomTitle = headers[0].getTitle(line);
                }
                Room[] link = {rooms.get(lastRoomTitle), rooms.get(head.getTitle(line))}; // get last room and subject room from title
                Door _door = new Door(head.getTail(line), link); // create door instance 
                for (Room r : link) {r.addDoor(_door);} // add door ref to both rooms
            }
        }
    }

}
