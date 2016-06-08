package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = new ArrayList<>();

        for (int year = 1; year < 2000; year++) {
            for (int month = 1; month < 13; month++) {
                for (int dayOfMonth = 1; dayOfMonth < 29; dayOfMonth++) {
                    for (int dayPart = 1; dayPart <= 3; dayPart++) {
                        int hour = new Double(24 / 3 * dayPart * Math.random()).intValue();
                        mealList.add(new UserMeal(LocalDateTime.of(year, month, dayOfMonth, hour, 0), "Ужин", 510));
                    }
                }
            }
        }

        long start = System.currentTimeMillis();
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println("getFilteredMealsWithExceeded " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        getFilteredMealsWithExceededBySteam(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println("getFilteredMealsWithExceededBySteam " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        getFilteredMealsWithExceededBySteamByGKislin(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println("getFilteredMealsWithExceededBySteamGKislin " + (System.currentTimeMillis() - start));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(
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

    public static List<UserMealWithExceed> getFilteredMealsWithExceededBySteam(
            List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> result = new ArrayList<>();

        mealList.stream()
                .collect(groupingBy(userMeal -> userMeal.getDateTime().toLocalDate()))
                .values()
                .stream()
                .map(userMeals -> {
                    boolean exceed = userMeals.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay;
                    return userMeals
                            .stream()
                            .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed));
                }).forEach(userMealWithExceedStream -> result.addAll(userMealWithExceedStream.collect(Collectors.toList())));

        return result;
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceededBySteamByGKislin(
            List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesByDate =
                mealList
                        .stream()
                        .collect(groupingBy(um -> um.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList
                .stream()
                .map(um -> new UserMealWithExceed(
                        um.getDateTime(),
                        um.getDescription(),
                        um.getCalories(),
                        caloriesByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
