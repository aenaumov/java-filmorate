package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    /*
     * Получение всех user
     */
    @GetMapping
    public Collection<User> getAll() {
        log.info("GET all users");
        return userService.getAll();
    }

    /*
     * Добавление нового user
     */
    @PostMapping
    public User post(@Valid @RequestBody User model) {
        log.info("POST user {}", model);
        checkValidation(model);
        userService.add(model);
        return model;
    }

    /*
     * Обновление user
     */
    @PutMapping
    public User put(@Valid @RequestBody User model) {
        log.info("PUT user {}", model);
        checkValidation(model);
        return userService.update(model);
    }

    /*
     * Получение user по id
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        log.info("GET user by id={}", id);
        return userService.getModelById(id);
    }

    /*
     * Добавление user в друзья
     */
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Long userId,
                          @PathVariable Long friendId) {
        log.info("PUT user id={} has added user id={} to friends", userId, friendId);
        userService.addFriend(userId, friendId);
    }

    /*
     * Удаление user из друзей
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") Long userId,
                             @PathVariable Long friendId) {
        log.info("DELETE user id={} has deleted user id={} from friends", userId, friendId);
        userService.deleteFriend(userId, friendId);
    }

    /*
     * Получение списка друзей user
     */
    @GetMapping("/{id}/friends")
    public List<User> getListOfFriends(@PathVariable Long id) {
        log.info("GET list of friends of user id={}", id);
        return userService.getListOfFriend(id);
    }

    /*
     * Получение общего списка друзей user
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getListOfCommonFriends(@PathVariable("id") Long userId,
                                             @PathVariable Long otherId) {
        log.info("GET list of common friends of user id={} and user id={}", userId, otherId);
        return userService.getListOfCommonFriends(userId, otherId);
    }

    /*
     * Проверка валидации user
     */
    public void checkValidation(User model) {
        for (Validator<User> validator : validators) {
            validator.validate(model);
        }
    }
}
