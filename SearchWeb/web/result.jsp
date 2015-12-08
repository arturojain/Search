<%-- 
    Document   : result
    Created on : 31-ago-2014, 19:53:26
    Author     : arturojain
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.DatosBD"%>
<%@page import="model.Busqueda"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="initial-scale=1.0">
        <title>Título</title>
        <link rel="stylesheet" href="css/standardize.css">
        <link rel="stylesheet" href="css/result-grid.css">
        <link rel="stylesheet" href="css/result.css">
        <script src="js/jquery-min.js" type="text/javascript"></script>
        <script src="js/search.js" type="text/javascript"></script>
    </head>
    <body class="body result clearfix">
        <div id="contenido" idDoc="<%=request.getParameter("idDoc")%>"></div>
    </body>
</html>