import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;  

public class App {

    static int scanState = 0; 
    static Map<String, Room> rooms = new HashMap<String, Room>();

    static String[] hFormat = {"^", "(\\[\\w+\\])*: "};
    static String[] headers = {"ROOM", "DOOR"};

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < headers.length; i++) {headers[i] = hFormat[0] + headers[i] + hFormat[1];}; // populate format on headers with concat
        scanPass();
        scanState = 1;
        scanPass();
        System.out.println("done running!");
        System.out.println("Rooms: " + rooms.toString());
        System.out.println(headers[0] + " " + headers[1]);

    }

    public static void scanPass() {
        try  
            {  
                //the file to be opened for reading  
                FileInputStream gameStream =new FileInputStream("data/game.txt");       
                Scanner gameScan =new Scanner(gameStream);    //file to be scanned  
                //returns true if there is another line to read

                while(gameScan.hasNextLine())  { // scanning for rooms
                    String line = gameScan.nextLine();
                    System.out.println(line.contains(headers[0]));
                    // TODO figure out why this is returning false??
                    if (scanState == 0 && line.contains(headers[0])) { // add all new rooms
                        String head = line.replaceFirst("[^"+headers[0]+"]", ""); // get header string
                        String tail = line.replaceFirst(headers[0], ""); // remove header, get tail
                        String title = head.substring(5, head.length()-2); // get title from header, TODO refactor this process
                        rooms.put(title, new Room(title, tail)); // TODO refactor usage of title?
                    } 
                    else if (scanState == 1 && line.contains(headers[1])) { // scanning for doors
                        String lastRoom = "foobar"; // TODO: need to remember last pass!
                        String head = line.replaceFirst("[^"+headers[0]+"]", ""); // get header string
                        String tail = line.replaceFirst(headers[0], ""); // remove header, get tail
                        String title = head.substring(5, head.length()-2); // get title from header, TODO refactor this process
                        Room[] link = {rooms.get(lastRoom), rooms.get(title)}; // get last room and subject room from title
                        link[0].addDoor(tail, link);
                    }
                    //System.out.println(sc.nextLine());      //returns the line that was skipped  
                } 
                gameScan.close();     //closes the scanner 
            }
        catch(IOException e)  
            {  
                e.printStackTrace();  
            }  
    }

    public boolean validLine(String line, String header) {
        // if (line.substring(header.length()-1).matches()) {
            
        // }
        return true;
    }
}
