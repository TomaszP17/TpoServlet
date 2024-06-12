<%@ page import="java.util.List" %>
<%@ page import="pjwstk.tpo.tposervlet.models.Category" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>PjatkHub - darmowe filmy z uczelni</title>
    <style>
        body {
            background-color: black;
            color: aliceblue;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            font-family: Arial, sans-serif;
        }
        .header, .nav, .content-title, .content, .footer {
            width: 80%;
            text-align: center;
            margin: 0 auto;
        }
        .nav {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #222;
            padding: 10px 20px;
            width: 100%;
        }
        .nav .title {
            color: darkorange;
            font-size: 24px;
        }
        .nav .title span {
            color: white;
        }
        .login-button {
            background-color: darkorange;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
        }
        .content-title {
            margin-top: 20px;
        }
        .content {
            display: grid;
            grid-template-columns: repeat(5, minmax(150px, 1fr));
            gap: 20px;
            list-style: none;
            padding: 0;
            justify-items: center;
            margin-top: 20px;
        }
        .category {
            background-color: #444;
            padding: 20px;
            border-radius: 5px;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        .category img {
            max-width: 100px;
            margin-bottom: 10px;
        }
        .content-title .title {
            color: darkorange;
        }
        .footer {
            margin-top: auto;
            background-color: #222;
            padding: 10px 0;
            width: 100%;
        }
        .link {
            color: darkorange;
        }
        /* Modal Styles */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.8);
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .modal-content {
            background-color: #444;
            padding: 20px;
            border-radius: 5px;
            text-align: center;
            color: white;
        }
        .modal-content h2 {
            color: darkorange;
        }
        .modal-content button {
            background-color: darkorange;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
            margin: 10px;
        }
    </style>
</head>
<body>
<div class="nav">
    <div class="title">Pjatk<span>Hub</span></div>
    <%--<button class="login-button" onclick="location.href='login-servlet'">Zaloguj się</button>--%>
</div>
<div class="content-title">
    <h1 class="title">BAZA DANYCH FILMOW DLA DOROSLYCH</h1>
    <h2>Wybierz kategorie</h2>
</div>
<ul class="content">
    <%
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        int counter = 0;
        for(Category category : categories) {
    %>
    <li class="category">
        <img src="<%= request.getContextPath() %>/images/<%= "animejpg" + ++counter %>.jpg" alt="<%= category.getName() %>">
        <a class="link" href="${pageContext.request.contextPath}/categoryServlet?id=<%= category.getId() %>"><%= category.getName() %></a>
    </li>
    <%
        }
    %>
</ul>
<br/>
<div class="content-title">
    <h2 class="title">WYSZUKAJ FILM</h2>
    <h3>WYBIERZ SPOŚRÓD POLECANYCH FILMÓW KABE:</h3>
    <form action="${pageContext.request.contextPath}/searchServlet" method="POST">>
        <label for="title">Tytuł:</label><br>
        <input type="text" id="title" name="title" placeholder="Przeszukaj PjatkHub"></input><br>
        <label for="categories">Wybierz kategorie:</label><br>

        <select name="categories" id="categories">
            <option value="brak">Brak</option>
            <option value="Informatyka">Informatyka</option>
            <option value="Zarządzanie Informacją">Zarządzanie Informacją</option>
            <option value="Sztuka Nowych Mediów">Sztuka Nowych Mediów</option>
            <option value="Architektura Wnętrz">Architektura Wnętrz</option>
            <option value="Kultura Japonii">Kultura Japonii</option>
        </select>
        <br>
        <label for="categories">Wybierz reżysera:</label><br>
        <select name="directories" id="directories">
            <option value="brak">Brak</option>
            <option value="Anthony Russo">Anthony Russo</option>
            <option value="Taika Waititi">Taika Waititi</option>
            <option value="Sofia Coppola">Sofia Coppola</option>
            <option value="Todd Phillips">Todd Phillips</option>
            <option value="Christopher Nolan">Christopher Nolan</option>
        </select>
        <br>
        <label for="watchers">Liczba widzów od: </label><br>
        <input type="text" id="watchers" name="watchers" placeholder="Minimalna liczba widzów filmu"><br>
        <input type="submit" class="login-button" value="Wyszukaj">
    </form>
</div>
<div class="footer">
    <p>MATERIALY ZOSTALY NAGRANE W PIWNICY UCZELNI POLSKO-JAPOŃSKIEJ AKADEMII TECHNIK KOMPUTEROWYCH</p>
</div>
<!-- Modal HTML -->
<div id="ageModal" class="modal">
    <div class="modal-content">
        <h2>Ograniczenie wiekowe</h2>
        <p>Ta strona zawiera treści tylko dla dorosłych. Proszę potwierdzić, że masz co najmniej 18 lat.</p>
        <button id="confirmAge">Mam co najmniej 18 lat</button>
        <button id="denyAge">Nie mam 18 lat</button>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const ageConfirmed = localStorage.getItem('ageConfirmed');
        const modal = document.getElementById('ageModal');

        const page = document.getElementsByClassName("main-app")
        if (!ageConfirmed) {
            modal.style.display = 'flex';
            page.style.filter = 'blur(4px)'
        }

        document.getElementById('confirmAge').addEventListener('click', function () {
            localStorage.setItem('ageConfirmed', 'true');
            modal.style.display = 'none';
        });

        document.getElementById('denyAge').addEventListener('click', function () {
            alert('Musisz mieć co najmniej 18 lat, aby przeglądać tę stronę.');
            window.location.href = 'https://www.pja.edu.pl';
        });
    });
</script>
</body>
</html>
