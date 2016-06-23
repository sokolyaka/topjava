package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.LoggedUser.id;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(this::save);
    }

    @Override
    public UserMeal save(UserMeal userMeal) {

        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public void delete(int id) {
        if (id() == repository.get(id).getUserId()) {
            repository.remove(id);
        }
    }

    @Override
    public UserMeal get(int id) {
        UserMeal userMeal = repository.get(id);
        if (id() == userMeal.getUserId()) {
            return userMeal;
        }

        throw new NotFoundException();
    }

    @Override
    public List<UserMeal> getAll() {
        return
                repository
                        .values()
                        .stream()
                        .filter(userMeal -> id() == userMeal.getUserId())
                        .sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()))
                        .collect(Collectors.toList());
    }
}

