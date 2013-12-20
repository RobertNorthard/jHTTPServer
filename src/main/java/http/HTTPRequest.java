package http;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A class that represents a HTTP request.
 *
 * @author Robert Northard
 * @version 18/12/2013
 */
public class HTTPRequest{

    private MethodCode method = null;
    private String resource = "";
    private String version = "";

    private Map<String, String> headers = new HashMap<String, String>();

    /**
     * Constructor for class HTTPRequest
     * @param method http request type
     * @param resource requested resource name
     * @param version http version
     * @param headers, map containing header mappings
     */
    public HTTPRequest(MethodCode method, String resource, String version, Map<String, String> headers){
        this.method = method;
        this.resource = resource;
        this.version = version;
        this.headers = headers;
    }

    /**
     * Parse first HTTP request and store headers
     * Example: GET / HTTP/1.1
     * pre-condition: valid http request line
     * @return a valid HTTP Request, null if not valid.
     */
    public static HTTPRequest parseRequest(InputStream in){
        Scanner scan = new Scanner(in);
        String line = scan.nextLine();
        String[] parts = line.split(" ");

        MethodCode method = null;
        String resource = null;
        String version = null;
        Map<String,String> headers = new HashMap<String,String>();

        if(parts.length == 3){

            //not the best approach
            try{
                method = MethodCode.valueOf(parts[0]);
            }catch (IllegalArgumentException e){
                method = MethodCode.UNRECOGNIZED;
            }

            resource = parts[1];
            version = parts[2];
            line = scan.nextLine();
            while(line.length() != 0){
                parts = line.split(":");
                headers.put(parts[0], parts[1]);
                line = scan.nextLine();
            }
            return new HTTPRequest(method, resource, version, headers);
        }
        return null;
    }

    /**
    *   Return true if valid HTTP Request
    *   @return true if valid HTTP request
    */
    public boolean isValidRequest(){

         if (this.method != MethodCode.UNRECOGNIZED && this.resource.startsWith("/"))
                if (this.version.equals("HTTP/1.1") && this.headers.containsKey("Host"))
                    return true;
                else if (this.version.equals("HTTP/1.0"))
                    return true;
        return false;
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
     * @return resource file name
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
