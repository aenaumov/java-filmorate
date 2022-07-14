package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public class ValidateException extends RuntimeException {
    public ValidateException(final String message) {
        super(message);
        log.warn(message);
    }
}