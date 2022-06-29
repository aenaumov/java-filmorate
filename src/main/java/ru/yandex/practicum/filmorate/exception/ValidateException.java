package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ValidateException extends Exception {
    public ValidateException(final String message) {
        super(message);
        log.warn(message);
    }
}