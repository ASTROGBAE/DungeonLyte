public class World {

    private Room firstRoom;

    public World() {
        WorldLoad wLoad = new WorldLoad("game.txt"); // init new world scan based on given file param
        firstRoom = wLoad.loadGame();
    }

}
