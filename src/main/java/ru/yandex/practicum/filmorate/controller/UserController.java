package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validator.UserIdValidator;
import ru.yandex.practicum.filmorate.validator.UserNameValidation;
import ru.yandex.practicum.filmorate.validator.Validator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private static final List<Validator<User>> validators = List.of(new UserNameValidation()
            , new UserIdValidator());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getAll() {
        log.info("GET all users");
        return userService.getAll();
    }

    @PostMapping
    public User post(@Valid @RequestBody User model) throws ValidateException {
        log.info("POST user {}", model);
        checkValidation(model);
        userService.add(model);
        return model;
    }

    @PutMapping
    public User put(@Valid @RequestBody User model) throws ValidateException {
        log.info("PUT user {}", model);
        checkValidation(model);
        return userService.update(model);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        log.info("GET user by id={}", id);
        return userService.getModelById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Long userId,
                          @PathVariable Long friendId) {
        log.info("PUT user id={} has added user id={} to friends", userId, friendId);
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") Long userId,
                             @PathVariable Long friendId) {
        log.info("DELETE user id={} has deleted user id={} from friends", userId, friendId);
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getListOfFriends(@PathVariable Long id) {
        log.info("GET list of friends of user id={}", id);
        return userService.getListOfFriend(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getListOfCommonFriends(@PathVariable("id") Long userId,
                                             @PathVariable Long otherId) {
        log.info("GET list of common friends of user id={} and user id={}", userId, otherId);
        return userService.getListOfCommonFriends(userId, otherId);
    }

    public void checkValidation(User model) throws ValidateException {
        for (Validator<User> validator : validators) {
            validator.validate(model);
        }
    }
}
