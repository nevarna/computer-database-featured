<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.navarna.computerdb.dto.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="label.titleAdd"/></title>
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
			<a class="navbar-brand" href="dashboard"><spring:message code="label.title"/></a>
		</div>
	</header>
	<%
	    String nameCompany = "";
	%>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form:form modelAttribute="computerDTO" action="addComputer"
						method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName"> <spring:message code="label.name"/></label> <input
									type="text" class="form-control" name="name" id="computerName"
									placeholder="Computer name">
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="label.introduced"/></label> <input
									type="date" class="form-control" name="introduced"
									id="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="label.discontinued"/></label> <input
									type="date" class="form-control" name="discontinued"
									id="discontinued" placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="label.company"/></label> <select
									class="form-control" id="companyId" name="idCompany">
									<%
									    Object attribut = request.getAttribute("listeCompany");
									        if (attribut instanceof ArrayList) {
									            ArrayList<?> liste = (ArrayList<?>) attribut;
									            for (Object o : liste) {
									                if (o instanceof CompanyDTO) {
									                    CompanyDTO company = (CompanyDTO) o;
									                    String affiche = "<option value=\"" + company.getId() + "\">" + company.getName()
									                            + "</option>";
									                    out.println(affiche);
									                }
									            }
									        }
									%>

								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="label.add"/>" class="btn btn-primary">
							<spring:message code="label.or"/> <a href="dashboard" class="btn btn-default"><spring:message code="label.cancel"/></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>