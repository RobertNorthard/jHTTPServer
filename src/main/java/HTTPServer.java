import java.io.*;
import java.net.*;
import java.util.*;
import http.*;
/**
 * A basic concurrent HTTP Web server
 * 
 * @author Robert Northard
 * @version 01/09/2012
 */
public class HTTPServer
{
    private static final int DEFAULT_PORT = 8000;

    private String directory = "";
    private ServerSocket serverSocket = null;

    /**
     * Constructor for class HTTP Web Server
     * @param name name of server
     * @param port port listening number
     * @trows IOException
     */
    public HTTPServer(int port) throws IOException{
        if(!isValidPort(port)) port = DEFAULT_PORT;
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Invoke to start
     */
    public void run() throws IOException{

        System.out.println("Server listening on: " + this.serverSocket.getInetAddress().getHostAddress() + ":" + this.serverSocket.getLocalPort());

        while(true){
            Socket conn = this.serverSocket.accept();
            System.out.println("Accepted Connection: " + conn.getRemoteSocketAddress() + " " + new Date());
            new RequestHandler(conn).start();
        }    
    }

    /**
     * Return true if valid port number, 0 - 65535 inclusive.
     * @return true if valid port number
     */
    public static boolean isValidPort(int port){      
        return (port > 0 && port < 65535);
    }
}