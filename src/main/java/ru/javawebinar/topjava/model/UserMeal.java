package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMeal {

    protected final int id;

    protected final LocalDateTime dateTime;

    protected final String description;

    protected final int calories;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        id = UserMealsUtil.getNewId();
    }

    public UserMeal(int id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public int getId() {
        return id;
    }
}
