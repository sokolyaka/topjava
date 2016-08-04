package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            userMeal.setUser(em.getReference(User.class, userId));
            em.persist(userMeal);
            return userMeal;
        } else {
            if (userMeal.getUser().getId() == userId) {
                return em.merge(userMeal);
            } else {
                return null;
            }
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        UserMeal reference = em.getReference(UserMeal.class, id);
        if (reference.getUser().getId() == userId) {
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
        return em
                .createNamedQuery(UserMeal.GET_ALL, UserMeal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em
                .createNamedQuery(UserMeal.GET_BETWEEN, UserMeal.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("userId", userId)
                .getResultList();
    }
}