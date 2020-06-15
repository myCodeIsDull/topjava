package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger logger  = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int authUserId) {
        logger.info("save {} by user with id:{}",meal,authUserId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(authUserId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            if(oldMeal.getUserId()==authUserId) {
                meal.setUserId(oldMeal.getUserId());
                return meal;
            }
            return oldMeal;
        });
    }

    @Override
    public boolean delete(int id, int authUserId) {
        logger.info("delete id:{}, by user with id:{}",id,authUserId);
        Meal meal = repository.get(id);
        return (meal != null && meal.getUserId() == authUserId) && repository.remove(id, meal);
    }

    @Override
    public Meal get(int id, int authUserId) {
        logger.info("get id:{}, by user with id:{}",id,authUserId);
        return repository.computeIfPresent(id,(key,value)->value.getUserId()==authUserId? repository.get(id) : null);
    }

    @Override
    public Collection<Meal> getAll(int authUserId) {
        logger.info("getAll by user with id:{}",authUserId);
        return repository.values().stream()
                .filter(meal -> meal.getUserId()==authUserId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

