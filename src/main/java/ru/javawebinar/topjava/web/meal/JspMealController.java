package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String getMeals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @DeleteMapping("/{id}")
    public String deleteMeal(@PathVariable("id") int id) {
        delete(id);
        return "redirect:/meals";
    }

    @PostMapping
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

    @PostMapping("/update")
    public String createMeal(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        Meal meal = new Meal(LocalDateTime.parse(params.get("dateTime")[0]), params.get("description")[0], Integer.parseInt(params.get("calories")[0]));
        String[] id = params.get("id");
        switch (id[0]) {
            case "" -> create(meal);
            default -> update(meal, Integer.parseInt(id[0]));
        }
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(name = "startDate", required = false) String sDate, @RequestParam(name = "endDate", required = false) String eDate,
                         @RequestParam(name = "startTime", required = false) String sTime, @RequestParam(name = "endTime", required = false) String eTime, Model model) {
        LocalDate startDate = parseLocalDate(sDate);
        LocalDate endDate = parseLocalDate(eDate);
        LocalTime startTime = parseLocalTime(sTime);
        LocalTime endTime = parseLocalTime(eTime);
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }


}
