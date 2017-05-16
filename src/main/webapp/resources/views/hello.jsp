<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE  html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HelloTest</title>

</head>
<body>
<%
String test = (String) request.getAttribute("test2");
Integer num = (Integer) request.getAttribute("moi");
out.println(test + " : "+ num);
%>
</body>
</html>