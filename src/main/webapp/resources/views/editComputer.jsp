<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.navarna.computerdb.dto.*"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="label.titleEdit"/></title>
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
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						<%
						    ComputerDTO computerDTO = (ComputerDTO) request.getAttribute("computerDTO");
						%>
					</div>
					<h1><spring:message code="label.titleEdit"/></h1>

					<form:form modelAttribute="computerDTO" action="editComputer"
						method="POST">
						<input type="hidden"
							<%out.println("value=\"" + computerDTO.getId() + "\"");%> id="id"
							name="id" />
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="label.name"/></label> <input
									type="text" class="form-control" id="computerName"
									placeholder="Computer name" name="name"
									<%out.println("value=\"" + computerDTO.getName() + "\"");%>>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="label.introduced"/></label> <input
									type="date" class="form-control" id="introduced"
									placeholder="Introduced date" name="introduced"
									<%if(computerDTO.getIntroduced() != null)out.println("value=\"" + computerDTO.getIntroduced() + "\"");%>>
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="label.discontinued"/></label> <input
									type="date" class="form-control" id="discontinued"
									placeholder="Discontinued date" name="discontinued"
									<%if(computerDTO.getDiscontinued() != null) out.println("value=\"" + computerDTO.getDiscontinued() + "\"");%>>
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
									                    String affiche;
									                    if (company.getId() == computerDTO.getIdCompany()) {
									                        affiche = "<option value=\"" + company.getId() + "\" selected>" + company.getName()
									                                + "</option>";
									                    } else {
									                        affiche = "<option value=\"" + company.getId() + "\">" + company.getName()
									                                + "</option>";
									                    }

									                    out.println(affiche);
									                }
									            }
									        }
									%>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="label.edit"/>" class="btn btn-primary">
							<spring:message code="label.or"/> <a href="dashboard" class="btn btn-default"><spring:message code="label.cancel"/></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>