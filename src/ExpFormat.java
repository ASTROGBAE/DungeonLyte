public class ExpFormat {
    
    String head;
    String titleRegex = "\\[\\w+\\]"; // regex for title
    String[] wrapperFullRegex = {"^", "*: "}; // regex to wrap front and back of full exp

    public ExpFormat(String _head) { // constructor
        head = _head;
    }

    private String bodyRegex() {
        return wrapperFullRegex[0] + head + titleRegex + wrapperFullRegex[1]; // header regex prefix and suffix
    }

    // get body (header+title or just header)
    public String getBody(String line) {
        if (isExpression(line)) { // check if valid line (body present)
            int bodyLength = line.indexOf(": ")+2; // get index of end of body
            return line.substring(0, bodyLength); // return body
        }
        return "";
    }

    // return title from header if it exists, else return ""
    public String getTitle(String line) {
        String body = getBody(line);
        if (body != "" && body.contains(titleRegex)) { // check if valid body and contails title
            return body.substring(body.indexOf("[")+1, body.indexOf("]")); // get string enclosed by square brackets []
        }
        return "";
    }

    // description of expression (after the body)
    // TODO what if no tail???
    public String getTail(String line) {
        String body = getBody(line);
        if (body != "") { // valid body
            return line.substring(body.length()+2); // return tail (cut off body)
            // TODO what if no tail, will this substring work?
        }
        return "";
    }

    // return true if line is a valid expression, else false
    public Boolean isExpression(String line) {
        return line.contains(bodyRegex() + ".*");
    }

}
