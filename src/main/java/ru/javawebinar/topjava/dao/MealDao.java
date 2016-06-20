package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

public interface MealDao {

    List<UserMeal> getAllMeals();

    UserMeal getMealById(int id);

    void deleteMeal(int id);

    void addMeal(UserMeal userMeal);

    void updateMeal(UserMeal userMeal);
}
