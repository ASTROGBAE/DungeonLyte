public class World {

    private Room firstRoom;

    /**
     * World object, containing the subject room and allowing movement between said (TODO add)
     */
    public World() {
        WorldLoad wLoad = new WorldLoad("game.txt"); // init new world scan based on given file param
        firstRoom = wLoad.loadGame();
    }

}
