package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class Film {

    final static LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

    private int id;

    @NotBlank(message = ("название не может быть пустым"))
    @NonNull
    private String name;

    @Size(max = 200, message = ("длина описания должна быть менее 200 знаков"))
    @NonNull()
    private String description;

    @NonNull
    private LocalDate releaseDate;

    @Positive(message = ("Продолжительность не может быть отрицательной"))
    private double duration;
}
