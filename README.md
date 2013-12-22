jHTTPServer
===========

A basic HTTP Server. 

<h3>HTTP Library</h3>


The HTTP library that is based on the <a href="http://www.ietf.org/rfc/rfc2616.txt">RFC2616 HTTP/1.1 Specification</a>. It provides a HTTPRequest class for dealing with HTTP requests and contrary a HTTP response class.

<h3>Example use</h3>
<pre><code>$ java Tester &
$ nc localhost 9000 < http-request
</pre></code>

<h3>HTTP Request Example</h3>

<pre><code>GET /index.html HTTP/1.1
Host: localhost:9000
Connection: close
</pre></code>

<h3>HTTP Response Example</h3>
<pre><code>HTTP/1.1 200 OK
Content-Type:text\html
Connection:close
</pre></code>

Please not the request/responses line/headers must all end with carriage return and a line feed.

<h3>TODO</h3>

<ul>
<li>Log HTTP Requests and Responses</li>
<li>Add properties file</li>
Allow user specify /www directory and optional response headers. i.e. Server. 
</ul>

<h3>Author</h3>

Robert Northard
