package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemory;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.UserMealsUtil.getFilteredWithExceeded;

public class CRUDController extends HttpServlet {
    private static final Logger LOG = getLogger(CRUDController.class);

    private MealDao dao = new MealDaoMemory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action").equalsIgnoreCase("list")) {
            List<UserMeal> mealList = dao.getAllMeals();
            List<UserMealWithExceed> filteredMealsWithExceeded =
                    getFilteredWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000);
            req.setAttribute("mealList", filteredMealsWithExceeded);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mealList.jsp");
            dispatcher.forward(req, resp);
        } else if (req.getParameter("action").equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.deleteMeal(id);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/controller?action=list");
            dispatcher.forward(req, resp);
        } else if (req.getParameter("action").equalsIgnoreCase("edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            UserMeal mealById = dao.getMealById(id);
            req.setAttribute("meal", mealById);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/edit.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        LocalDateTime date = null;
        String description = null;
        int calories = 0;
        try {
            date = LocalDateTime.parse(req.getParameter("date"));
            description = req.getParameter("description");
            calories = Integer.parseInt(req.getParameter("calories"));
        } catch (Exception e) {
            LOG.debug("incorrect data ", e);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/controller?action=edit&id=" + id);
            dispatcher.forward(req, resp);
            return;
        }

        dao.updateMeal(new UserMeal(id, date, description, calories));

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/controller?action=list");
        dispatcher.forward(req, resp);

    }
}
