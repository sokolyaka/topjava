package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.LoggedUser.id;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    @Autowired
    private UserMealService service;

    @Autowired
    private UserService userService;

    public List<UserMealWithExceed> getAll() {
        List<UserMeal> all = service.getAll();

        all = all
                .stream()
                .filter(userMeal -> id() == userMeal.getUserId())
                .sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());

        if (all.isEmpty()) {
            throw new NotFoundException();
        }

        int caloriesPerDay = userService.get(LoggedUser.id()).getCaloriesPerDay();

        return
                UserMealsUtil
                        .getFilteredWithExceeded(all, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public UserMeal get(Integer id) {
        UserMeal userMeal = service.get(id);
        if (id() != userMeal.getUserId()) {
            throw new NotFoundException();
        }

        return userMeal;
    }

    public UserMeal create(UserMeal userMeal) {
        if (id() != userMeal.getUserId()) {
            throw new NotFoundException();
        }

        return service.create(userMeal);
    }

    public void delete(UserMeal userMeal) {
        if (id() != userMeal.getUserId()) {
            throw new NotFoundException();
        }

        service.delete(userMeal);
    }

    public void update(UserMeal userMeal) {
        if (id() != userMeal.getUserId()) {
            throw new NotFoundException();
        }

        service.update(userMeal);
    }
}
