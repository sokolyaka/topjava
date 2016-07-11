package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
public class UserMealServiceTest {


    @Autowired
    private UserMealService userMealService;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        UserMeal userMeal = userMealService.get(BREAKFAST_ID, USER_ID);
        MATCHER.assertEquals(BREAKFAST, userMeal);
    }

    @Test(expected = NotFoundException.class)
    public void getWithWrongUserId() throws Exception {
        UserMeal userMeal = userMealService.get(BREAKFAST_ID, ADMIN_ID);
        MATCHER.assertEquals(BREAKFAST, userMeal);
    }

    @Test()
    public void delete() throws Exception {
        userMealService.delete(BREAKFAST_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWithWrongUserId() throws Exception {
        userMealService.delete(BREAKFAST_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        Collection<UserMeal> betweenDates =
                userMealService
                        .getBetweenDates(BREAKFAST.getDateTime().toLocalDate(), BREAKFAST.getDateTime().toLocalDate(), USER_ID);

        assertTrue(betweenDates.size() == 3);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<UserMeal> betweenDates =
                userMealService
                        .getBetweenDateTimes(BREAKFAST.getDateTime(), DINNER.getDateTime(), USER_ID);

        assertTrue(betweenDates.size() == 3);
    }

    @Test
    public void getAll() throws Exception {
        Collection<UserMeal> all = userMealService.getAll(USER_ID);
        assertTrue(all.size() == 4);
    }

    @Test
    public void update() throws Exception {
        LocalDateTime newDateTime = LocalDateTime.now();
        BREAKFAST.setDateTime(newDateTime);
        UserMeal update = userMealService.update(BREAKFAST, USER_ID);
        assertNotNull(update);
    }

    @Test(expected = NotFoundException.class)
    public void updateWithWrongUserId() throws Exception {
        LocalDateTime newDateTime = LocalDateTime.now();
        BREAKFAST.setDateTime(newDateTime);
        userMealService.update(BREAKFAST, ADMIN_ID);
    }

    @Test
    public void save() throws Exception {
        UserMeal newMeal = new UserMeal(LocalDateTime.now(), "new meal", 1000);
        UserMeal save = userMealService.save(newMeal, USER_ID);
        assertNotNull(save);
    }

}