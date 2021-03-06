package com.astrogbae;

import java.util.ArrayList;

/**
 * The class {@code Drake} defines a line in the Dracolysh language: including a header, title
 * and tail. Allows for advanced operations in Dracolysh in comparing drakes
 * and garnering information from them. See readme.md for more details
 * 
 * @author astrogbae 
 */

public class Drake {

    // fields for the given syntax, as given in Dracolysh: Header[Title]: tail.
    private String head;
    private String title;
    private String tail;

    // regex class utilities 
    private String titleRegex = "\\[\\w+\\]"; // regex for title
    private String[] wrapperFullRegex = {"^", "*: "}; // regex to wrap front and back of full exp

    /**
     * create an instance of a drake from a scanned line.
     * @param line string input, will create a proper Drake object if line is a 
     * syntaxically correct line of Dracolysh, will initialise head, title and tail as null otherwise
     */ // TODO add better error detection for incorrect line param?
    
    public Drake(String line) { // constructor for drake object
        if (isDrake(line)) { // check if line is syntatically correct (does not check Header type)
            head = setHead(line);
            title = setTitle(line); // add title if it exists, else it is ""
            tail = setTail(line);
        }
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

        /**
     * Compare String header against an ordered array of headers
     * @param header String header
     * @param headers ordered list of String headers (containing header String)
     * @return 1 if header is higher, -1 is lower, 0 if equal or header 
     */
    // TODO make header a class object with in-built comparison // TODO OR NOT!!
    public int compareToHeader(String header, ArrayList<String> headers) {
        if (headers.contains(this.getHead()) && headers.contains(header)) { // objects valid in headers
            int first = headers.indexOf(this.getHead());
            int second = headers.indexOf(header);
            return first - second;
        }
        return 0;
    }

    // set methods

    /**
     * 
     * @param line Dracolysh String (a line)
     * @return head String, 
     */
    private String setHead(String line) {
        // check if has title
        // do regex based on that
        if (!setTitle(line).equals("")) { // it a title exists, return name before header
            int bodyLength = line.indexOf("["); // get index of beginning of title
            return line.substring(0, bodyLength); // return body
        } // else, no title exists, just return body which is just the header
        return setBody(line);
    }

    /**
     * Get body of Dracolysh String line [head+title], only used in this class to help get header and title.
     * @param line Dracolysh String (a line)
     * @return body String, else return ""
     */
    private String setBody(String line) {
        int bodyLength = line.indexOf(": ")+2; // get index of end of body
        return line.substring(0, bodyLength); // return body
    }

    /**
     * 
     * @param line Dracolysh String (a line)
     * @return header String, else return ""
     */
    private String setTitle(String line) {
        String body = setBody(line);
        if (!body.equals("") && body.matches(".*"+titleRegex+".*")) { // check if valid body and contails title and everything else
            return body.substring(body.indexOf("[")+1, body.indexOf("]")); // get string enclosed by square brackets []
        }
        return "";
    }

    /**
     * 
     * @param line Dracolysh String (a line)
     * @return tail String, else return ""
     */
    // TODO what if no tail???
    private String setTail(String line) {
        String body = setBody(line);
        if (!body.equals("")) { // valid body
            return line.substring(body.length()); // return tail (cut off body)
            // TODO what if no tail, will this substring work?
        }
        return "";
    }

    /**
     * Check if String is valid dracolysh syntax, only used in this class for construction
     * @param line line of Dracolysh
     * @return return true if line is a valid drake expression, else false
     */
    private Boolean isDrake(String line) {
        String regex = wrapperFullRegex[0] + setHead(line) + titleRegex + wrapperFullRegex[1] + ".*"; // check matches entire line
        try { // try regex, will throw syntax exception if exp is invalid
            return line.matches(regex);
        }
        catch(Exception e) { // return false if syntax exp in invalid, catch exception
            return false;
          }
    }

    /**
     * return true if head of this drake equals String param 'h'
     * @param h head String (must be a valid head)
     * @return true if head matches, false otherwise 
     */
    public Boolean isHead(String h) {
        if (head != null && head.equals(h)) {return true;} return false;
    }

}
