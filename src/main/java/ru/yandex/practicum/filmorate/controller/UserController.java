package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserIdValidator;
import ru.yandex.practicum.filmorate.validator.UserNameValidation;
import ru.yandex.practicum.filmorate.validator.Validator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends Controller {

    protected static final List<Validator> validators = List.of(new UserNameValidation()
            , new UserIdValidator());

    public UserController() {
        super.setValidators(validators);
    }

    @GetMapping
    public Collection<User> getAllUsers() {

        Collection<Model> models = super.getAll();
        HashSet<User> users = new HashSet<>();
        for (Model model : models) {
            users.add((User) model);
        }
        return users;
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) throws ValidateException {
        super.post(user);
        log.debug("Создан пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) throws ValidateException {
        super.put(user);
        log.debug("Обновлен пользователь: {}", user);
        return user;
    }

}
