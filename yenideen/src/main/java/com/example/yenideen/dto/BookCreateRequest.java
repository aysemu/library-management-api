package com.example.yenideen.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Author must not be blank")
    private String author;

    @NotNull(message = "Age must not be null")
    @Min(value = 0, message = "Age must be greater than or equal to 0")
    private Long age;

    @NotNull(message = "Publish date must not be null")
    @PastOrPresent(message = "Publish date must be in the past or present")
    private LocalDate publishDate;
}
