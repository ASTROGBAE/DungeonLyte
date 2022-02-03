import java.util.ArrayList;

public class Header {
    // TODO integrate header objects into code (not yet done, class unused)
    String head;
    String[] headArr = {"Room", "Door", "Item", "Feature", "Lock"};
    ArrayList<String> headerList;

    public Header(String _head) {
        head = _head;
        headerList = new ArrayList<String>(); // init headerList object
        for (String s : headArr) {headerList.add(s);} // scan in head objects
    }
}
