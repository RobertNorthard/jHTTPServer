import java.util.*;
import java.io.*;

/**
 * A class to deal with HTTP Responses
 * 
 * @author Robert Northard
 * @version 18/12/2013
 */
public class HTTPResponse
{		
    private static final String HTTP_VERSION = "HTTP/1.1";
    private List<String> headers = new ArrayList<String>();
    private byte[] content = null;
    
    /**
     * Handle HTTP response
     */
    public void handleRequest(HTTPRequest request) throws IOException{

      	String resourcePath = "www/" + request.getResource();

		switch(request.getRequestMethod()){
            
            case HEAD: 
                this.setHeaders(ResponseCode._200);
                break;
            case GET: 
                File file = new File(resourcePath);
				//direct to index.html if resource /
				if(request.getResource().equals("/")){
					file = new File("www/index.html");
					this.setHeaders(ResponseCode._200);
					this.setContent(file); 
               } else if (file.exists()){
					this.setHeaders(ResponseCode._200);
                    this.setContent(file);
				}else
					this.setHeaders(ResponseCode._404);
        } 
    }

    /**
     * Reads the contents of a file
     */
    public void setContent(File file) throws IOException{                             
        InputStream in = new FileInputStream(file);
        this.content = new byte[(int)file.length()];
        int offSet = 0;
        while(offSet < file.length()){
            
            int rc = in.read(this.content, offSet, (content.length - offSet));
            offSet += rc;
        }
        in.close(); 
    }
    
    /**
     * Set the HTTP Headers
     */
    public void setHeaders(ResponseCode code){
         headers.add(this.HTTP_VERSION  + " " + code.toString() + "\r\n");
                headers.add("Date: " + new Date().toString()+ "\r\n");
                headers.add("Server: Robert's Webserver"+ "\r\n");
                headers.add("Connection: close"+ "\r\n\r\n");  
    } 

	/**
	* Return HTTP Response Headers
	* @return HTTP Response headers
	*/
	public List<String> getHeaders(){
		return this.headers;
	}
	
	/**
	* Return HTTP Response content
	* @return HTTP response content
	*/
	public byte[] getContent(){
		return this.content;
	}
}
