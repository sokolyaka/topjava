package ru.javawebinar.topjava.service.meal;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.DATA_JPA;

@ActiveProfiles(DATA_JPA)
public class DataJpaUserMealServiceTest extends AbstractUserMealServiceTest {
}
