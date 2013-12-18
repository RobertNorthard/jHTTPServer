import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A class to handle HTTP requests. Only takes into account first line.
 * 
 * @author Robert Northard
 * @version 18/12/2013
 */
public class HTTPRequest{

    private MethodCode method = null;
    private String resource = "";
	private String version = "";

    /**
     * Constructor for class HTTPRequest
     * @param method http request type
	 * @param resource requested resource name
	 * @param version http version
     */
    public HTTPRequest(MethodCode method, String resource, String version){
        this.method = method;
		this.resource = resource;
		this.version = version;
    }

    /**
     * Parse first HTTP request line
     * Example: GET / HTTP/1.1
	 * Break it down into request type, requested resource and HTTP version.
	 * pre-condition: valid http request line
	 * @return a HTTP Request
     */
    public static HTTPRequest parseRequest(String requestLine){
        String[] parts = requestLine.split(" ");
		
		MethodCode method = null;
		String resource = null;
		String version = null;
		
		if(parts.length == 3){
		
			//not the best approach
        	try{
            	method = MethodCode.valueOf(parts[0]);
        	}catch (IllegalArgumentException e){
            	method = MethodCode.UNRECOGNIZED;
        	}

        	resource = parts[1];
			version = parts[2];
		}
		
		return new HTTPRequest(method, resource, version);
    }

	/**
	* set resouce file name
	* @param resource the resource file name
	*/
	public void setResource(String resource){
		this.resource = resource;
	}

    /**
     * Return requested resource
	 * @return resouce file name 
     */
    public String getResource(){
        return this.resource;
    }

	/**
	* Return HTTP version
	* @return HTTP version
	*/
	public String getVersion(){
		return this.version;
	}

    /**
     * Return request method type 
	 * @return HTTP method request type
     */
    public MethodCode getRequestMethod(){
        return this.method;
    }
}