<%@ page import="pjwstk.tpo.tposervlet.models.SearchResult" %>
<%@ page import="java.util.List" %>
<%@ page import="pjwstk.tpo.tposervlet.models.Actor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wynik Wyszukiwania</title>
</head>
<body>

<h1>Witaj w wyszukaniu</h1>
<br>
<h2>Wyniki wyszukiwania:</h2>

<%
    List<SearchResult> results = (List<SearchResult>) request.getAttribute("films");
    if (results != null && !results.isEmpty()) {
        for (SearchResult result : results) {
%>
<h3>Tytuł: <%= result.getFilm().getTitle() %></h3>
<p>Kategoria: <%= result.getCategory().getName() %></p>
<p>Reżyser: <%= result.getFilm().getDirector() %></p>
<p>Ilość widzów: <%= result.getWatcherList().size() %></p>
<p>Aktorzy:</p>
<ul>
    <% for (Actor actor : result.getActorList()) { %>
    <li><%= actor.getFirstName() %> <%= actor.getLastName() %></li>
    <% } %>
</ul>
<hr>
<%
    }
} else {
%>
<p>Brak wyników.</p>
<%
    }
%>

</body>
</html>