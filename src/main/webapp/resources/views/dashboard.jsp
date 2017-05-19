<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.navarna.computerdb.mapper.*"%>
<%@page import="com.navarna.computerdb.model.*"%>
<%@page import="com.navarna.computerdb.dto.*"%>
<%@page import="com.navarna.computerdb.controller.*"%>
<%@page import="com.navarna.computerdb.exception.*"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="test"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
    String finUrl = null;
    String urlComplete = null;
    int nbElement = 10;
    Object obj = request.getAttribute("navigation");
    NavigationDashboardDTO navigation = null;
    if (obj instanceof NavigationDashboardDTO) {
        navigation = (NavigationDashboardDTO) obj;
        finUrl = navigation.getSearch();
        String typeSearch = navigation.getType();
        if (!finUrl.equals("")) {
            String retour = "";
            retour = finUrl.replace("+", "%2B");
            retour = finUrl.replace(" ", "+");
            finUrl = "?search=" + retour + "&type=" + typeSearch;
            finUrl += "&";
        } else {
            finUrl = "?";
        }

        nbElement = navigation.getNbElement();
        if (nbElement != 0) {
            urlComplete = finUrl + "nbElement=" + nbElement + "&";
        } else {
            urlComplete = "?";
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="label.title" /></title>
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
			<a class="navbar-brand" href="dashboard"> <spring:message
					code="label.title" />
			</a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<%
				    Integer totalElement = 0;
				    obj = request.getAttribute("totalElement");
				    if (obj instanceof Integer) {
				        totalElement = (Integer) obj;
				    }
				    out.print(totalElement.toString() + " ");
				%><spring:message code="label.count" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name"
							<%if (!navigation.getSearch().equals("")) {
                String nouvelleBalise = "value=\"" + navigation.getSearch() + "\"";
                out.print(nouvelleBalise);
            }%> />
						<input type="submit" id="searchsubmit"
							value="<spring:message code="label.filter"/>"
							class="btn btn-primary" /> <br> <input type="radio"
							name="type" value="computer.name" checked />
						<spring:message code="label.name" />
						<input type="radio" name="type" value="company.name" />
						<spring:message code="label.company" />
						<input type="radio" name="type" value="introduced" />
						<spring:message code="label.introduced" />
						<input type="radio" name="type" value="discontinued" />
						<spring:message code="label.discontinued" />
						<select class="form-horizontal" id="orderby" name="order">
							<option value="ASC"><spring:message code="label.asc" /></option>
							<option value="DESC"><spring:message code="label.desc" /></option>
						</select>
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message
							code="label.titleAdd" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
							code="label.del" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><spring:message code="label.name" /></th>
						<th><spring:message code="label.introduced" /></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="label.discontinued" /></th>
						<!-- Table header for Company -->
						<th><spring:message code="label.company" /></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<%
					    try {
					        if (request.getAttribute("computers") instanceof Page<?>) {
					            Page<?> listeComputer = (Page<?>) request.getAttribute("computers");

					            for (int i = 0; i < listeComputer.getPage().size(); i++) {
					                Object o = listeComputer.getPage().get(i);
					                if (o instanceof ComputerDTO) {
					                    ComputerDTO computerDTO = (ComputerDTO) o;
					                    String affichage = "<tr> <td class=\"editMode\"><input type=\"checkbox\" name=\"cb\"class=\"cb\" value=\""
					                            + computerDTO.getId() + "\"></td><td><a href=\"editComputer?id="
					                            + computerDTO.getId() + "\" onclick=\"\">" + computerDTO.getName() + "</a></td>";
					                    affichage += "<td>" + computerDTO.getIntroduced() + "</td>";
					                    affichage += "<td>" + computerDTO.getDiscontinued() + "</td>";
					                    affichage += "<td>" + computerDTO.getNameCompany() + "</td></tr>";
					                    out.println(affichage);
					                }
					            }
					        }
					    } catch (ClassCastException ce) {
					        throw new ControllerException("Le cast de page<Computer> à echouer", ce);
					    }
					%>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<%
				    Integer max = totalElement / nbElement;

				    Integer courant = navigation.getPage();
				    if (max != 0) {
				        String preview;
				        if (courant > 1) {
				            preview = "<li><a href=" + urlComplete + "page=" + (courant - 1)
				                    + " aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span></a></li>";
				        } else {
				            preview = "<li><a href=" + urlComplete + "page=" + 1
				                    + " aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span></a></li>";
				        }
				        out.println(preview);
				        String next;
				        if (courant < max) {
				            next = "<li><a href=" + urlComplete + "page=" + (courant + 1)
				                    + " aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span></a></li>";

				        } else {
				            next = "<li><a href=" + urlComplete + "page=" + max
				                    + " aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span></a></li>";

				        }
				        if (courant + 3 < max) {
				            max = courant + 3;
				        }
				        if (courant - 3 > 1) {
				            courant = courant - 3;
				        } else {
				            courant = 1;
				        }
				        for (int i = courant; i < max; i++) {
				            String affiche = "<li><a href=\"" + urlComplete + "page=" + i + "\">" + i + "</a></li>";
				            out.println(affiche);
				        }
				        out.println(next);
				    }
				%>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" class="btn btn-default"
					<%String lien = "onClick=\"javascript:document.location.href='" + finUrl + "nbElement=10" + "'\"";
            out.print(lien);%>>10</button>
				<button type="button" class="btn btn-default"
					<%lien = "onClick=\"javascript:document.location.href='" + finUrl + "nbElement=50" + "'\"";
            out.print(lien);%>>50</button>
				<button type="button" class="btn btn-default"
					<%lien = "onClick=\"javascript:document.location.href='" + finUrl + "nbElement=100" + "'\"";
            out.print(lien);%>>100</button>
			</div>
		</div>
	</footer>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>