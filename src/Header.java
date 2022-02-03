import java.util.ArrayList;

public class Header {
    String head;
    String[] headArr = {"Room", "Door", "Item", "Feature", "Lock"};
    ArrayList<String> headerList;

    public Header(String _head) {
        head = _head;
        headerList = new ArrayList<String>(); // init headerList object
        for (String s : headArr) {headerList.add(s);} // scan in head objects
    }
}
