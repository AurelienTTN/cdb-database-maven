<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard?new=1"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${page.nbElementTotal } Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="dashboard" method="POST">
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
                             <a href="dashboard?orderBy=name" onclick="">Computer Name</a>
                        </th>
                        <th>
                        	<a href="dashboard?orderBy=introduced" onclick=""> Introduced date</a>
                           
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                        	<a href="dashboard?orderBy=discontinued" onclick=""> Discontinued date</a>
                            
                        </th>
                        <!-- Table header for Company -->
                        <th>
                        	<a href="dashboard?orderBy=company_id" onclick=""> Company</a>
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
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a href="?boutonDebFin=0" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              <li>
              <c:if test = "${page.numeroPage -2 > 0}">
              <a href="?num=-2">${page.numeroPage - 2}</a></c:if></li>
              <li>
              <c:if test = "${page.numeroPage -1 > 0}">
              <a href="?num=-1">${page.numeroPage - 1}</a></c:if></li>
              <li><a href="?num=0">.. </a></li>
               
              <li><c:if test = "${page.numeroPage <= page.nbElementTotal/page.nbLigne}">
              <a href="?num=1">${page.numeroPage + 1}</a></c:if></li>
              <li>
              <c:if test = "${page.numeroPage + 1 <= page.nbElementTotal/page.nbLigne}">
              <a href="?num=2">${page.numeroPage + 2}</a></c:if></li>
              <li>
                <a href="?boutonDebFin=1" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
        

        <div class="btn-group btn-group-sm pull-right" role="group" >
        	<form id="pageSize" action="#" method="GET" class="form-inline">
	            <button type="submit" class="btn btn-default" name = "bouton" value="10" >10</button>
	            <button type="submit" class="btn btn-default" name="bouton" value="50">50</button>
	            <button type="submit" class="btn btn-default" name="bouton" value="100">100</button>
            </form>
        </div>

    </footer>
<script src="./js/jquery.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/dashboard.js"></script>

</body>
</html>