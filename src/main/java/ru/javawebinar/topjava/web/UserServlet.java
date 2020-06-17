package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action!=null && action.equalsIgnoreCase("user")) {
            int userId = Integer.parseInt(request.getParameter("id"));
            setAuthUserId(userId);
            response.sendRedirect("meals");
            return;
        }
        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
    private void setAuthUserId(int id) {
        SecurityUtil.setAuthUserId(id);
    }
}
