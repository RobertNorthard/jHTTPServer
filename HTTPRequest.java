import java.io.*;
import java.net.*;
import java.util.*;

public class HTTPRequest extends Thread{

    private InputStream in = null;
    List<String> requestLines = null;
    MethodCode method = null;
    String resource = "";

    /**
     * Constructor for class HTTPRequest
     * @param in InputStream to read request
     */
    public HTTPRequest(InputStream in){

        this.in = in;
        this.requestLines = new ArrayList<String>();

    }

    /**
     * get Request
     */
    public void getRequest(){

        Scanner scan = new Scanner(this.in);
        int nLine = 0; //number of lines read

        while(true){

            String line = scan.nextLine();
            if(line.length() == 0) break; //end of header lines
            this.requestLines.add(line);

        }

        this.parseRequest();
    }

    /**
     * Return requested resource
     */
    public String getResource(){
        return this.resource;
    }

    /**
     * Return request method 
     */
    public MethodCode getRequestMethod(){
        return this.method;
    }

    /**
     * Parse request 
     */
    private void parseRequest(){

        Scanner scan = new Scanner(this.requestLines.get(0));
        try{

            this.method = MethodCode.valueOf(scan.next());
        }catch (IllegalArgumentException e){
            this.method = MethodCode.UNRECOGNIZED;
        }
        this.resource = scan.next();
    }

}