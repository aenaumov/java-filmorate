package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class User {

    private int id;

    @Email(regexp = "\\w+@\\w+.\\w+", message = ("email не прошел валидацию"))
    @NonNull
    private String email;

    @Pattern(regexp = "\\w+", message = ("login не может быть пустым или содержать пробелы"))
    @NonNull
    private String login;

    private String name;

    @Past(message = "Дата рождения не может быть в будущем")
    @NonNull
    private LocalDate birthday;
}
