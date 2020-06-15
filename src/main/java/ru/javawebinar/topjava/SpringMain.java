package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;

import java.time.LocalDateTime;
import java.time.Month;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            //System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            //AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
//            adminUserController.getAll().forEach(System.out::println);
//            adminUserController.delete(2);
//            adminUserController.getAll().forEach(System.out::println);
//            adminUserController.delete(20);
//            adminUserController.create(new User(null,"Jenya","jenya23@users.com","qwerty", Role.USER));
//            adminUserController.getAll().forEach(System.out::println);
//            System.out.println(adminUserController.getByMail("misha@users.com"));
//            System.out.println(adminUserController.getByMail("misha@users1.com"));
            InMemoryMealRepository repository = appCtx.getBean(InMemoryMealRepository.class);
            System.out.println(repository.getAll(1));
            repository.save(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 20, 0), "Ужин", 410),1);
            System.out.println(repository.getAll(1));
            repository.delete(8, 2);
            System.out.println(repository.getAll(1));
            System.out.println(repository.save(new Meal(8,LocalDateTime.of(2020, Month.DECEMBER, 10, 20, 0), "Выпил немношко", 410),1));
            System.out.println(repository.getAll(1));
            System.out.println(repository.save(new Meal(9,LocalDateTime.of(2020, Month.AUGUST, 10, 20, 0), "test", 1000),2));




        }
    }
}
