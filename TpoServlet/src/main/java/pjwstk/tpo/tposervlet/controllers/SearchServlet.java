package pjwstk.tpo.tposervlet.controllers;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pjwstk.tpo.tposervlet.models.Film;
import pjwstk.tpo.tposervlet.models.SearchResult;
import pjwstk.tpo.tposervlet.services.DbService;
import pjwstk.tpo.tposervlet.services.IDbService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "searchServlet", value = "/searchServlet")
public class SearchServlet extends HttpServlet {

    IDbService dbService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        dbService = new DbService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Jestes w gecie searchservlet");

        try {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String categories = req.getParameter("categories");
        String directories = req.getParameter("directories");
        String watchers = req.getParameter("watchers");

        List<SearchResult> searchResults = dbService.getSearchedFilms(Arrays.asList(title, categories, directories, watchers));
        System.out.println("Films: " + searchResults);
        req.setAttribute("films", searchResults);
        req.getRequestDispatcher("/searchResults.jsp").forward(req, resp);
    }
}
