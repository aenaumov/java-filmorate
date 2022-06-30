package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    UserController controller;

    @BeforeEach
    void init() {
        controller = new UserController();
    }

    @Test
    void id_negative_Test() {

        User user = new User(0, "email@yandex.ru", "Alex", "test"
                , LocalDate.of(1900, 10, 20));
        user.setId(-1);
        ValidateException e = assertThrows(ValidateException.class, () -> controller.checkValidation(user));
        assertEquals("id пользователя не может быть отрицательным", e.getMessage());
    }

    @Test
    void name_empty_Test() throws ValidateException {
        User user = new User(0, "email@yandex.ru", "Alex", ""
                , LocalDate.of(1900, 10, 20));
        controller.checkValidation(user);
        assertEquals("Alex", user.getName());
    }

    @Test
    void email_wrong_Test() {

        User user = new User(0, "email.yandex.ru", "Alex", ""
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
        User user = new User(0, "email@yandex.ru", "Alex First", ""
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

        User user = new User(0, "email@yandex.ru", "Alex", "", LocalDate.now().plusDays(1));
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        ConstraintViolation<User> violation = violations.stream().findFirst().orElseThrow(()
                -> new RuntimeException("Отсутствует ошибка валидации"));
        assertEquals("birthday", violation.getPropertyPath().toString());
        assertEquals("Дата рождения не может быть в будущем", violation.getMessageTemplate());
    }

}