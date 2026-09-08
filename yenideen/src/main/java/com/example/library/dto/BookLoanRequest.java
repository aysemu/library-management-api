package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookLoanRequest {

    @NotNull(message = "User id must not be null")
    private Long userId;

    @NotNull(message = "Book id must not be null")
    private Long bookId;

    private Integer loanDays; // optional, default 14 days
}
