package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {

    void update(UserMeal UserMeal);

    void delete(UserMeal UserMeal);

    UserMeal create(UserMeal UserMeal);

    UserMeal get(Integer id);

    List<UserMeal> getAll();
}
