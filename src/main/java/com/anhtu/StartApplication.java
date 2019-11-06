package com.anhtu;

import com.anhtu.dao.UserDAO;
import com.anhtu.dao.UserDAOImpl;
import com.anhtu.entity.PROJECTOR;
import com.anhtu.entity.User;
import com.anhtu.repository.UserRepository;
import com.anhtu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StartApplication extends SpringBootServletInitializer {
    private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return (args) -> {
            log.info("StartApplication...");
            UserService userService = context.getBean(UserService.class);
            UserDAO userDAO = context.getBean(UserDAO.class);
            UserDAO userDAOImpl = context.getBean(UserDAOImpl.class);
            if (userDAO == userDAOImpl)
                System.out.println("userDAO == userDAOImpl");

            userRepository.save(new User("jsmith", "John2", "Smith", "qwerty"));
            userRepository.save(new User("jdoe", "Jane", "Doe", "mySecret"));

        };
    };

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(StartApplication.class);
    }
}

