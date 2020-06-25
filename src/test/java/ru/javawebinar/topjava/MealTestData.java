package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;


public class MealTestData {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final int NOT_FOUND = 10;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ID = 100_003;
    public static final Meal MEAL = new Meal(100_003, LocalDateTime.parse("2020-01-31 08:15:00", formatter), "Обед", 500);

    public static final List<Meal> USER_MEALS = Arrays.asList(new Meal(LocalDateTime.parse("2020-01-31 14:45:00", formatter), "Ужин", 800),
            new Meal(LocalDateTime.parse("2020-01-31 08:15:00", formatter), "Обед", 500),
            new Meal(LocalDateTime.parse("2020-01-31 00:00:00", formatter), "Еда на граничное значение", 50),
            new Meal(LocalDateTime.parse("2020-01-30 20:35:00", formatter), "Ужин", 1000),
            new Meal(LocalDateTime.parse("2020-01-30 13:00:00", formatter), "Обед", 800),
            new Meal(LocalDateTime.parse("2020-01-30 09:20:00", formatter), "Завтрак", 200));

    public static final List<Meal> FILTERED_MEALS = Arrays.asList(new Meal(LocalDateTime.parse("2020-01-31 14:45:00", formatter), "Ужин", 800),
            new Meal(LocalDateTime.parse("2020-01-31 08:15:00", formatter), "Обед", 500),
            new Meal(LocalDateTime.parse("2020-01-31 00:00:00", formatter), "Еда на граничное значение", 50));

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(ZoneId.systemDefault()), "test", 550);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL_ID, LocalDateTime.now(ZoneId.systemDefault()), "Updated", 830);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("id").isEqualTo(expected);
    }
}
