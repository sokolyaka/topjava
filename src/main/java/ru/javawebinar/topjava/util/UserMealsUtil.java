package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(
            List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, List<UserMeal>> dateUserMealMap = new TreeMap<>();

        for (UserMeal userMeal : mealList) {

            LocalDate key = userMeal.getDateTime().toLocalDate();
            List<UserMeal> userMeals = dateUserMealMap.get(key);
            if (userMeals == null) {
                userMeals = new ArrayList<>();
                dateUserMealMap.put(key, userMeals);
            }

            userMeals.add(userMeal);
        }

        List<UserMealWithExceed> result = new ArrayList<>();

        for (List<UserMeal> userMeals : dateUserMealMap.values()) {
            int totalCalories = 0;
            for (UserMeal userMeal : userMeals) {
                totalCalories += userMeal.getCalories();
            }

            boolean exceed = false;
            if (totalCalories > caloriesPerDay) {
                exceed = true;
            }

            for (UserMeal userMeal : userMeals) {
                if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                    result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed));
                }
            }
        }

        return result;
    }
}
