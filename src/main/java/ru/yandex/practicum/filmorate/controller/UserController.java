package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/users")
    public User post(@Valid @RequestBody User model) throws ValidateException {
        userService.add(model);
        return model;
    }

    @PutMapping("/users")
    public User put(@Valid @RequestBody User model) throws ValidateException {
        return userService.update(model);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getModelById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Integer userId,
                          @PathVariable Integer friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") Integer userId,
                             @PathVariable Integer friendId) {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getListOfFriends(@PathVariable Integer id) {
        return userService.getListOfFriend(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getListOfCommonFriends(@PathVariable("id") Integer userId,
                                             @PathVariable Integer otherId) {
        return userService.getListOfCommonFriends(userId, otherId);
    }

}
