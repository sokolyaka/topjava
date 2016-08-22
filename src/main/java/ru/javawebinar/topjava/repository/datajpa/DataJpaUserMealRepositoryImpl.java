package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository {

    @Autowired
    private ProxyUserMealRepository proxyUserMealRepository;

    @Autowired
    private ProxyUserRepository proxyUserRepository;

    @Transactional
    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (!userMeal.isNew() && get(userMeal.getId(), userId) == null) {
            return null;
        }

        userMeal.setUser(proxyUserRepository.getOne(userId));

        return proxyUserMealRepository.save(userMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxyUserMealRepository.delete(id,userId) !=0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return proxyUserMealRepository.get(id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxyUserMealRepository.findAll(userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxyUserMealRepository.getBetween(startDate, endDate, userId);

    }
}
