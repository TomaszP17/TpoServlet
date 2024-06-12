<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pjwstk.tpo.tposervlet.models.Film" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>Category</title>

    <style>
        .lista {
            border-width: 2px;
            border-style: solid;
            border-radius: 10px;
            transition: all .5s ease-in-out;
            cursor: pointer;
            margin-bottom: 10px;
        }

        .lista:hover {
            transform: scale(101%);
        }
        a {
            text-decoration: none;
            color: black;
        }
    </style>

</head>
<body>
<h1>Witaj na stronie z wynikiem dla konkretnej kategorii <%= request.getAttribute("catName") %>
</h1>
<ul>
    <% List<Film> films = (List<Film>) request.getAttribute("films");
        if (films != null) {
            for (Film film : films) { %>
    <a href="${pageContext.request.contextPath}/filmServlet?filmId=<%=film.getId()%>">
        <li class="lista">
            <h2><%= film.getTitle() %>
            </h2>
            <p><%= film.getDescription() %>
            </p>
            <p>Directed by: <%= film.getDirector() %>
            </p>
            <% Map<Film, Integer> filmsWatchers = (Map<Film, Integer>) request.getAttribute("filmsWatchers");
            int numberOfWatchers = filmsWatchers.get(film); %>
            <p>Number of watchers: <%=numberOfWatchers%></p>
            <br>
        </li>
    </a>
    <% }
    } %>
</ul>
</body>
</html>
