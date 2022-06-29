package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserIdValidator;
import ru.yandex.practicum.filmorate.validator.UserNameValidation;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    HashMap<Integer, User> users = new HashMap<>();
    private static final List<UserValidator> validators = List.of(new UserNameValidation()
            , new UserIdValidator());
    private int id = 0;

    @GetMapping
    public Collection<User> getAll() {
        return users.values();
    }

    @PostMapping
    public User post(@Valid @RequestBody User user) throws ValidateException {

        checkValidation(user);

        user.setId(++id);
        users.put(id, user);
        log.debug("Создан пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) throws ValidateException {

        checkValidation(user);

        final int id = user.getId();
        if (users.containsKey(id)) {
            users.replace(id, user);
            log.debug("Обновлен пользователь: {}", user);
        } else {
            users.put(id, user);
            log.debug("Создан пользователь: {}", user);
        }
        return user;
    }

    protected void checkValidation(User user) throws ValidateException {
        for (UserValidator validator : validators) {
            validator.validate(user);
        }
    }
}