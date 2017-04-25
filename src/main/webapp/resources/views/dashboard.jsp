<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.navarna.computerdb.mapper.*"%>
<%@page import="com.navarna.computerdb.model.*"%>
<%@page import="com.navarna.computerdb.dto.*"%>
<%@page import="com.navarna.computerdb.controller.*"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="test"%>
<%
    String finUrl;
    Object obj = request.getAttribute("research");
    if (obj instanceof String) {
        finUrl = (String) obj;
        finUrl += "&";
    } else {
        finUrl = "?";
    }
%>
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
			<a class="navbar-brand" href="Dashboard"> Application - Computer
				Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<%
				    obj = request.getAttribute("totalElement");
				    if (obj instanceof Integer) {
				        Integer totalElement = (Integer) obj;
				        String affichage = totalElement.toString() + " Computers found";
				        out.print(affichage);
				    }
				%>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name"
							<%if ((request.getAttribute("name") != null) && (request.getAttribute("name") instanceof String)) {
                String nouvelleBalise = "value=\"" + request.getAttribute("name") + "\"";
                out.print(nouvelleBalise);
            }%> />
						<input type="radio" name="type" value="Computer" checked />Computer
						<input type="radio" name="type" value="Company" />Company <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="AddComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="2">
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
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

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
					                            + computerDTO.getId() + "\"></td><td><a href=\"EditComputer?id="
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
				    obj = request.getAttribute("maxPage");
				    Object obj2 = request.getAttribute("pageCurrente");
				    if ((obj instanceof Integer) && (obj2 instanceof Integer)) {
				        Integer max = (Integer) obj;
				        Integer courant = (Integer) obj2;
				        if (max != 0) {
				            String preview;
				            if (courant > 1) {
				                preview = "<li><a href=" + finUrl + "page=" + (courant - 1)
				                        + " aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span></a></li>";
				            } else {
				                preview = "<li><a href=" + finUrl + "page=" + 1
				                        + " aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span></a></li>";
				            }
				            out.println(preview);
                            String next;
                            if (courant < max) {
                                next = "<li><a href=" + finUrl + "page=" + (courant + 1)
                                        + " aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span></a></li>";

                            } else {
                                next = "<li><a href=" + finUrl + "page=" + max
                                        + " aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span></a></li>";

                            }
				            if (courant + 5 < max) {
				                max = courant + 5;
				            }
				            if (courant - 5 > 1) {
				                courant = courant - 5;
				            } else {
				                courant = 1;
				            }
				            for (int i = courant; i < max; i++) {
				                String affiche = "<li><a href=\"" + finUrl + "page=" + i + "\">" + i + "</a></li>";
				                out.println(affiche);
				            }
				            out.println(next);
				        }
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