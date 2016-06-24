package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private UserMealRestController userMealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            userMealRestController = appCtx.getBean(UserMealRestController.class);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if ("sort".equalsIgnoreCase(action)) {
            doFilter(req, res);
        } else {
            String id = req.getParameter("id");
            UserMeal userMeal =
                    new UserMeal(
                            id.isEmpty() ? null : Integer.valueOf(id),
                            1,
                            LocalDateTime.parse(req.getParameter("dateTime")),
                            req.getParameter("description"),
                            Integer.valueOf(req.getParameter("calories")));

            LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
            userMealRestController.update(userMeal);
            res.sendRedirect("meals");
        }
    }

    private void doFilter(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        LocalDate datefrom = null;
        try {
            datefrom = LocalDate.parse(req.getParameter("datefrom"));
        } catch (Exception e) {
            //NOP
        }

        LocalDate dateto = null;
        try {
            dateto = LocalDate.parse(req.getParameter("dateto"));
        } catch (Exception e) {
            //NOP
        }

        LocalTime timefrom = null;
        try {
            timefrom = LocalTime.parse(req.getParameter("timefrom"));
        } catch (Exception e) {
            timefrom = LocalTime.MIN;
        }
        LocalTime timeto = null;
        try {
            timeto = LocalTime.parse(req.getParameter("timeto"));
        } catch (Exception e) {
            timefrom = LocalTime.MAX;
        }

        req.setAttribute("mealList", userMealRestController.getAll(datefrom, dateto, timefrom, timeto));
        req.getRequestDispatcher("/mealList.jsp").forward(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            getAll(request, response);
        } else if (action.equals("delete")) {
            delete(request, response);
        } else if (action.equals("create") || action.equals("update")) {
            createUpdate(request, response, action);
        }
    }

    private void createUpdate(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        final UserMeal meal = action.equals("create") ?
                new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000, 1) :
                userMealRestController.get(getId(request));
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getId(request);
        LOG.info("Delete {}", id);
        UserMeal userMeal = userMealRestController.get(id);
        userMealRestController.delete(userMeal);
        response.sendRedirect("meals");
    }

    private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("getAll");
        request.setAttribute("mealList", userMealRestController.getAll());
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
