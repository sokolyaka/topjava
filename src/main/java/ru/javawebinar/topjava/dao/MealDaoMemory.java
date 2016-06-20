package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

import static ru.javawebinar.topjava.dao.DataMock.mealList;

public class MealDaoMemory implements MealDao {
    private static final Object synObj = new Object();

    @Override
    public List<UserMeal> getAllMeals() {
        return mealList;
    }

    @Override
    public UserMeal getMealById(int id) {
        synchronized (synObj) {
            for (UserMeal userMeal : mealList) {
                if (userMeal.getId() == id) {
                    return userMeal;
                }
            }
            return null;
        }
    }

    @Override
    public void deleteMeal(int id) {
        synchronized (synObj) {
            for (UserMeal userMeal : mealList) {
                if (userMeal.getId() == id) {
                    mealList.remove(userMeal);
                    break;
                }
            }
        }
    }

    @Override
    public void addMeal(UserMeal userMeal) {
        synchronized (synObj) {
            mealList.add(userMeal);
        }
    }

    @Override
    public void updateMeal(UserMeal userMeal) {
        synchronized (synObj) {
            for (UserMeal meal : mealList) {
                if (meal.getId() == userMeal.getId()) {
                    mealList.remove(meal);
                    break;
                }
            }
            mealList.add(userMeal);
        }
    }
}
