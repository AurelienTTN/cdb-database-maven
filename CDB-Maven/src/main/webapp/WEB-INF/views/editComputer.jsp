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
                    <div class="label label-default pull-right" id="idActualComputer">
                     id=${computerActual.id}
                    </div>
                    <h1>Edit Computer</h1>

                    <form:form action="sendComputer" method="POST" modelAttribute="computerDTO">
                        <form:input path="id" name="id" type="hidden" value="${computerActual.id}"/> 
                        <fieldset>
                            <div class="form-group">
                                <form:label path="name">Computer name : ${computerActual.name}</form:label>
                                <form:input type="text" value="${computerActual.name}" class="form-control" path="name" placeholder="Computer name"/>
                            </div>
                            <div class="form-group">
                                <form:label path="dateEntree">Introduced date : ${computerActual.dateEntree} </form:label>
                                <form:input type="date" class="form-control" value="${computerActual.dateEntree }" path="dateEntree" placeholder="Introduced date"/>
                            </div>
                            <div class="form-group">
                                <form:label path="dateSortie">Discontinued date : ${computerActual.dateSortie}</form:label>
                                <form:input type="date" class="form-control" value= "${computerActual.dateSortie}" path="dateSortie" placeholder="Discontinued date"/>
                            </div>
                            <div class="form-group">
                                <form:label path="company">Company : ${computerActual.company}</form:label>
                                <form:select class="form-control" path="company">
                                	
                                 <c:forEach items="${listeCompany}" var="company">
                                    <option value="${company.id}">${company.name}</option>
								</c:forEach>
                                    <option value="0">--</option>
                                </form:select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>