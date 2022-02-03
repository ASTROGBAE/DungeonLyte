import java.util.ArrayList;

public class Header {
    // TODO integrate header objects into code (not yet done, class unused)
    private String head;
    private String[] headArr = {"Room", "Door", "Item", "Feature", "Lock", "Unlock"}; // all headers used in Dracolysh
    private int level; // level, defined by placement in header list i.e. Room is 1, Door is 2, etc...
    private ArrayList<String> headerList;
    // TODO make sure class encapsulation in each class...

    /** */
    public Header(String _head) {
        headerList = new ArrayList<String>(); // init headerList object
        for (String s : headArr) {headerList.add(s);} // scan in head objects // TODO make this more efficient? just makes head object arraylist before even seeing if valid...
        if (headerList.contains(_head)) {head = _head;} // if head valid, add to class
        // TODO throw some sort of error/problem if head param is invalid
        head = null;
        level = headerList.indexOf(head); // get level of header (defined above)
    }

    public String[] getHeaderArr() {
        return headArr;
    }

        /**
     * Compare String header against an ordered array of headers
     * @param header other header
     * @return 1 if header is higher, -1 is lower, 0 if equal or header 
     */
    // TODO make header a class object with in-built comparison
    public int compareToHeader(Header other) {
        return level - other.level; // compare levels
    }
}
