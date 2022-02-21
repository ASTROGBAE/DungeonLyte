import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameLoop {

    static boolean running = true;
    static World world;
    Random rand;
    Scanner scan;

    public GameLoop() {
        world = new World();
        rand = new Random();
        scan = new Scanner(System.in); // for user input
    }

    // TODO add options for help, exit, etc, check inventory and feats, etc. 
    
    public void run() {
        if (world.hasRoom()) { // world has room, can run
            running = true;

            while (running) {
                printNewLine(10); // clean console for new title 
                printDescription(); printNewLine(1); 
                printQuesion(); printNewLine(1); 
                printDoorTitleList(); printNewLine(1); 
                while(!printUserAction()) {printNewLine(1); } // loop print user action while it aint work TODO is this how it works?
            }   
        }
        else {
            // TODO no room exists, throw error
        }
    }

    // print newline for as many iterations as number in param
    private void printNewLine(int number) {
        if (number > 0) { for (int i = 0; i < number; i++) {
            System.out.println();
        }}
    }

    // print description of room and its doors as a single paragrath, used in printDiscription above
    private void printDescription() {
        String description = world.getRoomDescription(); // add discription of current room
        ArrayList<Door> neighbours = world.getCurrentDoors(); // get neighbour doors of Room
        description += " "; // whitespace to start descriptions
        if (neighbours != null) { // if room is connected to others
            for (Door door : world.getCurrentDoors()) {
                description += door.getDescription() + " ";
            } // TODO remove last piece of whitespace from loop retroactively?
        }
        System.out.print(description);
    }

    // TODO encapsulate Room objects within World, so this does not need to access it. This should only access basic objects like strings, booleans etc. 
// TODO check if above todo is still relevent lol

    // print prompt question eg. "what do you want to do?"
    private void printQuesion() { // TODO make more versions of this prompt?
        // TODO fix this up
        String[] prompts = {"What would you like to do", "What do you want to do", "What do you do"};
        System.out.print("Enter a number from one of the prompts below: \n\n"); // pick random choice from above
    }
    
    // print a numbered list of door titles, before user action
    private void printDoorTitleList() { // TODO maybe change world name of "room" to current to make it less confusing?
        ArrayList<Door> choices = world.getCurrentDoors(); // get list of current doors
        for (int i = 0; i < choices.size(); i ++) { // print out name of each door with number as an option
            int input = i+1; // +1 for option, not index
            System.out.println(input + ": " + choices.get(i).getName());
        }
    }

    // attempt user input and move rooms if valid, or loop again if does not work
    // return true if user prompt worked, false otherwise
    private Boolean printUserAction() {
        ArrayList<Door> choices = world.getCurrentDoors(); // get list of current doors
        System.out.print("Enter a valid choice (1-" + choices.size() + "): "); // prompt valid number
        try { // attempt user input
            int ansIdx = Integer.parseInt(scan.nextLine())-1; // prompt user input, get answer as int, -1 to convert to index
            if (ansIdx >= 0 && ansIdx < choices.size()) { // valid number entered
                Door openDoor = choices.get(ansIdx); // get target door based on number
                if (world.moveThroughDoor(openDoor)) {return true;} // update world to new door, update to new room
            } else {
                // in out of bounds, but still an int TODO fix up edge cases
            }
        } catch (NumberFormatException e) {
            // invalid input (not an int)
        }
        return false;
    }

}
