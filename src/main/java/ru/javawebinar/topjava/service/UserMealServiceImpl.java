package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void update(UserMeal userMeal) {
        mealRepository.save(userMeal);
    }

    @Override
    public void delete(UserMeal userMeal) {
        mealRepository.delete(userMeal.getId());
    }

    @Override
    public UserMeal create(UserMeal userMeal) {
        return mealRepository.save(userMeal);
    }

    @Override
    public UserMeal get(Integer id) {
        return mealRepository.get(id);
    }

    @Override
    public List<UserMeal> getAll() {
        return mealRepository.getAll();
    }
}
