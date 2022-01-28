import java.util.ArrayList;

public class Drake {

    private String head;
    private String title;
    private String tail;

    // utility stuff
    private String titleRegex = "\\[\\w+\\]"; // regex for title
    private String[] wrapperFullRegex = {"^", "*: "}; // regex to wrap front and back of full exp

    public Drake(String line) { // constructor for drake object
        if (isDrake(line)) { // if valid drake string, add in values
            head = setHead(line);
            title = setTitle(line); // add title if it exists, else it is ""
            tail = setTail(line);
        } else {
        System.out.println("Drake could not be created: " + line);}
    }

    private String drakeRegex(String line) {
        return wrapperFullRegex[0] + setHead(line) + titleRegex + wrapperFullRegex[1]; // header regex prefix and suffix
    }

    // get methods
    public String getHead() {
        return head;
    }
    public String getTitle() {
        return title;
    }
    public String getTail() {
        return tail;
    }

    // compare methods
    // compare priority of header, if it exists in arraylist of headers
    public int compareToHeader(String header, ArrayList<String> headers) {
        if (headers.contains(this.getHead()) && headers.contains(header)) { // objects valid in headers
            int first = headers.indexOf(this.getHead());
            int second = headers.indexOf(header);
            return second - first;
        }
        return 0;
    }

    // set methods
    // return string of head from line
    private String setHead(String line) {
        // check if has title
        // do regex based on that
        if (!setTitle(line).equals("")) { // it a title exists, return name before header
            int bodyLength = line.indexOf("["); // get index of beginning of title
            return line.substring(0, bodyLength); // return body
        } // else, no title exists, just return body which is just the header
        return setBody(line);
    }

    // get body (header+title or just header)
    private String setBody(String line) {
        int bodyLength = line.indexOf(": ")+2; // get index of end of body
        return line.substring(0, bodyLength); // return body
    }

    // return title from header if it exists, else return ""
    private String setTitle(String line) {
        String body = setBody(line);
        if (!body.equals("") && body.matches(".*"+titleRegex+".*")) { // check if valid body and contails title and everything else
            return body.substring(body.indexOf("[")+1, body.indexOf("]")); // get string enclosed by square brackets []
        }
        return "";
    }

    // description of expression (after the body)
    // TODO what if no tail???
    private String setTail(String line) {
        String body = setBody(line);
        if (!body.equals("")) { // valid body
            return line.substring(body.length()); // return tail (cut off body)
            // TODO what if no tail, will this substring work?
        }
        return "";
    }

    // return true if line is a valid drake expression, else false
    private Boolean isDrake(String line) {
        String regex = drakeRegex(line) + ".*"; // check matches entire line
        return line.matches(regex);
    }

    public Boolean isHead(String h) {
        if (head.equals(h)) {return true;} return false;
    }

    // testing loop (delete later)
    // public static void main(String[] args) throws Exception {
    //     Drake _drake = new Drake("Room[antechamber]: a dank room");
    //     System.out.println("eyy");
    // }
}
