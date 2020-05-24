package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);


        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        NavigableSet<UserMealWithExcess> mealWithExcessOrderedSet = new TreeSet<>(Comparator.comparing(UserMealWithExcess::getDateTime));
        Map<LocalDate,Integer> dateToCaloriesMap = new HashMap<>();
        List<UserMealWithExcess> userMealWithExcessLinkedList = new LinkedList<>();
        for(UserMeal meal : meals) {
            mealWithExcessOrderedSet.add(new UserMealWithExcess(meal,true));
            dateToCaloriesMap.merge(meal.getDateTime().toLocalDate(),meal.getCalories(),Integer::sum);
        }
        for(Map.Entry<LocalDate,Integer> entry : dateToCaloriesMap.entrySet()) {
            if(entry.getValue() > caloriesPerDay) {
                UserMealWithExcess from = new UserMealWithExcess(LocalDateTime.of(entry.getKey(), startTime), "startTime", 0, true);
                UserMealWithExcess to = new UserMealWithExcess(LocalDateTime.of(entry.getKey(), endTime), "endTime", 0, true);
                userMealWithExcessLinkedList.addAll(mealWithExcessOrderedSet.subSet(from, true, to, false));
            }
        }
        return userMealWithExcessLinkedList;
    }


    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy((UserMeal meal) -> meal.getDateTime().toLocalDate()))
                .entrySet().stream()
                .flatMap(entry -> {
                    int totalCount = entry.getValue().stream().mapToInt(UserMeal::getCalories).sum();
                    if(totalCount > caloriesPerDay) {
                        return entry.getValue().stream()
                                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(),startTime,endTime))
                                .map(meal -> new UserMealWithExcess(meal.getDateTime(),meal.getDescription(),meal.getCalories(),true));
                    }
                    return Stream.empty();
                }).collect(Collectors.toList());
    }

}
