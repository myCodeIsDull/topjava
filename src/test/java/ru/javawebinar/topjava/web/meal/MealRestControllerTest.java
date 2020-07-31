package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestMatcher;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;
import java.util.stream.Stream;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class MealRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MealService mealService;

    private static final TestMatcher<MealTo> MEAL_TO_MATCHER = TestMatcher.usingFieldsComparator(MealTo.class, "");

    private static final String REST_URL = MealRestController.REST_URL;

    @Test
    void getAll() throws Exception {
        List<MealTo> expected = MealsUtil.getTos(MEALS, DEFAULT_CALORIES_PER_DAY);
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(expected));
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = MealTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Meal created = TestUtil.readFromJson(action, Meal.class);
        int id = created.id();
        newMeal.setId(id);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealService.get(id, SecurityUtil.authUserId()), newMeal);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(String.format("%s/%d", REST_URL, MEAL1_ID)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, SecurityUtil.authUserId()));
    }

    @Test
    void update() throws Exception {
        Meal meal = getUpdated();
        perform(MockMvcRequestBuilders.put(String.format("%s/%d", REST_URL, MEAL1_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Meal updated = mealService.get(MEAL1_ID, SecurityUtil.authUserId());
        MEAL_MATCHER.assertMatch(updated, meal);
    }

    @Test
    void getBetween() throws Exception {
        List<MealTo> expected = MealsUtil.getTos(List.of(MEAL3,MEAL2,MEAL1), DEFAULT_CALORIES_PER_DAY);
        perform(MockMvcRequestBuilders.get(String.format("%s/filter?startDate=2020-01-30&endDate=2020-01-30&startTime=&endTime=",REST_URL)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(expected));
    }


}
