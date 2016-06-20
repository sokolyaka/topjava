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
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.UserMealsUtil.getFilteredWithExceeded;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private MealDao dao = new MealDaoMemory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        List<UserMeal> mealList = dao.getAllMeals();

        List<UserMealWithExceed> filteredMealsWithExceeded =
                getFilteredWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000);

        req.setAttribute("mealList", filteredMealsWithExceeded);
        RequestDispatcher Dispatcher = getServletContext().getRequestDispatcher("/mealList.jsp");

        Dispatcher.forward(req, resp);
    }
}
