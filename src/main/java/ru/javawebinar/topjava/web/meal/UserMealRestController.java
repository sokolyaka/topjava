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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UnknownFormatFlagsException;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.LoggedUser.id;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    public static final int NULL = 0;
    public static final int TIME = 1;
    public static final int FROM = 2;
    public static final int TO = 3;
    public static final int FULL = 5;
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
                        .getWithExceeded(all, caloriesPerDay);
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

    public List<UserMealWithExceed> getAll(LocalDate datefrom, LocalDate dateto, LocalTime timefrom, LocalTime timeto) {
        int filter = generateFilter(datefrom, dateto, timefrom, timeto);
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
        switch (filter) {
            case NULL:
                return getAll();
            case TIME:
                return UserMealsUtil.getFilteredWithExceeded(all, timefrom, timeto, caloriesPerDay);
            case FROM:
                return UserMealsUtil.getFilteredByDateWithExceeded(all, datefrom, LocalDate.MAX, caloriesPerDay);
            case TO:
                return UserMealsUtil.getFilteredByDateWithExceeded(all, LocalDate.MIN, dateto, caloriesPerDay);
            case FULL:
                return UserMealsUtil.getFilteredByDateTimeWithExceeded(all, LocalDateTime.of(datefrom, timefrom), LocalDateTime.of(dateto, timeto), caloriesPerDay);
            default:
                throw new IllegalArgumentException();
        }
    }

    private int generateFilter(LocalDate datefrom, LocalDate dateto, LocalTime timefrom, LocalTime timeto) {
        if (datefrom == null && dateto == null && timefrom == null && timeto == null) {
            return NULL;
        }

        if (datefrom == null && dateto == null) {
            return TIME;
        }

        if (datefrom != null && dateto == null) {
            return FROM;
        }

        if (datefrom == null && dateto != null) {
            return TO;
        }

        if (datefrom != null && dateto != null && timefrom != null && timeto != null) {
            return FULL;
        }

        throw new UnknownFormatFlagsException("unknown condition");
    }
}
