import java.net.Socket;

/**
 * A class to handle HTTP requests and responses
 * 
 * @author Robert Northard
 * @version 18/12/2013
 */
public class RequestHandler extends Thread{

    private Socket socket = null;

    /**
     * Constructor for class RequestHandler
     * @param socket provides access to input and ouput streams
     */
    public RequestHandler(Socket socket){
        this.socket = socket;
    }

    /**
     * Handle HTTP Request and Responses
     */
    @Override
    public void run(){
        try{
            //get http request
            HTTPRequest req = new HTTPRequest(socket.getInputStream());
            req.getRequest();
            
            //handle HTTP response
            HTTPResponse resp = new HTTPResponse(req);
            resp.handleRequest();
            resp.write(socket.getOutputStream());
            
            socket.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
