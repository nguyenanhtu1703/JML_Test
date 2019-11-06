package com.anhtu.restcontroller;

import com.anhtu.entity.User;
import com.anhtu.error.UserExistedException;
import com.anhtu.error.UserNotFoundException;
import com.anhtu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/${secret.key}")
    User newBook(@Valid @RequestBody User newUser) {
        if (userRepository.findByLogin(newUser.getLogin()) != null) {
            throw new UserExistedException(newUser.getLogin());
        }

        return userRepository.save(newUser);
    }

    @PatchMapping("/users/${secret.key}/{login}")
    User patch(@RequestBody User user, @PathVariable String login) {
        userRepository.findById(login).map(x -> {
            if (user.getName() != null)
                x.setName(user.getName());
            if (user.getPassword() != null) {
                System.out.println("NOT NULLL");
                x.setPassword(user.getPassword());
            } else
                System.out.println("NULLL");
            if (user.getSurname() != null)
                x.setSurname(user.getSurname());

            return userRepository.save(x);
        }).orElseGet(() -> {
            throw new UserNotFoundException(login);
        });

        return user;
    }

    @DeleteMapping("/user/${secret.key}/{login}")
    void deleteUser(@PathVariable String login) {
        if (userRepository.findById(login) != null)
            userRepository.deleteById(login);
        else
            throw new UserNotFoundException(login);
    }

    @GetMapping("/users")
    List<User> findAll() {
        return userRepository.findAll();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExistedException.class)
    public Map<String, String> handleValidationExceptions(UserExistedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());

        System.out.println("User Existed!");
        return errors;
    }
}
