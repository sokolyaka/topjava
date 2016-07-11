package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final int BREAKFAST_ID = START_SEQ+2;
    public static final int LUNCH_ID = START_SEQ + 3;
    public static final int DINNER_ID = START_SEQ + 4;
    public static final int AT_NIGHT_ID = START_SEQ + 5;

    public static final UserMeal BREAKFAST = new UserMeal(BREAKFAST_ID, LocalDateTime.of(1999, 1, 8, 10, 23), "breakfast", 500);
    public static final UserMeal LUNCH = new UserMeal(LUNCH_ID, LocalDateTime.of(1999, 1, 8, 14, 25), "lunch", 500);
    public static final UserMeal DINNER = new UserMeal(DINNER_ID, LocalDateTime.of(1999, 1, 8, 18, 26), "dinner", 500);
    public static final UserMeal AT_NIGHT = new UserMeal(AT_NIGHT_ID, LocalDateTime.of(1999, 1, 9, 1, 4), "at night", 600);

}
