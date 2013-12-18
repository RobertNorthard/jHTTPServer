
/**
 * Enumeration class Method Codes
 * 
 * @author Robert Northard
 * @version 18/12/2013
 */
public enum MethodCode{
    GET("GET"),

    HEAD("HEAD"),

    POST("POST"),

    PUT("PUT"),

    DELETE("DELETE"),

    TRACE("TRACE"),

    CONNECT("CONNECT"),

    UNRECOGNIZED(null);

    private final String method;

    private MethodCode(String method) {
        this.method = method;
    }
}