public class Main {

    static GameLoop game;
    public static void main(String[] args) throws Exception {
        game = new GameLoop();
        game.run();
        System.out.println("Done!");

    } // TODO out of bounds when traversing world, check issues
}
