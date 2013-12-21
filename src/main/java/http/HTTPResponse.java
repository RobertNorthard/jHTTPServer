package http;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * A class that represents a HTTP response.
 * 
 * @author Robert Northard
 * @version 18/12/2013
 */
public class HTTPResponse
{       
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String DIRECTORY = "www";
    private static final String DEFAULT = "index";

    private List<String> headers = new ArrayList<String>();
    private byte[] content = null;
    private HTTPRequest request = null;

    /**
     * Constructor for class HTTP response
     * @param request HTTP request to generate a HTTP response for
     */
    public HTTPResponse(HTTPRequest request) throws IOException{
        this.request = request; 
        this.handleRequest();
    }

    /**
     * Handle HTTP response, HEAD and GET implemented.
     */
    private void handleRequest() throws IOException{

        String resourcePath = "www" + this.request.getResource();
        File dir = new File(DIRECTORY);
        File[] dirContents = dir.listFiles();

        switch(request.getRequestMethod()){

            case HEAD: 
            this.setHeaders(ResponseCode._200, null);
            break;
            case GET: 
            File file = new File(resourcePath);
            //direct to index.html if resource /
            if(request.getResource().equals("/")){
                //redirect to default page
                for(File fil : dirContents){
                    if(fil.getName().startsWith(DEFAULT)){
                        file = new File(fil.getPath());
                        this.setContent(file);
                    }else{ this.content = this.listDirectory(dir).getBytes(); this.setHeaders(ResponseCode._200, "Content-Type:text/html"); }
                }

                this.setHeaders(ResponseCode._200, this.getContentType(file)); 

            }else if (file.exists()){
                //file found
                this.setHeaders(ResponseCode._200, this.getContentType(file));
                this.setContent(file);
            }else
                //file not found
                this.setHeaders(ResponseCode._404,null);
            break;
            case PUT:
            case POST:
            case TRACE:
            case CONNECT:
                this.setHeaders(ResponseCode._501, null);
        } 
    }

    /**
    *  Returns a list of the directories and files in a directory
    *  @param dir directory to list contents of
    *  @return a list of the directories and files in a directory, if fir not a directory return null
    */
    private String listDirectory(File dir){
        //if no index file print files and directories in www/
        StringBuilder fileList = new StringBuilder();
        
        if(dir.isDirectory()){
            for(String fileName: dir.list()){
                fileList.append("<a href =" + fileName + ">" + fileName + "</a></br>");
            }
            return fileList.toString();
            }
        return null;
    }

    /**
     * Write the HTTP headers and file if applicable to client
     */
    public void writeResponse(OutputStream out) throws IOException{
        //write headers
        for(String header : headers)
            out.write(header.getBytes());

        //write content
        if(this.content != null)
            out.write(this.content);
    }

    /**
     * Read the contents of a file
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
     * @param code HTTP response code
     */
    private void setHeaders(ResponseCode code, String contentType){
        this.addHeader(this.HTTP_VERSION  + " " + code.toString() + "\r\n");
        this.addHeader("Date: " + new Date().toString()+ "\r\n");
        this.addHeader("Server: Robert's Webserver"+ "\r\n");

        if(contentType != null)
            this.addHeader("Content-Type:" + contentType + "\r\n");

        this.addHeader("Connection: close"+ "\r\n\r\n");  
    } 

    /**
    *  Add HTTP header
    *  @param header header to add
    */
    public void addHeader(String header){
        this.headers.add(header);
    }

    /**
     * Return HTTP Response Headers
     * @return HTTP Response headers
     */
    public String getHeaders(){
        StringBuilder headers = new StringBuilder();

        for(String s: this.headers){
            headers.append(s + "\n");
        }
        return headers.toString();
    }

    /**
     * Return HTTP Response content
     * @return HTTP response content
     */
    public byte[] getContent(){
        return this.content;
    }

    
    /**
    *  Returns MIME Content type
    *  @param File file to guess content type
    *  @return MIME content type
    */
    public String getContentType(File file){
        return URLConnection.guessContentTypeFromName(file.getName());
    }
}
