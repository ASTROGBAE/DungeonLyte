import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameLoop {

    static World world;
    Random rand;
    Scanner scan;

    public GameLoop() {
        world = new World();
        rand = new Random();
        scan = new Scanner(System.in); // for user input
    }
    
    public void run() {
        boolean running = true;
        String answer = null;

        while (running) {
            responce(answer);
            answer = question();
        }
    }

    // TODO encapsulate Room objects within World, so this does not need to access it. This should only access basic objects like strings, booleans etc. 

    private Room requestRoomChange() {
        System.out.print("Enter a number from one of the prompts below: \n\n"); // pick random choice from above
        ArrayList<Door> choices = world.getCurrentDoors();
        for (int i = 0; i < choices.size(); i ++) {
            System.out.println(i + ": " + choices.get(i).getName());
        }
        String[] prompts = {"What would you like to do", "What do you want to do", "What do you do"};
        System.out.print(prompts[rand.nextInt(prompts.length)]+"?: "); // prompt user input
        try {
            int answer = Integer.parseInt(scan.nextLine()); // prompt user input
            if (answer > 0 && answer < choices.size()) { // valid number enteres
                return choices.get(answer).getOtherRoom(world.getRoom()); // get other room
            } else {
                // in out of bounds
            }
        } catch (NumberFormatException e) {
            // invalid input (not an int)
        }
        return null;
    }

    private String responce(String _answer) {
        if (_answer == null) {
            // beginning of game
            String re = world.getRoomDescription();
            for (Door door : world.getCurrentDoors()) {
                re += door.getDescription() + " ";
            }
            return re+"\n";
        }
        else if (_answer.equals("")) {
            // empty string
        }
        return "";
    }

}
