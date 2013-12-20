import java.net.Socket;
import java.util.*;
import java.net.*;
import java.io.*;
import http.HTTPRequest;
import http.HTTPResponse;

/**
 * A class to handle HTTP requests and responses
 * 
 * @author Robert Northard
 * @version 10/12/2013
 */
public class RequestHandler extends Thread{

    private Socket socket = null;
    //have http request and responses as fields?

    /**
     * Constructor for class RequestHandler
     * @param socket provides access to input and ouput streams
     */
    public RequestHandler(Socket socket){
        this.socket = socket;
    }

    /**
     * Handle the request as determined by the protocal
     */
    @Override
    public void run(){
        try{
            //get request
            HTTPRequest req = HTTPRequest.parseRequest(this.socket.getInputStream());
            //generate HTTP response

            HTTPResponse resp = new HTTPResponse(req);
            resp.writeResponse(this.socket.getOutputStream());
            //terminate communication
            socket.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
