package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {

    @Autowired
    private MealService service;

    @GetMapping("/meals")
    public String getMeals(Model model) {
        int id = SecurityUtil.authUserId();
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(id), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @DeleteMapping("/meals/{id}")
    public String delete(@PathVariable("id") int id) {
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @PostMapping("/meals")
    public String createOrUpdate(@RequestParam(name = "id", required = false) Integer id, @RequestParam(name = "action") String action, Model model) {
        switch (action) {
            case "create" -> model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
            case "update" -> {
                int userId = SecurityUtil.authUserId();
                model.addAttribute("meal", service.get(id, userId));
            }
        }
        return "mealForm";
    }

    @GetMapping("/meals/filter")
    public String filter(@RequestParam(name = "startDate", required = false) String sDate, @RequestParam(name = "endDate", required = false) String eDate,
                         @RequestParam(name = "startTime", required = false) String sTime, @RequestParam(name = "endTime", required = false) String eTime, Model model) {
        int userId = SecurityUtil.authUserId();
        LocalDate startDate = parseLocalDate(sDate);
        LocalDate endDate = parseLocalDate(eDate);
        LocalTime startTime = parseLocalTime(sTime);
        LocalTime endTime = parseLocalTime(eTime);
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        model.addAttribute("meals", MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }


}
