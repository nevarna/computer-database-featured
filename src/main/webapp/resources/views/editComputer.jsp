<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.ArrayList"%>
<%@page import="com.navarna.computerdb.dto.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <%
        Object attribut = request.getAttribute("reponse");
        if ((attribut != null) && (attribut instanceof Integer)) {
            int reponseInsertion = (Integer) attribut;
            String affiche = "";
            if(reponseInsertion == 1){
                affiche ="Le Computer a été modifié";
            }
            else {
                affiche = "Le Computer n'a pas été modifié";
            }
            affiche ="<div class=\"alert\"><span class=\"closebtn\" onclick=\"this.parentElement.style.display='none';\">&times;</span>"+affiche+"</div>";

            out.println(affiche);
        }
    %>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        <%
                        attribut = request.getAttribute("id");
                        long id = 0 ;
                        if(attribut instanceof Long){
                            id = (Long) attribut;
                            out.println("id :"+id);
                        }
                        %>
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="editComputer" method="POST">
                        <input type="hidden" <%out.println("value=\""+id+"\""); %> id="id"name="id"/> 
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" placeholder="Computer name" name="name">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" placeholder="Introduced date" name="introduced">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" placeholder="Discontinued date" name="discontinued">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="idCompany">
                                    <%
                                        attribut = request.getAttribute("listeCompany");
                                        if (attribut instanceof ArrayList) {
                                            ArrayList<?> liste = (ArrayList<?>) attribut;
                                            for (Object o : liste) {
                                                if (o instanceof CompanyDTO) {
                                                    CompanyDTO company = (CompanyDTO) o;
                                                    String affiche = "<option value=\"" + company.getId() + "\">" + company.getName() + "</option>";
                                                    out.println(affiche);
                                                }
                                            }
                                        }
                                    %>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>