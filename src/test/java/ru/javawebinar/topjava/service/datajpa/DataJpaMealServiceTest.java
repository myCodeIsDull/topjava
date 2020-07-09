package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = "datajpa")
public class DataJpaMealServiceTest extends MealServiceTest {
    @Test
    public void getWithUser() {
        Meal expected = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        USER_MATCHER.assertMatch(expected.getUser(), ADMIN);
        MEAL_MATCHER.assertMatch(ADMIN_MEAL1, expected);
    }
}
