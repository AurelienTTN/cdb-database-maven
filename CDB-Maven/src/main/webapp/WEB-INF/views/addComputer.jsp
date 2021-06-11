<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="utf-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="./resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard?new=1"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form:form method="POST" action="addComputer" modelAttribute="computerDTO">
                        <fieldset>
                            <div class="form-group">
                                <form:label path="name">Computer name</form:label>
                                <form:input required="required" type="text" class="form-control" path="name" placeholder="Computer name"/>
                            </div>
                            <div class="form-group">
                                <form:label path="dateEntree">Introduced date</form:label>
                                <form:input type="date" class="form-control" path="dateEntree" placeholder="Introduced date"/>
                            </div>
                            <div class="form-group">
                                <form:label path="dateSortie">Discontinued date</form:label>
                                <form:input type="date" class="form-control" path="dateSortie" placeholder="Discontinued date"/>  
                            </div>
                            <div class="form-group">
                                <form:label path="company">Company</form:label>
                                <select class="form-control" id="company" name="company" >
                               
                                <c:forEach items="${listeCompany}" var="company">
                                    <option value="${company.id}">${company.name}</option>
								</c:forEach>
                               
                                </select>
                            </div>                  
                        </fieldset>
                        
                        <span id=alerteDate style=display:none>Dates incorrectes</span>
                        <span id=alerteName style=display:none>Veuillez rentrer un nom correct</span>
                        <div class="actions pull-right">
                            <input type="submit" value="addComputer" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
    <script src="./resources/js/jquery.min.js"></script>
	<script src="./resources/js/bootstrap.min.js"></script>
    <script src="./resources/js/verificationAddComputer.js"></script>
</body>
</html>