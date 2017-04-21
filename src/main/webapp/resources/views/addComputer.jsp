<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.navarna.computerdb.model.*"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>
	<%
	    Object attribut = request.getAttribute("reponse");
	    if ((attribut != null) && (attribut instanceof Integer)) {
	        int reponseInsertion = (Integer) attribut;
	        String affiche = "";
	        if(reponseInsertion == 0){
	            affiche ="Le Computer a été inséré";
	        }
	        else {
	            affiche = "Le Computer n'a pas été insérer";
	        }
	        affiche ="<div class=\"alert\"><span class=\"closebtn\" onclick=\"this.parentElement.style.display='none';\">&times;</span>"+affiche+"</div>";

	        out.println(affiche);
	    }
	%>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="AddComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" name="name" id="computerName"
									placeholder="Computer name">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" name="introduced"
									id="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" name="discontinued"
									id="discontinued" placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="idCompany">
									<%
									    attribut = request.getAttribute("listeCompany");
									    if (attribut instanceof ArrayList) {
									        ArrayList<?> liste = (ArrayList<?>) attribut;
									        for (Object o : liste) {
									            if (o instanceof Company) {
									                Company company = (Company) o;
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
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="Dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>