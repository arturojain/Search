<%-- 
    Document   : index
    Created on : 31-ago-2014, 14:36:14
    Author     : arturojain
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="initial-scale=1.0">
  <title>Search.</title>
  <link rel="stylesheet" href="css/standardize.css">
  <link rel="stylesheet" href="css/index-grid.css">
  <link rel="stylesheet" href="css/index.css">
  <script src="js/jquery-min.js" type="text/javascript"></script>
  <script src="js/search.js" type="text/javascript"></script>
</head>
<body class="body index clearfix">
  <div class="search clearfix">
    <input id="search" class="_input" name="search" placeholder="Search." type="text" onkeyup="search();">
    <!-- select id="w" class="_select _select-1" name="W" onchange="search(); return false;">
      <option value="1">TFIDF</option>
      <option value="2">LOG-TFIDF</option>
    </select>
    <select id="q" class="_select _select-2" name="Q" onchange="search(); return false;">
      <option value="3">Espacios vectoriales</option>
      <option value="4">Coseno del ángulo</option>
    </select -->
    <select id="l" class="_select _select-1" name="L" onchange="search(); return false;">
      <option value="0">No lematizar</option>
      <option value="1">Lematizar</option>
    </select>
    <select id="r" class="_select _select-2" name="R" onchange="search(); return false;">
      <option value="0">0</option>
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
      <option value="4">4</option>
      <option value="5">5</option>
    </select>
    <select id="t" class="_select _select-3" name="T" onchange="search(); return false;">
      <option value="0">0</option>
      <option value="5">5</option>
      <option value="10">10</option>
      <option value="15">15</option>
      <option value="20">20</option>
    </select>
    <div id="results" class="results"></div>
  </div>
  <div class="element"></div>
</body>
</html>