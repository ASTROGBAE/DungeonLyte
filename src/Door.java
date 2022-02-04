public class Door extends WorldObject {

    Room[] link = null;

    /**
     * Create Door object, that will act as a link object between two Room objects. Name var is left blank. 
     * @param _desc
     * @param _link
     */
    public Door(String _desc, Room[] _link) {
        super(_link[0].getName()+"/"+_link[1].getName(), _desc); // define name as 'doorname/doorname' str
    }

    // set methods for factory, before being added to list
    public void setLink(Room[] _link) {
        name = getDoorName(_link[0], _link[1]);
        link = _link;
    }
    
    public String getDoorName(Room r1, Room r2) {
        return r1.getName()+"/"+r2.getName();
    }
    /**
     * Check the String names of Room objects match, regardless of which link is first in the Door list
     * @param firstRoom one Room that is connected (order doesn't matter)
     * @param secondRoom one Room that is connected (order doesn't matter)
     * @return true if both Rooms match, false otherwise
     */
    public Boolean matchesRooms(Room firstRoom, Room secondRoom){
        String[] thisNames = {link[0].getName(), link[1].getName()};
        String[] otherNames = {firstRoom.getName(), firstRoom.getName()}; 
        if (thisNames[0].equals(otherNames[0]) && thisNames[1].equals(otherNames[1])) {
            return true;
        }
        else if (thisNames[0].equals(otherNames[1]) && thisNames[0].equals(otherNames[1])) {
            return true;
        }
        return false; 
    }
    
}
