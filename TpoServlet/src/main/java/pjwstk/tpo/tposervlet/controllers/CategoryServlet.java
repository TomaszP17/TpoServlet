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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "categoryServlet", value = "/categoryServlet")
public class CategoryServlet extends HttpServlet {

    IDbService dbService = new DbService();

    @Override
    public void init(ServletConfig config) throws ServletException {
        dbService = new DbService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryId = req.getParameter("id");
        System.out.println("CATEGORY ID: " + categoryId);

        String categoryName = dbService.getCategoryNameById(Integer.parseInt(categoryId));
        List<Film> films = dbService.getAllFilmsByCategories(Integer.parseInt(categoryId));
        Map<Film, Integer> filmsWatchers = new HashMap<>();

        films.forEach(el -> filmsWatchers.put(el, dbService.getSumOfWatcherByFilmId(el.getId())));

        req.setAttribute("catName", categoryName);
        req.setAttribute("films", films);
        req.setAttribute("filmsWatchers", filmsWatchers);

        try {
            req.getRequestDispatcher("/category.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
