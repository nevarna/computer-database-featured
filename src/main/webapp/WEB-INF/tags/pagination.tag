<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="page" required="true" type="java.lang.String"%>
<%@ attribute name="lien" required="true" type="java.lang.String" %>
<%@ attribute name="test" type="java.lang.String"%>
<td>
<%
    String fini = lien + " " + page + " " + test;
    System.out.println(fini);
    out.print(fini); %>
</td>