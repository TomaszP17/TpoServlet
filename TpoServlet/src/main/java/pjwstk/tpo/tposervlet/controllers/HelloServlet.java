package pjwstk.tpo.tposervlet.controllers;

import java.io.*;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pjwstk.tpo.tposervlet.models.Category;
import pjwstk.tpo.tposervlet.services.DbService;

@WebServlet(name = "helloServlet", value = "")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        DbService dbService = new DbService();
        List<Category> categories = dbService.getAllCategoriesNames();
        request.setAttribute("categories", categories);
        try {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}