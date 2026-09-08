package com.example.yenideen.dto;

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
    private String title;
    private String author;
    private Long age;
    private LocalDate publishDate;
}
