package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "delete from Meal m where m.id=:id and m.user.id=:userId"),
        @NamedQuery(name = Meal.GET_ALL, query = "select m from Meal as m where m.user.id =:userId order by m.dateTime desc"),
        @NamedQuery(name = Meal.FILTERED_BY_DATE, query = "select m from Meal as m where m.user.id =:userId and (m.dateTime >= :startDateTime and m.dateTime < :endDateTime) order by m.dateTime desc"),
        @NamedQuery(name = Meal.GET, query = "select m from Meal as m where m.id=:id and m.user.id=:userId"),
        @NamedQuery(name = Meal.UPDATE, query = "update Meal m set m.dateTime=:dateTime, m.description=:description, m.calories=:calories where m.id=:id and m.user.id=:userId")
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx"), @UniqueConstraint(columnNames = {"id"}, name = "meals_pkey")})
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String GET_ALL = "Meal.getAll";
    public static final String FILTERED_BY_DATE = "Meal.getByDate";
    public static final String GET = "Meal.get";
    public static final String UPDATE = "Meal.update";

    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @NotBlank
    @Size(min = 2, max = 120)
    @Column(nullable = false)
    private String description;

    @Min(value = 10)
    @Max(value = 5000)
    @Column(nullable = false)
    private int calories;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}

