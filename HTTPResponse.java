import java.util.*;
import java.io.*;

/**
 * Write a description of class HTTPReponse here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HTTPResponse
{
    private HTTPRequest request = null;
    private static final String HTTP_VERSION = "HTTP/1.1";
    private List<String> headers = new ArrayList<String>();
    private byte[] content = null;

    public HTTPResponse(HTTPRequest request){
        this.request = request;
    }
    
    public void handleRequest()
                    throws IOException{
        
        String requestedResource = this.request.getResource();
        switch(this.request.getRequestMethod()){
            
            case HEAD: 
                this.setHeaders(ResponseCode._200);
                break;
            case GET: 
                File file = new File("www/" + requestedResource);
                if(file.exists() && !requestedResource.equals("/")){
                    this.setHeaders(ResponseCode._200);
                    this.getFile(file);
                }else{
                    this.setHeaders(ResponseCode._404);
                }
        } 
    }
    
    public void getFile(File file)
                                throws IOException{
                                    
        InputStream in = new FileInputStream(file);
        this.content = new byte[(int)file.length()];
        int offSet = 0;
        while(offSet < file.length()){
            
            int rc = in.read(this.content, offSet, (content.length - offSet));
            offSet += rc;
        }
        
        in.close(); 
    }
    
    public void write(OutputStream out)
                                throws IOException{
        for(String header : this.headers)
            out.write(header.getBytes());
        
        if(this.content != null)
            out.write(this.content);
    }
    
    public void setHeaders(ResponseCode code){
        
         headers.add(this.HTTP_VERSION  + " " + code.toString() + "\r\n");
                headers.add("Date: " + new Date().toString()+ "\r\n");
                headers.add("Server: Robert's Webserver"+ "\r\n");
                headers.add("Connection: close"+ "\r\n\r\n");
        
    } 
}
