jHTTPServer
===========

Last Updated: 18/12/2013

Basic Java HTTP Server.

TODO:
  - connection not terminating after request
  - implement other HTTP status code


Example use:
$java Tester &
$ nc localhost 9000
GET /index.html HTTP/1.1
Host: localhost:9000
Connection: close;

HTTP/1.1 200 OK
Date: Wed Dec 18 16:19:47 GMT 2013
Server: Robert's Webserver
Connection: close

<html>
<head>
<title>Index.html - Sample</title>
</head>
<body>
<h1>Sample Web page</h1>
</html>
