package ru.javawebinar.topjava.service.meal;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = "datajpa")
public class DataJpaMealServiceTest extends MealServiceTest {
    @Test
    public void getWithUser() {
        USER_MATCHER.assertMatch(service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID).getUser(), ADMIN);
    }
}
