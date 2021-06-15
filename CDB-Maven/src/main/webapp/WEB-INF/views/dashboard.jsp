<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="./resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="new"> <spring:message code="label.title"/></a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${session.page.nbElementTotal } <spring:message code="computer.found"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="search" action="search#" method="GET" class="form-inline">
						<spring:message code="text.search" var="searchText"/>
                        <input type="search" id="search" name="search" class="form-control" placeholder="${searchText}" />
                        <spring:message code="text.search" var="searchText2"/>
                        <input type="submit" id="searchsubmit" value="${searchText}"
                        class="btn btn-primary"/>
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="add.computer"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="edit.button"/></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="delete#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" name="selectall"/> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        
                        <th>
                             <a href="order?orderBy=name" onclick=""><spring:message code="computer.title"/></a>
                        </th>
                        <th>
                        	<a href="order?orderBy=introduced" onclick=""><spring:message code="introduced.title"/></a>
                           
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                        	<a href="order?orderBy=discontinued" onclick=""><spring:message code="discontinued.title"/></a>
                            
                        </th>
                        <!-- Table header for Company -->
                        <th>
                        	<a href="order?orderBy=company_id" onclick=""> <spring:message code="company.title"/></a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	
                	<c:forEach items="${listeComputer}" var="computer">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
                        </td>
                        <td>
                            <a href="editComputer?computerID=${computer.id}" onclick="">${computer.name}</a>
                            
                        </td>
                        <td>${computer.dateEntree}</td>
                        <td>${computer.dateSortie}</td>
                        <td>${computer.company.name}</td>
                    </tr>
                    </c:forEach>
             
                
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
    
      <div class="btn-group btn-group-sm pull-left" role="group" >
      	
        <form id="bouton" action="dashboard?langue#" method="GET" class="form-inline">
	            <button type="submit" class="btn btn-default" name = "lang" value="EN" >Anglais</button>
	            <button type="submit" class="btn btn-default" name="lang" value="FR">Français</button>
         </form>        
       </div>
	         
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a href="extremites?boutonDebFin=0" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              <li>
              <c:if test = "${session.page.numeroPage -2 > 0}">
              <a href="getNumPage?num=-2">${session.page.numeroPage - 2}</a></c:if></li>
              <li>
              <c:if test = "${session.page.numeroPage -1 > 0}">
              <a href="getNumPage?num=-1">${session.page.numeroPage - 1}</a></c:if></li>
              <li><a href="getNumPage?num=0">.. </a></li>
               
              <li><c:if test = "${session.page.numeroPage <= session.page.nbElementTotal/session.page.nbLigne}">
              <a href="getNumPage?num=1">${session.page.numeroPage + 1}</a></c:if></li>
              <li>
              <c:if test = "${session.page.numeroPage + 1 <= session.page.nbElementTotal/session.page.nbLigne}">
              <a href="getNumPage?num=2">${session.page.numeroPage + 2}</a></c:if></li>
              <li>
                <a href="extremites?boutonDebFin=1" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
        

        <div class="btn-group btn-group-sm pull-right" role="group" >
        	<form id="bouton" action="getNbElement#" method="GET" class="form-inline">
	            <button type="submit" class="btn btn-default" name = "bouton" value="10" >10</button>
	            <button type="submit" class="btn btn-default" name="bouton" value="50">50</button>
	            <button type="submit" class="btn btn-default" name="bouton" value="100">100</button>
            </form>
        </div>
       </div>

    </footer>
<script src="./resources/js/jquery.min.js"></script>
<script src="./resources/js/bootstrap.min.js"></script>
<script src="./resources/js/dashboard.js"></script>

</body>
</html>