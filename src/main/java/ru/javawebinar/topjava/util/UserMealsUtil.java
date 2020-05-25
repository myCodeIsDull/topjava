package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)

        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);


        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(12, 0), 2000));
    }

    public static UserMealWithExcess getUserMealWithExcessDTO(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(),userMeal.getCalories(), excess);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateToCaloriesMap = new HashMap<>();
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        for(UserMeal meal : meals) {
            dateToCaloriesMap.merge(meal.getDateTime().toLocalDate(),meal.getCalories(),Integer::sum);
            if(TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(),startTime,endTime)) {
                userMealWithExcessList.add(getUserMealWithExcessDTO(meal,false));
            }
            if(dateToCaloriesMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay) {
                for(UserMealWithExcess userMealWithExcess : userMealWithExcessList) {
                    if(userMealWithExcess.getDateTime().toLocalDate().equals(meal.getDateTime().toLocalDate())) {
                        userMealWithExcess.setExcess(true);
                    }
                }
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate()))
                .values().stream()
                .flatMap(list -> {
                    int totalCount = list.stream().mapToInt(UserMeal::getCalories).sum();
                    if(totalCount > caloriesPerDay) {
                        return list.stream()
                                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(),startTime,endTime))
                                .map(meal -> getUserMealWithExcessDTO(meal,true));
                    }
                    return list.stream()
                            .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(),startTime,endTime))
                            .map(meal -> getUserMealWithExcessDTO(meal,false));
                }).collect(Collectors.toList());
    }
}
