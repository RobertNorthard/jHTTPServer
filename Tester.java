
/**
 * A class for dealing with HTTP responses
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tester
{
    public static void main(String[] args) 
                                       throws Exception{
    
        new HTTPServer("Hi", 9000, "www").run(); 
        
    }
    
   
}
