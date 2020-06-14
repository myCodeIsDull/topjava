package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            //System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            adminUserController.getAll().forEach(System.out::println);
            adminUserController.delete(2);
            adminUserController.getAll().forEach(System.out::println);
            adminUserController.delete(20);
            adminUserController.create(new User(null,"Jenya","jenya23@users.com","qwerty", Role.USER));
            adminUserController.getAll().forEach(System.out::println);
            System.out.println(adminUserController.getByMail("misha@users.com"));
            System.out.println(adminUserController.getByMail("misha@users1.com"));

        }
    }
}
