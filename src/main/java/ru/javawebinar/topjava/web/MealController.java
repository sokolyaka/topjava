package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Controller
public class MealController {

    @Autowired
    private UserMealRestController mealController;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(HttpServletRequest request) {
        String action = request.getParameter("action");
        if (action == null) {
            return getAll(request);
        } else if (action.equals("delete")) {
            return delete(request);
        } else if (action.equals("create")) {
            return create(request);
        } else if (action.equals("update")) {
            return update(request);
        } else {
            return "index";
        }
    }

    private String getAll(HttpServletRequest request) {
        request.setAttribute("mealList", mealController.getAll());
        return "mealList";
    }

    @RequestMapping(value = "/meals?action=delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        int id = getId(request);
        mealController.delete(id);
        return "redirect:meals";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(HttpServletRequest request){
        final UserMeal meal = new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000);
        request.setAttribute("meal", meal);
        return "mealEdit";
}

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(HttpServletRequest request) {
        final UserMeal meal = mealController.get(getId(request));
        request.setAttribute("meal", meal);
        return "mealEdit";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String mealsPost(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            final UserMeal userMeal = new UserMeal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                mealController.create(userMeal);
            } else {
                mealController.update(userMeal, getId(request));
            }
            return "redirect:meals";

        } else if (action.equals("filter")) {
            return filter(request);
        } else {
            return "index";
        }
    }

    private String filter(HttpServletRequest request) {
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        request.setAttribute("mealList", mealController.getBetween(startDate, startTime, endDate, endTime));
        return "mealList";
    }

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }
}
