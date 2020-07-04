package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User user = em.getReference(User.class, userId);
            meal.setUser(user);
            em.persist(meal);
        } else {
            if (em.createNamedQuery(Meal.UPDATE)
                    .setParameter("dateTime", meal.getDateTime())
                    .setParameter("description", meal.getDescription())
                    .setParameter("calories", meal.getCalories())
                    .setParameter("id", meal.getId())
                    .setParameter("userId", userId)
                    .executeUpdate() == 0) {
                return null;
            }
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId).executeUpdate() !=0;
    }

    @Override
    public Meal get(int id, int userId) {
        TypedQuery<Meal> query = em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("id", id)
                .setParameter("userId", userId);

        return DataAccessUtils.singleResult(query.getResultList());
    }

    @Override
    public List<Meal> getAll(int userId) {
        TypedQuery<Meal> query = em.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        TypedQuery<Meal> query = em.createNamedQuery(Meal.FILTERED_BY_DATE, Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime);
        return query.getResultList();
    }
}