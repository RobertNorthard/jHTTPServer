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
    public HTTPServer(String name, int port, String directory)
    throws IOException{

        if(!isValidPort(port))
            port = DEFAULT_PORT;
            
        this.serverSocket = new ServerSocket(port);
        this.directory = directory;
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
            new Request(conn).start();
        }    
    }

    private class Request extends Thread{

        Socket client = null;

        public Request(Socket client){
            this.client = client;
        }

        public void sendHTTPResponse(String statusCode, String data)
        throws Exception {

            String reply  = "HTTP/1.1 " + statusCode + "\r\n" +
                "Connection: close\r\n" +
                "Server: Robert's HTTP Server\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                data;

            this.client.getOutputStream().write(reply.getBytes());

        }
        public void sendFile(InputStream in, OutputStream out)
        throws Exception{

            byte[] buffer = new byte[1024];

            while(true){

                int rc = in.read(buffer, 0, 1024);

                if(rc < 0) break;

                out.write(buffer, 0, rc);

            }

        }

        public void run(){
            try{
                String[] buffer = new String[32]; //to store HTTP request header lines
                Scanner fromClient = new Scanner(this.client.getInputStream());
                int nLines = 0;

                //read and store HTTP headers
                while(true){
                    String line = fromClient.nextLine();
                    if(line.length() == 0) break;
                    buffer[nLines] = line;
                    nLines++;
                }

                //display header lines
                for(int i = 0; i<nLines; i++)
                    System.out.println(buffer[i]);

                //get reqeust type and requested resource
                Scanner scan = new Scanner(buffer[0]);
                String requestType = scan.next();
                String requestedResource = scan.next();

                File file = new File(directory + "/" + requestedResource);
                String reply = "";

                if(!requestType.equals("GET")){

                    this.sendHTTPResponse("HTTP/1.0 501 Not Implemented\r\n", "<h1>501</h1>");
                    

                }else if (!file.exists() || requestedResource.equals("/")){

                    this.sendHTTPResponse("404 Not Found\r\n", "<h1> File Not Found </h1>");

                }else{

                   this.sendHTTPResponse("200 OK", null);
                   this.sendFile(new FileInputStream(file), this.client.getOutputStream());
                }

                this.client.getOutputStream().write(reply.getBytes());
                this.client.close();

            }catch(Exception e){
                System.out.println("Error: " + e.toString());
            }
        }
    }  
}