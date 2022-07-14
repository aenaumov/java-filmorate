package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ModelNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.ModelStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    UserController controller;
    ModelStorage<User> storage;
    UserService service;

    @BeforeEach
    void init() {
        storage = new InMemoryUserStorage();
        service = new UserService(storage);
        controller = new UserController(service);
    }

    @Test
    void notExisted_Id_Test() {
        User user = new User(1L, "email@yandex.ru", "Alex", "test"
                , LocalDate.of(1900, 10, 20));
        ModelNotFoundException e = assertThrows(ModelNotFoundException.class, ()->controller.put(user));
        assertEquals("Объект с id 1 не найден", e.getMessage());
    }

    @Test
    void id_negative_Test() {

        User user = new User(0L, "email@yandex.ru", "Alex", "test"
                , LocalDate.of(1900, 10, 20));
        user.setId(-1L);
        ValidateException e = assertThrows(ValidateException.class, () -> controller.checkValidation(user));
        assertEquals("id пользователя не может быть отрицательным", e.getMessage());
    }

    @Test
    void name_empty_Test() throws ValidateException {
        User user = new User(0L, "email@yandex.ru", "Alex", ""
                , LocalDate.of(1900, 10, 20));
        controller.checkValidation(user);
        assertEquals("Alex", user.getName());
    }

    @Test
    void email_wrong_Test() {

        User user = new User(0L, "email.yandex.ru", "Alex", ""
                , LocalDate.of(1900, 10, 20));

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        ConstraintViolation<User> violation = violations.stream().findFirst().orElseThrow(()
                -> new RuntimeException("Отсутствует ошибка валидации"));
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("email не прошел валидацию", violation.getMessageTemplate());

    }

    @Test
    void login_wrong_Test() {
        User user = new User(0L, "email@yandex.ru", "Alex First", ""
                , LocalDate.of(1900, 10, 20));

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        ConstraintViolation<User> violation = violations.stream().findFirst().orElseThrow(()
                -> new RuntimeException("Отсутствует ошибка валидации"));
        assertEquals("login", violation.getPropertyPath().toString());
        assertEquals("login не может быть пустым или содержать пробелы", violation.getMessageTemplate());
    }

    @Test
    void birthday_wrong_Test() {

        User user = new User(0L, "email@yandex.ru", "Alex", "", LocalDate.now().plusDays(1));
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        ConstraintViolation<User> violation = violations.stream().findFirst().orElseThrow(()
                -> new RuntimeException("Отсутствует ошибка валидации"));
        assertEquals("birthday", violation.getPropertyPath().toString());
        assertEquals("Дата рождения не может быть в будущем", violation.getMessageTemplate());
    }

}
