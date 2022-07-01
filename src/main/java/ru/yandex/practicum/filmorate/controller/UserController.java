package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserIdValidator;
import ru.yandex.practicum.filmorate.validator.UserNameValidation;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends Controller<User> {

    protected static final List<Validator> validators = List.of(new UserNameValidation()
            , new UserIdValidator());

    public UserController() {
        super.setValidators(validators);
    }

}
