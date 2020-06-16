package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    private static final Logger logger  = LoggerFactory.getLogger(MealRestController.class);


    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        logger.info("getAll");
        return service.getAll(authUserId(),authUserCaloriesPerDay());
    }

    public List<MealTo> getAllByDate(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        logger.info("getAllByDate");
        return service.getAllByDate(authUserId(),authUserCaloriesPerDay(),
                startDate==null?startDate=LocalDate.MIN : startDate,endDate==null? endDate = LocalDate.MAX : endDate.plusDays(1),
                startTime==null? startTime = LocalTime.MIN : startTime, endTime==null? endTime = LocalTime.MAX : endTime);
    }

    public Meal get(int id) {
        logger.info("get {}",id);
        return service.get(id,authUserId());
    }

    public Meal create(Meal meal) {
        logger.info("create {}",meal);
        checkNew(meal);
        return service.create(meal,authUserId());
    }

    public void delete(int id) {
        logger.info("delete {}",id);
        service.delete(id,authUserId());
    }

    public void update(Meal meal, int id) {
        logger.info("update {} with user id {}",meal, authUserId());
        assureIdConsistent(meal,id);
        service.update(meal,id,authUserId());
    }






}