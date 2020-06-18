package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger();

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int authUserId) {
        logger.info("save {} by user with id:{}", meal, authUserId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(authUserId);
            return repository.put(meal.getId(), meal);
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            if (oldMeal.getUserId() == authUserId) {
                meal.setUserId(oldMeal.getUserId());
                return meal;
            }
            return oldMeal;
        });
    }

    @Override
    public boolean delete(int id, int authUserId) {
        logger.info("delete id:{}, by user with id:{}", id, authUserId);
        Meal meal = repository.get(id);
        return (meal != null && meal.getUserId() == authUserId) && repository.remove(id, meal);
    }

    @Override
    public Meal get(int id, int authUserId) {
        logger.info("get id:{}, by user with id:{}", id, authUserId);
        Meal meal = repository.get(id);
        return meal != null ? meal.getUserId() == authUserId ? meal : null : null;
    }

    @Override
    public List<Meal> getAll(int authUserId) {
        logger.info("getAll by user with id:{}", authUserId);
        return repository.values().stream().collect(Collectors.groupingBy(Meal::getUserId))
                .entrySet().stream()
                .filter(e -> e.getKey() == authUserId)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllByDate(LocalDate startDate, LocalDate endDate, int authUserId) {
        logger.info("getByDate startDate:{}, endDate:{}, with user id:{}", startDate, endDate, authUserId);
        return repository.values().stream()
                .collect(Collectors.groupingBy(Meal::getUserId))
                .entrySet().stream()
                .filter(e -> e.getKey() == authUserId)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .filter(meal -> isBetweenHalfOpen(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

