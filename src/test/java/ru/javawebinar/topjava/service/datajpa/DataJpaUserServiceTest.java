package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = "datajpa")
public class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void getUserWithMeal() {
        User user = service.getWithMeals(USER_ID);
        MEAL_MATCHER.assertMatch(user.getMeals(), MEALS);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    public void assertUserMealsIsEmpty() {
        User newUser = getNew();
        service.create(newUser);
        int id = newUser.id();
        User fetched = service.getWithMeals(id);
        MEAL_MATCHER.assertMatch(fetched.getMeals(), Collections.emptyList());
    }
}
