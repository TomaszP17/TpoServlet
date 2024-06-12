package pjwstk.tpo.tposervlet.controllers;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pjwstk.tpo.tposervlet.models.Film;
import pjwstk.tpo.tposervlet.services.DbService;
import pjwstk.tpo.tposervlet.services.IDbService;

import java.io.IOException;

@WebServlet(name = "filmServlet", value = "/filmServlet")
public class FilmServlet extends HttpServlet {
    IDbService dbService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        dbService = new DbService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int filmId = Integer.parseInt(req.getParameter("filmId"));

        Film film = dbService.getAllAboutFilm(filmId);
        req.setAttribute("film", film);
        try {
            req.getRequestDispatcher("/film.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
