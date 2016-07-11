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

    }

    @Test
    public void getBetweenDateTimes() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void save() throws Exception {

    }

}