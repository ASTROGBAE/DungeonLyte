import java.util.ArrayList;

public class Player extends WorldObject {

    private ArrayList<Item> items;
    private ArrayList<Feature> features;
    private ArrayList<WorldObject> encounters;

    public Player(String _name, String _desc) { // TODO add mechanic to add name?
        super(_name, _desc);
        items = new ArrayList<Item>();
        features = new ArrayList<Feature>();
        encounters = new ArrayList<WorldObject>();
    }

    public void addItem(Item _item) {
        if (_item != null) {
            items.add(_item);
        } 
    }

    public boolean addFeature(Feature _feature) {
        if (_feature != null && !features.contains(_feature)) {
            features.add(_feature);
        } 
        return false;
    }

    public boolean addEncounter(WorldObject object) {
        if (object != null && !encounters.contains(object)) {
            encounters.add(object);
            return true;
        }
        return false; 
    }

    public boolean hasEncountered(WorldObject object) {
        if (object != null && encounters.contains(object)) {
            return true;
        }
        return false; 
    }
    
}
