package ru.javawebinar.topjava.web;

import org.springframework.context.annotation.Bean;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static int authUserId;

    public static int authUserId() {
        return authUserId;
    }

    public static void setAuthUserId(int id) {
        authUserId = id;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}