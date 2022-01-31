public class Door extends WorldObject {

    Room[] link = null;

    /**
     * Create Door object, that will act as a link object between two Room objects. Name var is left blank. 
     * @param _desc
     * @param _link
     */
    public Door(String _desc, Room[] _link) {
        super(_desc); 
        link = _link;
    }
    
}
