package ru.javawebinar.topjava.service.user;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.user.UserServiceTest;

@ActiveProfiles(profiles = "datajpa")
public class DataJpaUserServiceTest extends UserServiceTest {
}
