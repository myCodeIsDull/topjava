package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
            if (em.createQuery("update Meal m set m.dateTime=:dateTime, m.description=:description, m.calories=:calories where m.id=:id and m.user.id=:userId")
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
        int result;
        try {
            result = em.createQuery("delete from Meal m where m.id=:id and m.user.id=:userId")
                    .setParameter("id", id)
                    .setParameter("userId", userId).executeUpdate();
        } catch (NoResultException e) {
            return false;
        }
        return result != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal result;
        try {
            result = (Meal) em.createQuery("select m from Meal as m where m.id=:id and m.user.id=:userId")
                    .setParameter("id", id)
                    .setParameter("userId", userId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return result;
    }

    @Override
    public List<Meal> getAll(int userId) {
        TypedQuery<Meal> query = em.createQuery("select m from Meal as m where m.user.id =:userId order by m.dateTime desc", Meal.class)
                .setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        TypedQuery<Meal> query = em.createQuery("select m from Meal as m where m.user.id =:userId and (m.dateTime >= :startDateTime and m.dateTime < :endDateTime) order by m.dateTime desc ", Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime);
        return query.getResultList();
    }
}