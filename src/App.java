import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;  

public class App {

    static int scanState = 0; 
    static Map<String, Room> rooms = new HashMap<String, Room>();
    static ExpFormat[] headers = {new ExpFormat("ROOM"), new ExpFormat("DOOR")};

    public static void main(String[] args) throws Exception {

        scanPass();
        scanState = 1;
        scanPass();

        System.out.print("Rooms: ");
        for (Entry<String, Room> entry : rooms.entrySet()) {
            System.out.println(entry.getValue().toString());
        }

    }

    public static void scanPass() {
        try  
            {  
                //the file to be opened for reading  
                FileInputStream gameStream =new FileInputStream("data/game.txt");       
                Scanner gameScan =new Scanner(gameStream);    //file to be scanned  
                //returns true if there is another line to read
                for (ExpFormat head : headers) { // iterate through each header
                    while(gameScan.hasNextLine())  { // scanning for rooms
                        String line = gameScan.nextLine();
                        String lastRoomTitle = "";

                        if (head.getHead() == "ROOM") {
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
                            Door _door = new Door(head.getTail(line), link);
                            for (Room r : link) {r.addDoor(_door);} // add door ref to both rooms
                        }
                    }
                } 
                gameScan.close();     //closes the scanner 
            }
        catch(IOException e)  
            {  
                e.printStackTrace();  
            }  
    }
}
