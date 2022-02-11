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

    public ArrayList<Door> getCurrentDoors() {
        return roomDoors;
    }

    public ArrayList<Door> getCurrentRoomItems() {
        return roomDoors;
    }

    private boolean updateRoom(Room _room) {
        if (room == null || room != _room) { // not already the same room or just at start so set to null
            room = _room;
            roomDoors = room.getDoors();
            roomStore = room.getStore();
        }
        return false;
    }

    public boolean moveThroughDoor(Door _door) {
        if (roomDoors.contains(_door)) { // valid door to move through
            updateRoom(_door.getOtherRoom(room));// update values
        }
        return false;
    }

}
