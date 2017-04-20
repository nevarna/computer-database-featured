<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.navarna.computerdb.mapper.*"%>
<%@page import="com.navarna.computerdb.model.*"%>
<%@page import="com.navarna.computerdb.controller.*"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="test"%>
<% String finUrl ; 
Object obj = request.getAttribute("research");
if(obj instanceof String){
    finUrl =(String) obj ;
    finUrl +="&";
}
else {
    finUrl="?";
}
finUrl +="page=";
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
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">121 Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" 
							<%if ((request.getAttribute("name")!=null)&&(request.getAttribute("name") instanceof String)){
							    String nouvelleBalise = "value=\""+request.getAttribute("name")+"\"";
							    out.print(nouvelleBalise);
							}    %>/> <input
							
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
			<input type="hidden" name="selection" value="">
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
					try{
					    if(request.getAttribute("computers") instanceof Page<?>){
					        Page<?> listeComputer = (Page<?>) request.getAttribute("computers");
					           
					        for (int i = 0 ; i < listeComputer.getPage().size(); i++){
					            Object o = listeComputer.getPage().get(i);
					            if( o instanceof Computer){
					                Computer computer = (Computer) o;
					                String affichage = "<tr> <td class=\"editMode\"><input type=\"checkbox\" name=\"cb\"class=\"cb\" value=\"0\"></td><td><a href=\"editComputer.html\" onclick=\"\">"
		                                    + computer.getName() + "</a></td>";
		                            affichage += "<td>" + computer.getIntroduced() + "</td>";
		                            affichage += "<td>" + computer.getDiscontinued() + "</td>";
		                            affichage += "<td>" + computer.getCompany().getName() + "</td></tr>";
		                            out.println(affichage);
					            }
					        }
					    }
					} catch (ClassCastException ce){
					    throw new ControllerException ("Le cast de page<Computer> à echouer", ce);
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
			String preview = "<li><a href="+finUrl+"-1 aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span></a></li>";
			out.println(preview);
			for (int i = 1 ; i < 6 ; i++){
			    String affiche = "<li><a href=\""+finUrl+i+"\">"+i+"</a></li>";
			    out.println(affiche);
			}
			String next = "<li><a href="+finUrl+"0 aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span></a></li>";
            out.println(next);			
			%>
			</ul>
		</div>
		<div class="btn-group btn-group-sm pull-right" role="group">
			<button type="button" class="btn btn-default">10</button>
			<button type="button" class="btn btn-default">50</button>
			<button type="button" class="btn btn-default">100</button>
		</div>

	</footer>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>