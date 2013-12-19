import java.net.*;

/**
 * A tester class for HTTP Server class
 * 
 * @author Robert Northard 
 */
public class Tester
{
    public static void main(String[] args) throws Exception{
	    new HTTPServer(9000).run();
    }   
}