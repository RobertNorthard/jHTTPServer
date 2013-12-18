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

        this.serverSocket = new ServerSocket(port);
        this.directory = directory;
    }

    /**
     * Invoke to start
     */
    public void run()
    throws IOException{

        System.out.println("Server listening on: " + this.serverSocket.getInetAddress() + ":" + this.serverSocket.getLocalPort());

        while(true){
            Socket conn = this.serverSocket.accept();
            new client(conn).start();
        }    
    }

    private class client extends Thread{
        
        Socket client = null;
        
        public client(Socket client){
            this.client = client;
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
                    reply="HTTP/1.0 501 Not Implemented\r\n" +
                    "Connection: close\r\n" + "Content-Type: text/html\r\n" +
                    "\r\n" +
                    "<h1>>Not implemented</h1>\r\n";
                }else if (!file.exists() || requestedResource.equals("/")){
                    reply="HTTP/1.0 404 Not Found\r\n" +
                    "Connection: close\r\n" + "Content-Type: text/html\r\n" +
                    "\r\n" +
                    "<h1>Sorry, work in progress</h1>\r\n";
                }else{
                    reply ="HTTP/1.1 200 OK\r\n" +
                    "Connection: close\r\n" +
                    "Server: Robert's HTTP Server\r\n" + 
                    "Content-Length: " + file.length() + "\r\n" +
                    "Content-Type: text/html\r\n" +
                    "\r\n";

                    OutputStream outs = this.client.getOutputStream();
                    outs.write(reply.getBytes());

                    InputStream in = new FileInputStream(file);
                    byte[] data = new byte[1024];

                    while(true){
                        int rc = in.read(data, 0, data.length);

                        if(rc <= -1 )break;
                        outs.write(data, 0, rc);

                    }

                }

                this.client.getOutputStream().write(reply.getBytes());       

                this.client.close();

            }catch(Exception e){
                System.out.println("Error: " + e.toString());
            }
        }
    }  
}