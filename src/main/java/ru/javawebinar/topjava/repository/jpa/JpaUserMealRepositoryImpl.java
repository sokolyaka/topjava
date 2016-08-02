package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            userMeal.setUser(em.getReference(User.class, userId));
            em.persist(userMeal);
            return userMeal;
        } else {
            return em.merge(userMeal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        UserMeal reference = em.getReference(UserMeal.class, id);
        if (reference.getUser().getId() == id) {
            em.remove(reference);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserMeal get(int id, int userId) {
        UserMeal userMeal = em.find(UserMeal.class, id);
        if (userMeal.getUser().getId() == userId) {
            return userMeal;
        } else {
            return null;
        }
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return null;
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return null;
    }
}