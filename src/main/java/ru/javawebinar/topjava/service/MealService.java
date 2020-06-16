package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<MealTo> getAll(int authUserId, int caloriesPerDay) {
        return MealsUtil.getTos(repository.getAll(authUserId),caloriesPerDay);
    }

    public List<MealTo> getAllByDate(int authUserId, int caloriesPerDay, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.getFilteredTos(repository.getAll(authUserId),caloriesPerDay,startDate,endDate,startTime,endTime);
    }

    public Meal get(int id, int authUserId) {
        return checkNotFoundWithId(repository.get(id,authUserId),id);
    }

    public void delete(int id, int authUserId) {
        checkNotFoundWithId(repository.delete(id,authUserId),id);
    }

    public Meal create(Meal meal, int authUserId) {
        return repository.save(meal,authUserId);
    }

    public void update(Meal meal, int id, int authUserId) {
        checkNotFoundWithId(repository.save(meal,authUserId),id);
    }
}