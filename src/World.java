import java.util.ArrayList;

public class World {

    private Room room;
    private ArrayList<Door> roomDoors;
    private ArrayList<WorldObject> roomStore;
    private Player player;

    /**
     * World object, containing the subject room and allowing movement between said (TODO add)
     */
    public World() {
        WorldLoad wLoad = new WorldLoad("game.txt"); // init new world scan based on given file param
        updateRoom(wLoad.loadGame());
        player = new Player("name", "wow");
    }

    public Room getRoom() {
        return room;
    }

    public String getRoomDescription() {
        return room.getDescription();
    }

    public ArrayList<Door> getCurrentDoors() {
        return roomDoors;
    }

    public ArrayList<Door> getCurrentRoomItems() {
        return roomDoors;
    }

    public boolean updateRoom(Room _room) {
        if (room == null || room != _room) { // not already the same room or just at start so set to null
            room = _room;
            roomDoors = room.getDoors();
            roomStore = room.getStore();
        }
        return false;
    }

    // return true if room is not null, false otherwise
    boolean hasRoom() {
        if (room != null) {
            return true;
        } return false;
    }

    // check if current room is connected to param room by any doors //TODO is this even needed wtf
    public boolean roomConnected(Room _room) {
        for (Door d : room.getDoors()) {
            if (d.getOtherRoom(room).equals(_room)) { // check if room exists in any connected door
                return true; 
            }
        }
        return false; 
    }

    public Room getOtherRoom(Door _door) { // TODO what to do with void objects returning booleans? Make a better system!
        if (roomDoors.contains(_door)) { // valid door to move through
            return _door.getOtherRoom(room);// update values
        }
        return null;
    }

    public void playerPickUp() {
        playerPickUpObjects(room);
    }

    public void playerPickUpObjects(WorldObject current) { // recursive yum yum object
        if (current != null) {
            if (current instanceof Item) { // adding items logic
                player.addItem((Item)current);
            }
            else if (current instanceof Feature) {
                player.addFeature((Feature)current);
            }
            // check current store, call method again on stored objects...
            ArrayList<WorldObject> roomStore = room.getStore(); // TODO is this too robust? 
            if (!roomStore.isEmpty()) { // things in store...
                for (WorldObject object : roomStore) {
                    playerPickUpObjects(object); // recursive loop on all world objects...
                }
            }
        }
    }

    // prompt from room object to move through a door, updating room object for this world to new room
    // return true if successful
    public Boolean moveThroughDoor(Door openDoor) { // TODO make this boolean so it works out!
        if (room.getDoors().contains(openDoor)) {
            room = openDoor.getOtherRoom(room) ; // change door to other
            return true;
        } return false;
    }

}
