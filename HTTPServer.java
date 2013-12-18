import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A basic concurrent HTTP Web server
 * 
 * @author Robert Northard
 * @version 01/09/2012
 */
public class HTTPServer
{
    private static final int DEFAULT_PORT = 8000;

    private String name = "";
    private String directory = "";
    private ServerSocket serverSocket = null;

    /**
     * Constructor for class HTTP Web Server
     * @param name name of server
     * @param port port listening number
     * @trows IOException
     */
    public HTTPServer(String name, int port)
    throws IOException{

        if(!isValidPort(port))
            port = DEFAULT_PORT;

        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Return true if valid port number, 0 - 65535 inclusive.
     * @return true if valid port number
     */
    public static boolean isValidPort(int port){      
        return (port > 0 && port < 65535);
    }

    /**
     * Invoke to start
     */
    public void run()
    throws IOException{

        System.out.println("Server listening on: " + this.serverSocket.getInetAddress() + ":" + this.serverSocket.getLocalPort());

        while(true){
            Socket conn = this.serverSocket.accept();
            new RequestHandler(conn).start();
        }    
    }
}