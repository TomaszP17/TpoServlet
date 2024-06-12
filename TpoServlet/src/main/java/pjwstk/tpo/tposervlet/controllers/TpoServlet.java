package pjwstk.tpo.tposervlet.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "tpoServlet", value = "/tpoServlet")
public class TpoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getHeaderNames().asIterator().forEachRemaining(h -> {
            System.out.println(h);
            try {
                resp.getWriter().println(h + " : " + req.getHeader(h));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        resp.getWriter().println("Hello from TPO!");
        resp.addCookie(new Cookie("tpo", "Servlet"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("Hello from TPO!");
        resp.addCookie(new Cookie("tpo", "Servlet"));
    }
}
