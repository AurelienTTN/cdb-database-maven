<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="utf-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
            <a class="navbar-brand" href="dashboard?new=1"> <spring:message code="label.title"/></a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="add.title"/></h1>
                    <form:form method="POST" action="addComputer" modelAttribute="computerDTO">
                        <fieldset>
                            <div class="form-group">
                                <form:label path="name"><spring:message code="computer.title"/></form:label>
         						<spring:message code="computer.title" var="computerTitle"/>
                                <form:input required="required" type="text" class="form-control" path="name" placeholder="${computerTitle}"/>
                            </div>
                            <div class="form-group">
                                <form:label path="dateEntree"><spring:message code="introduced.title"/></form:label>
                                	<spring:message code="introduced.title" var="introducedTitle"/>
                                <form:input type="date" class="form-control" path="dateEntree" placeholder="${introducedTitle}"/>
                            </div>
                            <div class="form-group">
                                <form:label path="dateSortie"><spring:message code="discontinued.title"/></form:label>
                                	<spring:message code="discontinued.title" var="discontinuedTitle"/>
                                <form:input type="date" class="form-control" path="dateSortie" placeholder="${discontinuedTitle}"/>  
                            </div>
                            <div class="form-group">
                                <form:label path="company"><spring:message code="company.title"/></form:label>
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
                        	<spring:message code="add.button" var="addButton"/>
                            <input type="submit" value="${addButton}" class="btn btn-primary">
                            <spring:message code="or.label" />
                            <a href="dashboard" class="btn btn-default"> <spring:message code="cancel.button" /></a>
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